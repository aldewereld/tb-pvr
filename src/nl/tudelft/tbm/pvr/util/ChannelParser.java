package nl.tudelft.tbm.pvr.util;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.tbm.pvr.data.Channel;

/**
 * @author [YOUR NAME HERE; include student number!]
 */
public class ChannelParser implements ChannelParserInterface {

    /**
     * Hulp methode om het bestand van internet in te lezen en als Stream aan te bieden.
     * @param type  "xml" of "txt" om aan te duiden welk bestand je wilt gebruiken
     * @return  De InputStream die gelezen kan worden.
     */
    private InputStream openStream(String type) {
        try {
            URL url;

            if(type.equals("xml"))
                url = new URL("http://ict1.tbm.tudelft.nl/epg.xml");
            else
                url = new URL("http://ict1.tbm.tudelft.nl/epg.txt");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Channel> createChannels() {
        ArrayList<Channel> result = new ArrayList<Channel>();

        Scanner input = new Scanner(openStream("txt"));//gebruik openStream("xml") om de xml file te lezen.

        //Jou code hier...
        result.add(new Channel());

        return result;
    }
}
