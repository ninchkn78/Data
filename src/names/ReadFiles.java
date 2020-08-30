package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;


public class ReadFiles {

    private int getYear(String path) {
        // Replacing every non-digit number
        path = path.replaceAll("[^\\d]", "");
        if (path.equals("")) return -1;
        return Integer.parseInt(path);
    }

    public Map<Integer, List<String[]>> generateMap(String folderName) {
        int year;
        Map<Integer, List<String[]>> dataSet = new TreeMap<>();
        try {
            Path path = Paths.get(Objects.requireNonNull(ReadFiles.class.getClassLoader().getResource(folderName)).toURI());
            File folder = new File(String.valueOf(path));
            File[] textFiles = folder.listFiles();
            for (File file : textFiles) {
                year = getYear(file.getName());
                dataSet.put(year, generateList(file));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return dataSet;
    }
    private List<String[]> generateList(File textFile) throws FileNotFoundException {
        List<String[]> profiles = new ArrayList<>();
        String[] profile;
        Scanner scan = new Scanner(textFile);
        while (scan.hasNextLine()) {
            profile = scan.nextLine().split(",");
            profiles.add(profile);
                }
        return profiles;
    }
}

