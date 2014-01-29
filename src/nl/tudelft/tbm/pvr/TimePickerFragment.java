package nl.tudelft.tbm.pvr;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Huib Aldewereld.
 */
public class TimePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = 0;

        // Create a new instance of TimePickerDialog and return it
        return new DurationTimePickDialog(getActivity(), (MainActivity) getActivity(), hour, minute, true, 30);
//                DateFormat.is24HourFormat(getActivity()));
    }

    private class DurationTimePickDialog extends TimePickerDialog
    {
        final OnTimeSetListener mCallback;
        TimePicker mTimePicker;
        final int increment;

        public DurationTimePickDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView, int increment)
        {
            super(context, callBack, hourOfDay, minute/increment, is24HourView);
            this.mCallback = callBack;
            this.increment = increment;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mCallback != null && mTimePicker!=null) {
                mTimePicker.clearFocus();
                mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                        mTimePicker.getCurrentMinute()*increment);
            }
        }

        @Override
        protected void onStop()
        {
            // override and do nothing
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {//TODO: API lvl 9 fix!
            super.onCreate(savedInstanceState);
            try
            {
                Class<?> rClass = Class.forName("com.android.internal.R$id");
                Field timePicker = rClass.getField("timePicker");
                this.mTimePicker = (TimePicker)findViewById(timePicker.getInt(null));
                Field m = rClass.getField("minute");

                NumberPicker mMinuteSpinner = (NumberPicker)mTimePicker.findViewById(m.getInt(null));
                mMinuteSpinner.setMinValue(0);
                mMinuteSpinner.setMaxValue((60/increment)-1);
                List<String> displayedValues = new ArrayList<String>();
                for(int i=0;i<60;i+=increment)
                {
                    displayedValues.add(String.format("%02d", i));
                }
                mMinuteSpinner.setDisplayedValues(displayedValues.toArray(new String[0]));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}

