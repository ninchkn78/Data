package names;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DataReader {

  private final static String FILE_PREFIX = "yob";
  public final static String DATA_SOURCE_ERROR = "INVALID DATA SOURCE";
  private final static int FILE_NAME_ERROR = -1;

  //assuming file name only contains numbers that represent a year, gets the year as an int
  private static int getYear(String fileName) {
    // Replacing every non-digit number
    fileName = fileName.replaceAll("[^\\d]", "");
    if (fileName.equals("")) {
      return FILE_NAME_ERROR;
    }
    return Integer.parseInt(fileName);
  }

  //given a data source, returns a map with years as keys and data as values
  public static Map<Integer, List<String[]>> generateBabyNamesDataSet(String dataSource, String dataType) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    return switch (dataType) {
      case "FOLDER" -> generateMapFromFolder(dataSource);
      case "URL" -> generateMapFromURL(dataSource);
      case "LOCAL_ZIP" -> generateMapFromZIP(dataSource, "LOCAL");
      case "URL_ZIP" -> generateMapFromZIP(dataSource, "URL");
      default -> dataSet;
    };
  }

  //given a data source of meanings, returns names mapped to meanings
  public static Map<String, String> generateNamesMeaningsMap(String gender, String dataSource){
    File meanings = createFileFromLocalSource(dataSource);
    Map<String, String> nameMeanings = new HashMap<>();
    Scanner scan;
    try {
      scan = new Scanner(meanings);
      String line, name, meaning;
      int nameEndIndex, genderIndex, meaningStartIndex;
      while (scan.hasNextLine()) {
        line = scan.nextLine();
        nameEndIndex = line.indexOf(" ");
        //assumes the gender is one space after the name
        genderIndex = nameEndIndex + 1;
        //assumes the meaning starts one space after the gender
        meaningStartIndex = genderIndex + 2;
        String nameGender = line.substring(genderIndex,genderIndex + 1);
        if(nameGender.toUpperCase().equals(gender)){
          name = line.substring(0,nameEndIndex);
          meaning = line.substring(meaningStartIndex);
          nameMeanings.put(name,meaning);
        }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return nameMeanings;
  }

  //given a text file as an input stream, makes an List with each line as an array of information
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

  //given a folder, makes map with years as keys and yearData as values
  private static Map<Integer, List<String[]>> generateMapFromFolder(String dataSource) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    File[] textFiles = createFileFromLocalSource(dataSource).listFiles();
    int year;
    if (textFiles != null) {
      for (File file : textFiles) {
        year = getYear(file.getName());
        InputStream test = null;
        try {
          test = new FileInputStream(file);
        } catch (FileNotFoundException e) {
          System.out.println(DATA_SOURCE_ERROR);
        }
        dataSet.put(year, generateList(test));
      }
    }
    return dataSet;
  }

  //given a URL, makes map with years as keys and yearData as values
  private static Map<Integer, List<String[]>> generateMapFromURL(String dataSource) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    try {
      URL source = new URL(dataSource);
      BufferedReader in = new BufferedReader(new InputStreamReader((source.openStream())));
      int year, startIndex, endIndex;
      String line, fileName;
      while ((line = in.readLine()) != null) {
        startIndex = line.indexOf("href=\"" + FILE_PREFIX);
        startIndex = line.indexOf(FILE_PREFIX, startIndex);
        endIndex = line.indexOf("\"", startIndex);
        if (startIndex > 0) {
          fileName = line.substring(startIndex, endIndex);
          year = getYear(fileName);
          dataSet.put(year, generateList(new URL(dataSource + fileName).openStream()));
        }
      }
    } catch (IOException e) {
      System.out.println(DATA_SOURCE_ERROR);
    }
    return dataSet;
  }

  //given a ZIP source, makes map with years as keys and yearData as values
  //ASSUMES THAT ALL FILES WITH NUMBERS ARE TEXT FILES WITH DATA BASED ON YEAR
  private static Map<Integer, List<String[]>> generateMapFromZIP(String dataSource,
      String dataType) {
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    try {
      ZipInputStream zipStream = getZipStream(dataSource, dataType);
      ZipEntry entry;
      if (zipStream != null) {
        while ((entry = zipStream.getNextEntry()) != null) {
          InputStream textFile = new ZippedFileInputStream(zipStream);
          if (getYear(entry.getName()) != FILE_NAME_ERROR) {
            dataSet.put(getYear(entry.getName()), generateList(textFile));
          }
        }
      }
    } catch (IOException e) {
      System.out.println(DATA_SOURCE_ERROR);
    }
    return dataSet;
  }

  //makes a file object given the name of the file
  private static File createFileFromLocalSource(String dataSource) {
    Path path = null;
    try {
      path = Paths
          .get(Objects.requireNonNull(DataReader.class.getClassLoader().getResource(dataSource))
              .toURI());
    } catch (NullPointerException | URISyntaxException e) {
      System.out.println(DATA_SOURCE_ERROR);
    }
    return new File(String.valueOf(path));
  }

  //makes an ZipInputStream, given the name of the dataSource and if it's local or from a URL
  private static ZipInputStream getZipStream(String dataSource, String dataType) {
    try {
      if (dataType.equals("URL")) {
        URL source = new URL(dataSource);
        return new ZipInputStream(
            source.openStream());
      } else if (dataType.equals("LOCAL")) {
            File zipFile = createFileFromLocalSource(dataSource);
            return new ZipInputStream(new FileInputStream(zipFile));
      }
    } catch (IOException e) {
      System.out.println(DATA_SOURCE_ERROR);
    }
    return null;
  }
  public static void main(String[] args) {
    //System.out.println(DataReader.createFileFromLocalSource("names.zip"));
    DataReader.getZipStream("names.zip","LOCAL");
  }

}

