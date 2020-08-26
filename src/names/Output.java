package names;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Output {
    /**
     * Start of the program.
     */
    public String topRankedNames(int year){
        Process process = new Process(year);
        String MaleName = process.getName("M", 1);
        String FemaleName = process.getName("F", 1);
        return "F: " + FemaleName + "\nM: " + MaleName;
    }
    public String countBabies(int year, char letter, String gender){
        Process process = new Process(year);
        int CountNames = process.namesStartWith(letter, gender);
        int TotalBabies = process.totalCount(letter,gender);
        return "#OfNames: " + CountNames + "\n#OfBabies: " + TotalBabies;
    }
    public static void main(String[] args)
    {
        Output Test = new Output();
        System.out.println(Test.countBabies(1900,'Q',"M"));
        System.out.println(Test.countBabies(1900,'Q',"F"));
        System.out.println(Test.topRankedNames(1900));

    }
}
