package nl.tudelft.tbm.pvr.data;

import android.graphics.Color;

import nl.tudelft.tbm.pvr.R;

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
    public static int[] science = new int[]{Color.rgb(38,128,77), Color.rgb(89,179,128)};
    public static int[] children = new int[]{Color.rgb(168,1,5), Color.rgb(217,54,57)};
    public static int[] religion = new int[]{Color.rgb(124,65,130), Color.rgb(176,127,181)};
    public static int[] cultuur = new int[]{Color.rgb(123,18,98), Color.rgb(173,61,147)};
    public static int[] film = new int[]{Color.rgb(130,144,144), Color.rgb(194,194,194)};

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
        } else if (category.equals("Science/Nature")) {
            return science;
        } else if (category.equals("Children")) {
            return children;
        } else if (category.equals("Religion")) {
            return religion;
        } else if (category.equals("Music")) {
            return cultuur;
        } else if (category.equals("Film")) {
            return film;
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

    public static int getIcon(String name) {
        if(name.equals("Nederland 1")) return R.drawable.ic_ned1;
        if(name.equals("Nederland 2")) return R.drawable.ic_ned2;
        if(name.equals("Nederland 3")) return R.drawable.ic_ned3;
        if(name.equals("RTL 4")) return R.drawable.ic_rtl4;
        if(name.equals("RTL 5")) return R.drawable.ic_rtl5;
        if(name.equals("RTL 7")) return R.drawable.ic_rtl7;
        if(name.equals("RTL 8")) return R.drawable.ic_rtl8;
        if(name.equals("NET 5")) return R.drawable.ic_net5;
        if(name.equals("SBS 6")) return R.drawable.ic_sbs6;
        if(name.equals("Discovery Channel")) return R.drawable.ic_discovery;

        return R.drawable.ic_no_icon;
    }
}
