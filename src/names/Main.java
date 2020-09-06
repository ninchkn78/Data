package names;

public class Main {
  public static void main(String[] args) {
    // Outputter Test = new Outputter("https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_complete/", "URL");
    Outputter Test = new Outputter("ssa_complete", "FOLDER");
    //Outputter Test = new Outputter("https://www.ssa.gov/oact/babynames/names.zip", "URL_ZIP");
    //Outputter Test = new Outputter("names.zip", "LOCAL_ZIP");
    System.out.println(Test.topMaleAndFemaleName(1990, false));
    System.out.println(Test.countNamesAndBabies(1900, "R", "M"));
    System.out.println(Test.ranksFromDataset("Megan","F"));
    System.out.println(Test.todayName(2000,"Megan","F", false));
    System.out.println(Test.mostPopularNames(1880,2018,"F"));
    System.out.println(Test.mostPopularFemaleStartingLetter(1880,2018));
    System.out.println(Test.ranksFromRange(1880,2018,"Megan","F"));
    System.out.println(Test.rankChange(1880,2018,"Megan","F"));
    System.out.println(Test.namesWithBiggestRankChange(1880,2018,"F"));
    System.out.println(Test.averageRank(1880,2018,"Megan","F"));
    System.out.println(Test.recentAverageRank(25,"Megan","F"));
    System.out.println(Test.namesWithHighestAverageRank(1880,2018,"F"));
    System.out.println(Test.namesWithRank(2000,2018,"F",5));
    System.out.println(Test.mostFrequentRankedNames(1880,2018,"F",5));
    System.out.println(Test.mostCommonPrefix(1880,2018,"M"));
  }
}
