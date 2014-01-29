package nl.tudelft.tbm.pvr.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tudelft.tbm.pvr.R;
import nl.tudelft.tbm.pvr.data.Constant;

/**
 * @author Huib Aldewereld
 * Implements the Custom View element used in drawing the Timeline (the rounded rectangles).
 */
public class CustomView extends LinearLayout {

    protected int backgroundColor = Color.BLACK;
    protected int[] categoryColor = Constant.timeHeader;
    protected int cornerRadius = 5;
    protected float inside = 1f;

    protected String title;

    private final float scale = getContext().getResources().getDisplayMetrics().density;
    private int width = 0;

    public CustomView(Context context) {
        super(context);
    }

    /**
     * Creates a new LinearLayout View shaped as a rounded rectangle
     * @param context   Activity/Context in which this LinearLayout is used.
     * @param duration  The duration of the program/header block (determines it's width).
     * @param title     The main title to appear on the block.
     */
    public CustomView(Context context, String title, int duration) {
        super(context);

        this.title = title;

        width = (int) (duration * Constant.programLength * scale * 0.5f);

        setLayout(duration);

        //set background image
        setBackgroundImage();

        //set title
        setTitle();
    }

    protected void setLayout(int duration) {
        int width = (int) (duration * Constant.programLength * scale +0.5f);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LayoutParams(width,LayoutParams.MATCH_PARENT));//change duration to pixels!
        this.setGravity(Gravity.CENTER);
    }

    protected void setTitle() {
        TextView header = new TextView(getContext());
        header.setText(title);
        header.setTextAppearance(getContext(), R.style.textNormal);
        header.setPadding(5,0,0,0);
        header.setMaxLines(1);
        this.addView(header);
    }

    public int getSize() { return width;   }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    protected void setBackgroundImage() {
        float[] outerR = new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        RectF inset = new RectF(inside,inside,inside,inside);
        float[] innerR = new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        ShapeDrawable circle = new ShapeDrawable(new RoundRectShape(outerR, inset, innerR));
        circle.setPadding(6, 0, 6, 0);
        circle.getPaint().setColor(backgroundColor);

        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, categoryColor);
        gradient.setCornerRadius(cornerRadius);
        gradient.setBounds(circle.getBounds());

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gradient, circle});
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackgroundDrawable(layerDrawable);
        } else {
            this.setBackground(layerDrawable);
        }
    }
}
