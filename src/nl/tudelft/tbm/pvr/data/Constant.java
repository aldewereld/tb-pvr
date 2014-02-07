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

    public static int[] timeHeader = new int[]{Color.rgb(15,20,135), Color.rgb(15,20,200)};

    public static int[] getColor(String category) {
        if (category.equals("Actualiteit")) {
            return actuality;
        } else if (category.equals("Drama")) {
            return drama;
        } else if (category.equals("Nieuws")) {
            return news;
        } else if (category.equals("Talk")) {
            return talk;
        } else if (category.equals("Education")) {
            return education;
        } else if (category.equals("Sports")) {
            return sports;
        } else {
            return unknown;
        }
    }

    //channel icon urls:
    public static String nederland1 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned1.gif";
    public static String nederland2 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned2.gif";
    public static String nederland3 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/ned3.gif";
    public static String rtl4 = "http://graphics.tudelft.nl/~paul/logos/gif/64x64/rtl4.gif";
}
