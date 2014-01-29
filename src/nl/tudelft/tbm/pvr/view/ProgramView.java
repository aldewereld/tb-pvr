package nl.tudelft.tbm.pvr.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import nl.tudelft.tbm.pvr.R;
import nl.tudelft.tbm.pvr.data.Constant;
import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 * Implements the drawable Program elements (rounded rectangles representing the program in the timeline).
 */
public class ProgramView extends CustomView {

    protected int[] categoryColor = Constant.unknown;
    protected int cornerRadius = 10;
    protected float inside = 1.5f;

    private String subtitle;
    private Program program;

    public ProgramView(Context context) {
        super(context);
    }

    public ProgramView(Context context, Program program) {
        super(context);

        this.program = program;
        this.title = program.getTitle();
        if(!program.getSubtitle().equals(""))
            subtitle = program.getSubtitle();
        else
            subtitle = program.getCategory();
        categoryColor = Constant.getColor(program.getCategory());

        setLayout(program.getDuration());

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
            backgroundColor = Color.RED;
        else
            backgroundColor = Color.BLACK;
    }

    public Program getProgram() {   return program; }
}