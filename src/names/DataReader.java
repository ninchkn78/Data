package names;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import javax.xml.crypto.Data;

public class DataReader {

  private final static String FILE_PREFIX = "yob";

  private static int getYear(String fileName) {
    // Replacing every non-digit number
    fileName = fileName.replaceAll("[^\\d]", "");
    if (fileName.equals("")) {
      return -1;
    }
    return Integer.parseInt(fileName);
  }

  public static Map<Integer, List<String[]>> generateMap(String dataSource, String dataType) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    if (dataType.equals("FOLDER")) {
      return generateMapFromFolder(dataSource);
    }
    else if (dataType.equals("URL")){
      return generateMapFromURL(dataSource);
    }
    return dataSet;
  }

  private static List<String[]> generateList(InputStream textFile) {
    List<String[]> profiles = new ArrayList<>();
    String[] profile;
    Scanner scan;
    scan = new Scanner(textFile);
    while (scan.hasNextLine()) {
      profile = scan.nextLine().split(",");
      profiles.add(profile);
    }
    return profiles;
  }

  public static Map<Integer, List<String[]>> generateMapFromFolder(String dataSource) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    try {
      Path path = Paths
          .get(Objects.requireNonNull(DataReader.class.getClassLoader().getResource(dataSource))
              .toURI());
      File folder = new File(String.valueOf(path));
      File[] textFiles = folder.listFiles();
      int year;
      for (File file : textFiles) {
        year = getYear(file.getName());
        InputStream test = new FileInputStream(file);
        dataSet.put(year, generateList(test));
      }
      return dataSet;

      //README
    } catch (URISyntaxException | FileNotFoundException e) {
      System.out.println("FOLDER NOT FOUND");
      System.exit(-1);
    }
    return dataSet;
  }


  public static Map<Integer, List<String[]>> generateMapFromURL(String dataSource) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    try {
      URL source = new URL(dataSource);
      BufferedReader in = new BufferedReader(new InputStreamReader((source.openStream())));
      int year;
      int startIndex;
      int endIndex;
      String line;
      String fileName;
      while ((line = in.readLine()) != null) {
       startIndex = line.indexOf("href=\"" + FILE_PREFIX);
       startIndex = line.indexOf(FILE_PREFIX, startIndex);
       endIndex = line.indexOf("\"", startIndex);
       if (startIndex > 0) {
         fileName = line.substring(startIndex, endIndex);
         year = getYear(fileName);
         dataSet.put(year, generateList(new URL(dataSource+ fileName).openStream()));
       }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataSet;
  }
  public static void main(String[] args) {
    DataReader.generateMapFromURL("https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_complete/");
  }
}

