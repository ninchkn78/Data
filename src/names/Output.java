package names;


import java.util.Collections;
import java.util.List;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Output {
    /**
     * Start of the program.
     */
    //change later to take in an entire source

    private int dataStartYear;
    private int dataEndYear;
    private Process process;
    private final String YEAR_ERROR = "YEAR NOT IN DATABASE";

    public Output (int start ,int end) {
        dataStartYear = start;
        dataEndYear = end;
        process = new Process(dataStartYear, dataEndYear);
    }
    public Output(int year){
        this(year, year);
    }
    public String topNames(int year){
        String maleName = process.getName(year,"M", 1);
        if (maleName.equals((YEAR_ERROR)) )return YEAR_ERROR;
        String femaleName = process.getName(year,"F", 1);
        return maleName + "\n" + femaleName;
    }
    public String countBabies(char letter, String gender){
        Process process = new Process(dataStartYear, dataEndYear);
        int CountNames = process.countNames(letter, gender);
        int TotalBabies = process.totalCount(letter,gender);
        return "#OfNames: " + CountNames + "\n#OfBabies: " + TotalBabies;
    }
    public List<String> getRanks(int start, int end, String name, String gender){
        Process process = new Process(dataStartYear, dataEndYear);
        return process.getRanks(name, gender);
    }
    //need to change start and end parameters to handle when
    //looking for prior year
    //also need some way to get a database ??
    public String getTodayName(int start, int end, String name, String gender){
        Process process1 = new Process(start);
        Process process2 = new Process(end);
        List<String> ranks = process1.getRanks(name, gender);
        String compareRank = ranks.get(0);
        compareRank = compareRank.replaceAll("\n", "");
        //if getRank couldn't find a name
        if (compareRank.charAt(0) == 'N'){
            return "Name not found" + "in year " + start;
        }
        else {
            int rankNum = Integer.parseInt(compareRank);
            return String.join(" ", process2.getNames(gender,rankNum));
        }

    }
    //how should this handle ties?

    public String mostPopularName(int start, int end, String gender){
        Process process = new Process(start,end);
        List<String> names = process.getNames(gender, 1);
        return process.mostFrequent(names);
    }
    public List<String> mostPopularLetter(int start, int end){
        Process process = new Process(start, end);
        List<String> letters = process.mostPopularLetters("F");
        if (letters.size() > 2) {
            Collections.sort(letters);
            return letters;
        }
        char letter = letters.remove(0).charAt(0);
        return process.namesStartWith(letter, "F");


    }

    public static void main(String[] args)
    {
        Output Test = new Output(1900);
        System.out.println(Test.topNames(1990));
//        System.out.println(Test.countBabies(1985,1985,'R',"M"));
//        System.out.println(Test.countBabies(1900,1905,'Q',"F"));
//        System.out.println(Test.getRanks(2001, 2001, "Alex","M"));
//        System.out.println((Test.getTodayName(1965, 2001, "Janet", "F")));
//        System.out.println(Test.mostPopularName(2001,2001,"F"));
//        System.out.println(Test.mostPopularLetter(1900, 1910));
//        System.out.println(Test.mostPopularLetter(1900,1925));
    }
}
