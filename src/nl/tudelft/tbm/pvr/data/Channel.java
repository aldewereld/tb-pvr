package nl.tudelft.tbm.pvr.data;

import java.util.ArrayList;

/**
 * @author Huib Aldewereld
 * Datatype to represent Channels
 */
public class Channel {
    private String name;
    private ArrayList<Program> programs = new ArrayList<Program>();

    public Channel(String name, ArrayList<Program> programs) {
        this.name = name;
        this.programs = programs;
    }

    public String getName() {   return name;    }
    public ArrayList<Program> getPrograms() { return programs;    }
}
