package nl.tudelft.tbm.pvr.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.StringTokenizer;

import nl.tudelft.tbm.pvr.R;
import nl.tudelft.tbm.pvr.data.Constant;
import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 * Implements the drawable Program elements (rounded rectangles representing the program in the timeline).
 */
public class ProgramView extends CustomView {

    private String subtitle;
    private Program program;

    public ProgramView(Context context) {
        super(context);
    }

    public ProgramView(Context context, Program program, int duration) {
        super(context);

        this.program = program;
        this.title = program.getTitle();
        if(duration < 15) {
            this.title = "...";
        } else {
            if(!program.getSubtitle().equals(""))
                subtitle = program.getSubtitle();
            else
                subtitle = program.getCategory();
        }
        setCategoryColor(Constant.getColor(program.getCategory()));
        setCornerRadius(10);
        setInside(1.5f);
        setColorBackground(Color.BLACK);

        setLayout(duration);

        setBackgroundImage();

        setTitle();
    }

    @Override
    protected void setTitle() {
        TextView programName = new TextView(getContext());
        programName.setText(title);
        programName.setTextAppearance(getContext(), R.style.textLarge);
        programName.setPadding(5,0,0,0);
        programName.setMaxLines(2);
        this.addView(programName);

        //add subtitle/category (time for TimeHeaders).
        TextView programCat = new TextView(getContext());
        programCat.setText(subtitle);

        programCat.setTextAppearance(getContext(), R.style.textNormal);
        programCat.setTextColor(Color.WHITE);
        programCat.setPadding(7,0,0,0);
        programCat.setMaxLines(2);
        this.addView(programCat);
    }

    public void setRecord(boolean record) {
        if(record)
            setColorBackground(Color.RED);
        else
            setColorBackground(Color.BLACK);
    }

    public Program getProgram() {   return program; }
}
