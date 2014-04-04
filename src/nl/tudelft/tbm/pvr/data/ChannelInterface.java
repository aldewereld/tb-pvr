package nl.tudelft.tbm.pvr.data;

import java.util.ArrayList;

/**
 * @author Huib Aldewereld
 * Definition of the Channel datatype.
 */
public interface ChannelInterface {
    /**
     * Method that returns the name of this channel, e.g. "Nederland 1".
     * @return  String channel name.
     */
    public String getName();

    /**
     * Method that returns the list of programs scheduled on this channel.
     * @return  ArrayList of Programs.
     */
    public ArrayList<Program> getPrograms();

    /**
     * Method to add a program to the list of programs.
     * @param program   The program that is to be added to the list.
     */
    public void addProgram(Program program);
}
