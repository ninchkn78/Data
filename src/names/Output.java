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
    public String getRanks(int start, int end, String name, String gender){
        Process process = new Process(start, end);
        return process.getRanks(name, gender);
    }
    public String getTodayName(int start, int end, String name, String gender){
        Process process1 = new Process(start,start);
        Process process2 = new Process(end, end);
        String compareRank = process1.getRanks(name, gender);
        compareRank = compareRank.replaceAll("\n", "");
        //if getRank couldn't find a name
        if (compareRank.charAt(0) == 'N'){
            return "Name not found" + "in year " + start;
        }
        else {
            int rankNum = Integer.parseInt(compareRank);
            return process2.getName(gender,rankNum);
        }


    }
    public static void main(String[] args)
    {
        Output Test = new Output();
        //System.out.println(Test.countBabies(1900,1905,'Q',"M"));
        //System.out.println(Test.countBabies(1900,1905,'Q',"F"));
        //System.out.println(Test.topRankedNames(1900, 1905));
        System.out.println(Test.getRanks(1900, 1910, "Mary","F"));
        System.out.println((Test.getTodayName(2000, 2001, "Alex", "F")));

    }
}
