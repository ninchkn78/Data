package names;


import java.util.List;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Output {
    /**
     * Start of the program.
     */
    public String topNames(int start){
        Process process = new Process(start);
        List<String> names = process.getNames("M", 1);
        names.addAll(process.getNames("F", 1));
        return String.join("\n", names);
    }
    public String countBabies(int start, int end, char letter, String gender){
        Process process = new Process(start, end);
        int CountNames = process.namesStartWith(letter, gender);
        int TotalBabies = process.totalCount(letter,gender);
        return "#OfNames: " + CountNames + "\n#OfBabies: " + TotalBabies;
    }
    public List<String> getRanks(int start, int end, String name, String gender){
        Process process = new Process(start, end);
        return process.getRanks(name, gender);
    }
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
        if(letters.size() > 2){
            return
        }

    }

    public static void main(String[] args)
    {
        Output Test = new Output();
        //System.out.println(Test.countBabies(1900,1905,'Q',"M"));
        //System.out.println(Test.countBabies(1900,1905,'Q',"F"));
        //System.out.println(Test.topNames(1900));
        //System.out.println(Test.getRanks(1900, 1910, "Mary","F"));
        //System.out.println((Test.getTodayName(2000, 2001, "Alex", "F")));
        //System.out.println(Test.mostPopularName(2000,2010,"M"));
        System.out.println(Test.mostPopularLetter(1900, 1910));

    }
}
