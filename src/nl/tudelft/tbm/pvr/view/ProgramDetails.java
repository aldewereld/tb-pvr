package nl.tudelft.tbm.pvr.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.StringTokenizer;

import nl.tudelft.tbm.pvr.R;

/**
 * @author Huib Aldewereld
 */
public class ProgramDetails implements View.OnClickListener {
    private ProgramView program;
    private View pView;
    private PopupWindow popup;
    private Button record, close;
    private Animation appear;

    public ProgramDetails(Context context, ProgramView program) {
        this.program = program;

        final float scale = context.getResources().getDisplayMetrics().density;
        pView = ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_view, null);
        popup = new PopupWindow(pView, (int) (scale * 400 + 0.5f), FrameLayout.LayoutParams.WRAP_CONTENT, true);

        appear = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);

        popup.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_window_dim));
        popup.setOutsideTouchable(true);

        setDetails();
    }

    public void showAtLocation(View v) {
        popup.showAtLocation(v, Gravity.CENTER, 0, 0);
        pView.startAnimation(appear);
    }

    public void dismiss(){
        popup.dismiss();
    }

    public void onClick(View v) {
        showAtLocation(pView);
    }

    private void setDetails() {
        //set contents
        //title
        TextView title = (TextView) pView.findViewById(R.id.name);
        title.setText(program.getProgram().getTitle());
        //description
        TextView description = (TextView) pView.findViewById(R.id.description);
        description.setText(program.getProgram().getDescription());
        //channel
        TextView channel = (TextView) pView.findViewById(R.id.channelTitle);
        channel.setText(program.getChannel().getName());
        //airtime
        TextView airtime = (TextView) pView.findViewById(R.id.airTime);
        String time = "";
        StringTokenizer start = new StringTokenizer(program.getProgram().getStartTime(), "TZ");
        start.nextToken();//discard first (date)
        time += start.nextToken();
        StringTokenizer end = new StringTokenizer(program.getProgram().getEndTime(), "TZ");
        end.nextToken();//discard first (date)
        time += " to " + end.nextToken();
        airtime.setText(time);

        if(program.getProgram().getSubtitle().equals("")) {
            TableLayout table = (TableLayout) pView.findViewById(R.id.table);
            table.removeViewAt(3);
        } else {
            //subtitle
            TextView subtitle = (TextView) pView.findViewById(R.id.subTitle);
            subtitle.setText(program.getProgram().getSubtitle());
        }
        //category
        TextView category = (TextView) pView.findViewById(R.id.category);
        category.setText(program.getProgram().getCategory());

        //button behaviour
        close = (Button) pView.findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        record = (Button) pView.findViewById(R.id.recordButton);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
