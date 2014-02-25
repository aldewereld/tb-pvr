package nl.tudelft.tbm.pvr.data;

import android.graphics.Color;

/**
 * @author Huib Aldewereld
 * Java file containing constants that are used throughout the project.
 */
public class Constant {
    public static int programLength = 4;

    //Color definitions
    public static int[] actuality = new int[]{Color.rgb(23,95,178), Color.rgb(92,159,236)};
    public static int[] drama = new int[]{Color.rgb(160,100,190), Color.rgb(200,150,215)};
    public static int[] news = new int[]{Color.rgb(195,155,70), Color.rgb(235,190,95)};
    public static int[] talk = new int[]{Color.rgb(60,50,170), Color.rgb(145,135,230)};
    public static int[] education = new int[]{Color.rgb(200,200,200), Color.rgb(225,225,225)};
    public static int[] sports = new int[]{Color.rgb(60,150,20), Color.rgb(80,200,25)};
    public static int[] amusement = new int[]{Color.rgb(0,0,0), Color.rgb(0,0,0)};//TODO
    public static int[] unknown = new int[]{Color.rgb(100,100,100),Color.rgb(150,150,150)};
    //public static int[] science = new int[]{Color.rgb(), Color.rgb()};
    //public static int[] children = new int[]{Color.rgb(), Color.rgb()};
    //public static int[] adult = new int[]{Color.rgb(), Color.rgb()};
    //public static int[] religion = new int[]{Color.rgb(), Color.rgb()};

    public static int[] timeHeader = new int[]{Color.rgb(15,20,135), Color.rgb(15,20,200)};

    public static int[] getColor(String category) {
        if (category.equals("Actualiteit")) {
            return actuality;
        } else if (category.equals("Drama")) {
            return drama;
        } else if (category.equals("News")) {
            return news;
        } else if (category.equals("Talk")) {
            return talk;
        } else if (category.equals("Educational")) {
            return education;
        } else if (category.equals("Sports")) {
            return sports;
        } else if (category.equals("Amusement")) {
            return amusement;
        } else {
            return unknown;
        }
    }

    public static String[] channelOrder = {"Nederland 1", "Nederland 2", "Nederland 3", "RTL 4", "RTL 5", "SBS 6", "RTL 7", "RTL 8", "NET 5", "Veronica", "Discovery Channel",
                                            "National Geographic", "Eén", "Canvas", "BBC 1", "BBC 2", "Eurosport"};
    public static int channelIndex(String name) {
        for(int i = 0; i < channelOrder.length; i++) {
            if(channelOrder[i].equals(name)) return i;
        }
        return Integer.MAX_VALUE;
    }

    //channel icon urls:
    public static String nederland1 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned1.gif";
    public static String nederland2 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned2.gif";
    public static String nederland3 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned3.gif";
    public static String rtl4 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/rtl4.gif";
}
