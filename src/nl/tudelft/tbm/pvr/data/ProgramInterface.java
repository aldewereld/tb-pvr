package nl.tudelft.tbm.pvr.data;


/**
 * @author Huib Aldewereld
 * Definition of the Program data type.
 */
public interface ProgramInterface {

    /**
     * Method that returns the title of this program, e.g. "Goede Tijden, Slechte Tijden".
     * @return  String title.
     */
    public String getTitle();

    /**
     * Method that returns the subtitle of this program, e.g. "Rumour in Meerdijk".
     * @return  String subtitle.
     */
    public String getSubtitle();

    /**
     * Method that returns the description of this program, e.g. "Lorena is weer eens dom bezig... blabla."
     * @return  String description.
     */
    public String getDescription();

    /**
     * Method that returns the category (genre) of this program, e.g. "Drama".
     * @return  String category.
     */
    public String getCategory();

    /**
     * Method that returns the start time of this program in ISO format: yyyy-mm-ddThh:mm, e.g. "2014-01-01T20:00"
     * @return  String start time.
     */
    public String getStartTime();

    /**
     * Method that returns the end time of this program in ISO format: yyyy-mm-ddThh:mm, e.g. "2014-01-01T20:30"
     * @return  String end time.
     */
    public String getEndTime();

    /**
     * Method that returns the running time (duration) of this program (in minutes), e.g. 30.
     * @return  int duration.
     */
    public int getDuration();
}
