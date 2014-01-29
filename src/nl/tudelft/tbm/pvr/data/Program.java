package nl.tudelft.tbm.pvr.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
        String[] starts = startTime.split("-T:");
        String[] ends = endTime.split("-T:");
        Calendar startCal = new GregorianCalendar(Integer.parseInt(starts[0]),Integer.parseInt(starts[1]),Integer.parseInt(starts[2]),Integer.parseInt(starts[3]),Integer.parseInt(starts[4]));
        Calendar endCal = new GregorianCalendar(Integer.parseInt(ends[0]),Integer.parseInt(ends[1]),Integer.parseInt(ends[2]),Integer.parseInt(ends[3]),Integer.parseInt(ends[4]));

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
