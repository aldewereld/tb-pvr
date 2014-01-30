package nl.tudelft.tbm.pvr.view;

import android.app.ActionBar;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import nl.tudelft.tbm.pvr.R;
import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 * Implements a Custom ArrayAdapter that defines the looks of the Channels list
 */
public class ChannelAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private final ArrayList<Channel> channels;
    private String mDate = "2014-01-30T00:00Z";
    private PopupWindow popup;

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

        //set icon
        String s = channels.get(position).getName();
        if(s.startsWith("Nederland 1")) {
            imageView.setImageResource(R.drawable.ic_ned1);
        } else if (s.startsWith("Nederland 2")) {
            imageView.setImageResource(R.drawable.ic_ned2);
        } else if (s.startsWith("Nederland 3")) {
            imageView.setImageResource(R.drawable.ic_ned3);
        }


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

        if(startIndex >= 0) {
            int index = startIndex;

            while(screenWidth > 0) {
                if(index >= channels.get(position).getPrograms().size())
                    break;
                Program program = channels.get(position).getPrograms().get(index);
                ProgramView newProgram = new ProgramView(context, program, calculateDuration(program));

                newProgram.setOnClickListener(new CustomListener(newProgram));

                programs.addView(newProgram);
                screenWidth -= newProgram.getSize();
                index++;
            }
        }

        if(screenWidth > 0) {
            Program program = new Program("No data", "", "", "", mDate, mDate);
            ProgramView newProgram = new ProgramView(context, program, screenWidth);
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

    public class CustomListener implements View.OnClickListener {
        private final ProgramView program;

        public CustomListener(ProgramView program) {
            this.program = program;
        }

        @Override
        public void onClick(View v) {DisplayMetrics metrics = new DisplayMetrics();
            final float scale = context.getResources().getDisplayMetrics().density;
            View popupView = ((LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_view, null);
            popup = new PopupWindow(popupView, (int) (scale * 400 + 0.5f), ActionBar.LayoutParams.WRAP_CONTENT, true);
            popup.setAnimationStyle(android.R.style.Animation_Dialog);
            popup.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_window_dim));
            popup.setOutsideTouchable(true);

            //set contents
            TextView title = (TextView) popupView.findViewById(R.id.name);
            title.setText(program.getProgram().getTitle());
            TextView description = (TextView) popupView.findViewById(R.id.description);
            description.setText(program.getProgram().getDescription());
            TextView airtime = (TextView) popupView.findViewById(R.id.airTime);
            String time = "";
            StringTokenizer start = new StringTokenizer(program.getProgram().getStartTime(), "TZ");
            //discard first (date):
            start.nextToken();
            time += start.nextToken();
            StringTokenizer end = new StringTokenizer(program.getProgram().getEndTime(), "TZ");
            end.nextToken();
            time += " to " + end.nextToken();
            airtime.setText(time);
            TextView subtitle = (TextView) popupView.findViewById(R.id.subTitle);
            subtitle.setText(program.getProgram().getSubtitle());

            TextView category = (TextView) popupView.findViewById(R.id.category);
            category.setText(program.getProgram().getCategory());

            popup.showAtLocation(popupView, Gravity.CENTER, 0, 0);


            popupView.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    popup.dismiss();
                }
            });

            popupView.findViewById(R.id.recordButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.dismiss();
                }
            });

        }
    }
}