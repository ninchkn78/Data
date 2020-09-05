package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class FileReader {

  private static int getYear(String path) {
    // Replacing every non-digit number
    path = path.replaceAll("[^\\d]", "");
    if (path.equals("")) {
      return -1;
    }
    return Integer.parseInt(path);
  }

  public static Map<Integer, List<String[]>> generateMap(String dataSource, String dataType) {
    int year;
    Map<Integer, List<String[]>> dataSet = new TreeMap<>();
    File[] textFiles = handleDataType(dataSource, dataType);
    for (File file : textFiles) {
      year = getYear(file.getName());
      dataSet.put(year, generateList(file));
    }
    return dataSet;
  }

  private static File[] handleDataType(String dataSource, String dataType) {
    if (dataType.equals("FOLDER")) {
      Path path;
      try {
        path = Paths
            .get(Objects.requireNonNull(FileReader.class.getClassLoader().getResource(dataSource))
                .toURI());
        File folder = new File(String.valueOf(path));
        return folder.listFiles();
      } catch (URISyntaxException e) {
        System.out.println("INVALID DATA SOURCE");
        System.exit(-1);
      }
    }
    return null;
  }

  private static List<String[]> generateList(File textFile) {
    List<String[]> profiles = new ArrayList<>();
    String[] profile;
    Scanner scan = null;
    try {
      scan = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      System.out.println("INVALID DATA SOURCE");
      System.exit(-1);
    }
    while (scan.hasNextLine()) {
      profile = scan.nextLine().split(",");
      profiles.add(profile);
    }
    return profiles;
  }
}

