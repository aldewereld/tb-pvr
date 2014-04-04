package nl.tudelft.tbm.pvr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.view.ChannelAdapter;
import nl.tudelft.tbm.pvr.view.TimeHeaderView;

/**
 * @author Huib Aldewereld.
 */
public class EPGFragment extends Fragment {

    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private int mHours, mMinutes;
    private ArrayList<String> mDates = new ArrayList<String>();
    private String mDate = "2014-01-01";
    private ChannelAdapter mAdapter;
    private ArrayAdapter dayAdapter;
    Spinner daySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_epg, container, false);

        daySpinner = (Spinner) rootView.findViewById(R.id.dayButton);
        dayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, mDates);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setDate((String) daySpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.err.println("Nothing selected");
            }
        });

        ListView channelList = (ListView) rootView.findViewById(R.id.channelList);
        channelList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new ChannelAdapter(getActivity(), channels);
        ((MainActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        drawTimeLine((LinearLayout) getView().findViewById(R.id.timeHeaderContainer), mHours, mMinutes);
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels.clear();
        this.channels.addAll(channels);
        //Collections.sort(this.channels);//Only works when channels are made comparable!
        mAdapter.notifyDataSetChanged();
    }

    public void setTime(int hours, int minutes) {
        mHours = hours;
        mMinutes = minutes;
        String date = mDate + "T"+ (mHours < 10?"0":"")+mHours+":"+(mMinutes == 0?"00":"30")+"Z";
        mAdapter.setDate(date);
        mAdapter.notifyDataSetChanged();
    }

    public void setDate(String day) {
        if(mDate.equals(day))
            return;//no need to change view if the day stays the same!
        mDate = day;
        daySpinner.setSelection(mDates.indexOf(mDate));
        String date = mDate + "T"+ (mHours < 10?"0":"")+mHours+":"+(mMinutes == 0?"00":"30")+"Z";
        mAdapter.setDate(date);
        mAdapter.notifyDataSetChanged();
    }

    public void resetDates(ArrayList<String> dates) {
        mDates.clear();
        for(String date : dates)
            mDates.add(date);

        dayAdapter.notifyDataSetChanged();
    }

    public int datesIndex() {//returns index of active selection
        return mDates.indexOf(mDate);
    }

    public void drawTimeLine(LinearLayout container, int hours, int minutes) {
        container.removeAllViews();

        //update stored values, and propagate to ListView
        setTime(hours, minutes);

        //set text on button
        ((Button) getActivity().findViewById(R.id.timeButton)).setText((hours < 10?"0":"")+hours+":"+(minutes == 0?"00":"30"));

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        int hour = hours;
        int minute = minutes;
        boolean half = minutes != 0;
        while (screenWidth > 0) {
            TimeHeaderView header = new TimeHeaderView(getActivity(),""+(hour < 10?"0":"")+hour+":"+(minute==0?"00":"30"));
            container.addView(header);
            if(half) {
                minute = 0;
                hour++;
                if(hour == 24)
                    hour = 0;
            } else {
                minute = 30;
            }
            half = !half;
            screenWidth -= header.getSize();
        }
    }
}
