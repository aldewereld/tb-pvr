package nl.tudelft.tbm.pvr.view;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import nl.tudelft.tbm.pvr.R;
import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.data.Constant;
import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 * Implements a Custom ArrayAdapter that defines the looks of the Channels list
 */
public class ChannelAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private final ArrayList<Channel> channels;
    private String mDate = "2014-01-30T00:00Z";

    public ChannelAdapter(Context context, ArrayList<Channel> channels) {
        super(context, R.layout.channel_layout, channels);
        this.context = context;
        this.channels = channels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.channel_layout, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        imageView.setImageDrawable(parent.getResources().getDrawable(Constant.getIcon(channels.get(position).getName())));

        LinearLayout programs = (LinearLayout) rowView.findViewById(R.id.programs);
        int startIndex = -1;
        for(int i = 0; i < channels.get(position).getPrograms().size(); i++) {
            Program program = channels.get(position).getPrograms().get(i);
            if(isStartProgram(program)) {
                startIndex = i;
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        Channel channel = channels.get(position);
        View.OnClickListener listener = new ProgramDetails(context);

        if(startIndex >= 0) {
            int index = startIndex;

            while(screenWidth > 0) {
                if(index >= channels.get(position).getPrograms().size())
                    break;
                Program program = channels.get(position).getPrograms().get(index);
                ProgramView newProgram = new ProgramView(context, channel, program, calculateDuration(program));

                newProgram.setOnClickListener(listener);

                programs.addView(newProgram);
                screenWidth -= newProgram.getSize();
                index++;
            }
        }

        if(screenWidth > 0) {
            Program program = new Program("No data", "", "", "", mDate, mDate);
            ProgramView newProgram = new ProgramView(context, channel, program, screenWidth);
            programs.addView(newProgram);
        }

        return rowView;
    }

    private boolean isStartProgram(Program program) {
        StringTokenizer timeTok = new StringTokenizer(mDate, "\\-T:Z");
        Calendar time = new GregorianCalendar(Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()));

        StringTokenizer startTok = new StringTokenizer(program.getStartTime(), "\\-T:Z");
        Calendar start = new GregorianCalendar(Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()));

        if (time.getTimeInMillis() < start.getTimeInMillis()) {
            //System.err.println("Program "+program.getTitle()+" in future");
            return false;//program in future!
        }

        StringTokenizer endTok = new StringTokenizer(program.getEndTime(), "\\-T:Z");
        Calendar end = new GregorianCalendar(Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()));

        if (time.getTimeInMillis() > end.getTimeInMillis()) {
            //System.err.println("Program "+program.getTitle()+" already finished");
            return false;// program already finished!
        }

        return true;//match
    }

    private int calculateDuration(Program program) {
        StringTokenizer timeTok = new StringTokenizer(mDate, "\\-T:Z");
        Calendar time = new GregorianCalendar(Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()), Integer.parseInt(timeTok.nextToken()));

        StringTokenizer startTok = new StringTokenizer(program.getStartTime(), "\\-T:Z");
        Calendar start = new GregorianCalendar(Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()));

        if(time.getTimeInMillis() > start.getTimeInMillis()) {
            StringTokenizer endTok = new StringTokenizer(program.getEndTime(), "\\-T:Z");
            Calendar end = new GregorianCalendar(Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()));

            return (int) ((end.getTimeInMillis() - time.getTimeInMillis()) / 1000 /60);
        } else
            return program.getDuration();
    }

    public void setDate (String date) { mDate = date;   }
}
