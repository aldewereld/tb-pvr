package nl.tudelft.tbm.pvr.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import nl.tudelft.tbm.pvr.data.Channel;
import nl.tudelft.tbm.pvr.data.Program;

/**
 * @author Huib Aldewereld
 */
public class ChannelParser {

    public ArrayList<Channel> createChannels() {
        return openXmlStream("http://ict1.tbm.tudelft.nl/epg.xml");
    }

    private ArrayList<Channel> openXmlStream(String address){
        ArrayList<Channel> result = new ArrayList<Channel>();
        try {
            URL url = new URL(address);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(con.getInputStream());

            //read the stream
            result = parseXml(input);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private ArrayList<Channel> parseXml(InputStreamReader input) throws Exception {
        HashMap<String, Channel> channels = new HashMap<String, Channel>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(input);

        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if(xpp.getName().equals("channel")) {//we are parsing a channel-tag
                    //create new channel
                    String id, name = "";
                    id = xpp.getAttributeValue(null, "id");
                    while(true) {
                        if(eventType == XmlPullParser.END_TAG)
                            if(xpp.getName().equals("channel"))//end of current tag found
                                break;//stop the loop

                        if(eventType == XmlPullParser.START_TAG)
                            if(xpp.getName().equals("display-name"))
                                name = xpp.nextText();

                        eventType = xpp.next();
                    }
                    //add the channel to the hashmap
                    if(!name.equals("")) {
                        Channel newChannel = new Channel(name, new ArrayList<Program>());
                        channels.put(id, newChannel);
                    }


                } else if (xpp.getName().equals("programme")) {
                    //add new program to channel
                    String start, stop, channel, title = "", description = "", category = "", subtitle = "";

                    start = xpp.getAttributeValue(null, "start");
                    stop = xpp.getAttributeValue(null, "stop");
                    channel = xpp.getAttributeValue(null, "channel");
                    while(true) {
                        if(eventType == XmlPullParser.END_TAG)
                            if(xpp.getName().equals("programme"))//end of current tag found
                                break;//stop this loop
                        if(eventType == XmlPullParser.START_TAG) {
                            if(xpp.getName().equals("title")) title = xpp.nextText();
                            if(xpp.getName().equals("desc")) description = xpp.nextText();
                            if(xpp.getName().equals("category")) category = xpp.nextText();
                            if(xpp.getName().equals("sub-title")) subtitle = xpp.nextText();
                        }

                        eventType = xpp.next();
                    }
                    //create the program
                    Program newProgram = new Program(title, subtitle, description, category, start, stop);

                    //get the corresponding channel from the hashmap, and add the program:
                    channels.get(channel).addProgram(newProgram);

                } //else ignore (unknown tag type)
            }
            eventType = xpp.next();
        }

        ArrayList<Channel> result = new ArrayList<Channel>();
        result.addAll(channels.values());

        return result;
    }
}
