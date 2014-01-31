package nl.tudelft.tbm.pvr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.data.Program;
import nl.tudelft.tbm.pvr.view.ChannelAdapter;
import nl.tudelft.tbm.pvr.view.TimeHeaderView;

/**
 * @author Huib Aldewereld.
 */
public class EPGFragment extends Fragment {

    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private int mHours, mMinutes;
    private ChannelAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_epg, container, false);

        Spinner daySpinner = (Spinner) rootView.findViewById(R.id.dayButton);
        String[] days = {"Today", "Tomorrow"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        /* TEST CODE TODO: Remove*/
        ListView channelList = (ListView) rootView.findViewById(R.id.channelList);
        ArrayList<Program> ned1 = new ArrayList<Program>();
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-29T23:55Z","2014-01-30T00:15Z"));
        ned1.add(new Program("Paul & Witteman", "", "", "Talk", "2014-01-30T00:15Z", "2014-01-30T01:15Z"));
        ned1.add(new Program("Een Vandaag", "", "", "Actualiteit", "2014-01-30T01:15Z", "2014-01-30T01:45Z"));
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-30T01:45Z", "2014-01-30T02:00Z"));
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-30T02:00Z", "2014-01-30T02:27Z"));
        ned1.add(new Program("NOS Tekst TV", "", "", "Actualiteit", "2014-01-30T02:27Z", "2014-01-30T05:55Z"));
        ned1.add(new Program("Nederland in Beweging!", "", "", "Sports", "2014-01-30T05:55Z", "2014-01-30T06:10Z"));
        ned1.add(new Program("Max Geheugentrainer", "", "", "Amusement", "2014-01-30T06:10Z", "2014-01-30T06:30Z"));
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-30T06:30Z", "2014-01-30T07:00Z"));
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-30T07:00Z", "2014-01-30T07:10Z"));
        ned1.add(new Program("Vandaag de Dag", "", "", "Actualiteit", "2014-01-30T07:10Z", "2014-01-30T07:30Z"));
        ned1.add(new Program("NOS Journaal", "", "", "Nieuws", "2014-01-30T07:30Z", "2014-01-30T07:40Z"));
        ned1.add(new Program("Vandaag de Dag", "", "", "Actualiteit", "2014-01-30T07:40Z", "2014-01-30T08:00Z"));

        channels.clear();

        channels.add(new Channel("Nederland 1", ned1));
        channels.add(new Channel("Nederland 2", new ArrayList<Program>()));
        channels.add(new Channel("Nederland 3", new ArrayList<Program>()));
        channels.add(new Channel("RTL 4", new ArrayList<Program>()));
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
        this.channels = channels;
    }

    public void setTime(int hours, int minutes) {
        mHours = hours;
        mMinutes = minutes;
        String date = "2014-01-30T"+(mHours < 10?"0":"")+mHours+":"+(minutes == 0?"00":"30")+"Z";
        mAdapter.setDate(date);
        mAdapter.notifyDataSetChanged();
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
