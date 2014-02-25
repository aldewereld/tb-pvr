package nl.tudelft.tbm.pvr.data;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * @author Huib Aldewereld
 * Definition of the Program data type.
 */
public class Program {
    private String title;
    private String subtitle;
    private String description;
    private String category;
    private String startTime, endTime;//ISO format: YYYYMMDDHHMMSS +ZONE, e.g., 20140227190500 +0100

    public Program(String title, String subtitle, String description, String category, String startTime, String endTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        if(!startTime.contains("-"))
            this.startTime = startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8)+"T"+startTime.substring(8,10)+":"+startTime.substring(10,12);
        else this.startTime = startTime;
        if(!endTime.contains("-"))
            this.endTime = endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8)+"T"+endTime.substring(8,10)+":"+endTime.substring(10,12);
        else this.endTime = endTime;
    }

    private static int calculateDuration(String startTime, String endTime) {
        StringTokenizer startTok = new StringTokenizer(startTime, "\\-T:Z");
        Calendar startCal = new GregorianCalendar(Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()));
        StringTokenizer endTok = new StringTokenizer(endTime, "\\-T:Z");
        Calendar endCal = new GregorianCalendar(Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()));

        long millis = endCal.getTimeInMillis() - startCal.getTimeInMillis();

        return (int) (millis / 1000 / 60);// 1000 milliseconds per second, 60 seconds per minute.
    }

    public String getTitle() {  return title;   }
    public String getSubtitle() {   return subtitle;  }
    public String getDescription() {    return description; }
    public String getCategory() {   return category;    }
    public String getStartTime() {  return startTime;   }
    public String getEndTime() {    return endTime; }
    public int getDuration() {  return calculateDuration(startTime, endTime);    }
}
