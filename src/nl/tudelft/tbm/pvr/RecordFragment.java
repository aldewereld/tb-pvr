package nl.tudelft.tbm.pvr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 */
public class RecordFragment extends Fragment {

    private ArrayList<Program> scheduled = new ArrayList<Program>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.scheduleTitle);
        textView.setText("Recording Schedule");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

    public void setScheduled(ArrayList<Program> scheduled) {
        this.scheduled = scheduled;
    }
}
