package nl.tudelft.tbm.pvr.data;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * @author Huib Aldewereld
 * Datatype to represent Channels
 */
public class Channel {
    private String name;
    Bitmap icon;
    private ArrayList<Program> programs = new ArrayList<Program>();

    public Channel(String name, ArrayList<Program> programs) {
        this.name = name;
        this.programs = programs;
    }

    public String getName() {   return name;    }
    public ArrayList<Program> getPrograms() { return programs;    }
    public Bitmap getIcon() { return icon; }
    public void setIcon(Bitmap icon) {  this.icon = icon;   }
    public void addProgram(Program program) { programs.add(program); }
}
