package names;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Output {
    /**
     * Start of the program.
     */
    public String topRankedNames(int start, int end){
        Process process = new Process(start, end);
        String MaleName = process.getName("M", 1);
        String FemaleName = process.getName("F", 1);
        return "F: " + FemaleName + "\nM: " + MaleName;
    }
    public String countBabies(int start, int end, char letter, String gender){
        Process process = new Process(start, end);
        int CountNames = process.namesStartWith(letter, gender);
        int TotalBabies = process.totalCount(letter,gender);
        return "#OfNames: " + CountNames + "\n#OfBabies: " + TotalBabies;
    }
    public static void main(String[] args)
    {
        Output Test = new Output();
        System.out.println(Test.countBabies(1900,1905,'Q',"M"));
        System.out.println(Test.countBabies(1900,1905,'Q',"F"));
        System.out.println(Test.topRankedNames(1900, 1905));

    }
}
