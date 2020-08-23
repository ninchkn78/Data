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
        return "F: " + FemaleName + "\nM: " + MaleName;
    }
    public String CountBabiesForYearGenderFirstLetter(int year, char letter, String gender){
        Process process = new Process(year);
        int CountNames = process.NamesStartWith(letter, gender);
        int TotalBabies = process.TotalCount(letter,gender);
        return "#OfNames: " + Integer.toString(CountNames) + "\n#OfBabies: " + Integer.toString(TotalBabies);
    }
    public static void main(String[] args)
    {
        Output Test = new Output();
        System.out.println(Test.CountBabiesForYearGenderFirstLetter(1900,'Q',"M"));
        System.out.println(Test.CountBabiesForYearGenderFirstLetter(1900,'Q',"F"));
        System.out.println(Test.TopRankedNamesForYear(1900));

    }
}
