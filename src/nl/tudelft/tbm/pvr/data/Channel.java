package nl.tudelft.tbm.pvr.data;

import java.util.ArrayList;

/**
 * @author Huib Aldewereld
 * Datatype to represent Channels
 */
public class Channel implements Comparable<Channel> {
    private String name;
    private ArrayList<Program> programs = new ArrayList<Program>();

    public Channel(String name, ArrayList<Program> programs) {
        this.name = name;
        this.programs = programs;
    }

    public String getName() {   return name;    }
    public ArrayList<Program> getPrograms() { return programs;    }
    public void addProgram(Program program) { programs.add(program); }

    @Override
    public int compareTo(Channel channel) {

        int myIndex = Constant.channelIndex(name);
        int hisIndex = Constant.channelIndex(channel.getName());
        return myIndex - hisIndex;
    }
}
