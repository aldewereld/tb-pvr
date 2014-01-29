package nl.tudelft.tbm.pvr.view;

import android.content.Context;

/**
 * @author Huib Aldewereld
 * Implements the drawable header-elements in the Timeline.
 */
public class TimeHeaderView extends CustomView {

    public TimeHeaderView(Context context, String time) {
        super(context, time, 30);
    }
}
