package nl.tudelft.tbm.pvr.util;

import java.util.ArrayList;

import nl.tudelft.tbm.pvr.data.Channel;

/**
 * @author Huib Aldewereld
 * Interface for the class that collects program details (from txt/xml file) and creates the list of channels.
 */
public interface ChannelParserInterface {

    /**
     * Method invoked by the app to create the list of channels. This method should:
     *  - read the program details from file (txt/xml)
     *  - create the <code>ArrayList<Program></code> for each channel
     *  - create the Channel object for each channel (setting their name and program list)
     *  - return the list of create Channel objects.
     * Before returning the list of Channels, make sure that no gaps exist in the Program Lists of each channels (fill the gaps!).
     * @return  The ArrayList of Channels.
     */
    public ArrayList<Channel> createChannels();
}
