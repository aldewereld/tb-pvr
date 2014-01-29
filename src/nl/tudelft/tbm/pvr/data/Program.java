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
    private String startTime, endTime;//ISO format: YYYY-MM-DDTHH:MM, e.g., 2014-01-16T12:00

    public Program(String title, String subtitle, String description, String category, String startTime, String endTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private static int calculateDuration(String startTime, String endTime) {
        StringTokenizer startTok = new StringTokenizer(startTime, "\\-T:Z");
        Calendar startCal = new GregorianCalendar(Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()), Integer.parseInt(startTok.nextToken()));
        StringTokenizer endTok = new StringTokenizer(endTime, "\\-T:Z");
        Calendar endCal = new GregorianCalendar(Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()), Integer.parseInt(endTok.nextToken()));

        long millis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        int duration = (int) (millis / 1000 / 60);// 1000 milliseconds per second, 60 seconds per minute.

        return duration;
    }

    public String getTitle() {  return title;   }
    public String getSubtitle() {   return subtitle;  }
    public String getDescription() {    return description; }
    public String getCategory() {   return category;    }
    public String getStartTime() {  return startTime;   }
    public String getEndTime() {    return endTime; }
    public int getDuration() {  return calculateDuration(startTime, endTime);    }
}
