package nl.tudelft.tbm.pvr;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.data.Program;
import nl.tudelft.tbm.pvr.util.ChannelParser;
import nl.tudelft.tbm.pvr.util.ChannelParserInterface;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TimePickerDialog.OnTimeSetListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private EPGFragment mEPG;
    private RecordFragment mRecord;
    private int activeFragment = 1;

    private int mHours, mMinutes;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private ArrayList<Program> recordings = new ArrayList<Program>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epg);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(Calendar.getInstance().get(Calendar.MINUTE)-30 > 0)
            mMinutes = 30;
        else
            mMinutes = 0;

        updateChannels();
    }

    public void updateChannels() {
        ReadChannels task = new ReadChannels();
        task.execute();
        System.out.println("Finished update");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            case 0:
                if(mEPG == null) {
                    mEPG = new EPGFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mEPG)
                        .commit();
                break;
            case 1:
                if(mRecord == null)
                    mRecord = new RecordFragment();
                mRecord.setScheduled(recordings);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mRecord)
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        activeFragment = number;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                mEPG.setTime(mHours, mMinutes);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if(activeFragment == 2) {
                getMenuInflater().inflate(R.menu.scheduled, menu);
                restoreActionBar();
            } else {
                getMenuInflater().inflate(R.menu.epg, menu);
                restoreActionBar();
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void increaseTimeView(View v) {
        LinearLayout timeLine = (LinearLayout) findViewById(R.id.timeHeaderContainer);

        int timeCount = (timeLine.getChildCount() / 2) - 2;//remove two extra hours
        int newHour, newMinute = mMinutes;
        newHour = mHours;
        if(timeCount % 2 == 1) {//odd number of steps
            newMinute += 30;//add 30 minutes
            newHour++;//add an hour
            newMinute = newMinute % 60;//round to 0 if hour is full
            timeCount--;//make timeCount even
        }
        newHour += timeCount/2;//add half the number of blocks to hours
        //TODO when rounding to 0, set day to next (if possible; otherwise, block)
        newHour = newHour % 24;//set to 0 when day is finished

        onTimeSet(null, newHour, newMinute);
    }

    public void decreaseTimeView(View v) {
        LinearLayout timeLine = (LinearLayout) findViewById(R.id.timeHeaderContainer);

        int timeCount = (timeLine.getChildCount() / 2) - 2;//remove two extra hours
        int newHour = mHours, newMinute = mMinutes;
        if(timeCount % 2 == 1) {//odd number of steps
            if(newMinute != 0)
                newMinute = 0;
            else {
                newMinute = 30;
                newHour--;
            }
            timeCount--;//make timeCount even
        }
        newHour -= timeCount/2;
        //TODO when going below 0, move to previous day (if possible, otherwise block)
        if(newHour < 0)
            newHour = 24 + newHour;

        onTimeSet(null, newHour, newMinute);
    }

    @Override
    public void onTimeSet(TimePicker view, int hours, int minutes) {
            mHours = hours;
            mMinutes = minutes;

            //update the view!!
            mEPG.drawTimeLine((LinearLayout) findViewById(R.id.timeHeaderContainer), mHours, mMinutes);
    }

    public class ReadChannels extends AsyncTask<Void,Boolean,ArrayList<Channel>> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Updating channel information...");
            this.dialog.show();

        }

        @Override
        protected ArrayList<Channel> doInBackground(Void... params) {
            ChannelParserInterface parser = new ChannelParser();
            ArrayList<Channel> channels = parser.createChannels();
            for(Channel chan : channels) {
                System.err.println("Channel "+chan.getName()+" has "+chan.getPrograms().size()+" programs.");
            }

            return channels;
        }

        @Override
        protected void onPostExecute(final ArrayList<Channel> success) {
            if(this.dialog.isShowing())
                this.dialog.dismiss();

            if(!success.isEmpty()) {
                channels = success;
                mEPG.setChannels(channels);
                Toast.makeText(getApplicationContext(), "Complete!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
