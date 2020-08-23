package names;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Output {
    /**
     * Start of the program.
     */
    public String TopRankedNamesForYear(int year){
        Process process = new Process(year);
        String MaleName = process.TopName("M");
        String FemaleName = process.TopName("F");
        return "Top Female Name is: " + FemaleName + "\nTop Male Name is: " + MaleName;

    }
}
