package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class ReadFiles {
    private String fileName;



    private int getYear(String path) {
        // Replacing every non-digit number
        // with a space(" ")

        path = path.replaceAll("[^\\d]", "");

        if (path.equals("")) return -1;

        return Integer.parseInt(path);
    }

    //    public Map<Integer, List> listAllFiles(String path){
//        System.out.println("In listAllfiles(String path) method");
//        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
//            paths.forEach(filePath -> {
//                if (Files.isRegularFile(filePath)) {
//                    try {
//                        generateList(filePath);
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    //TO-DO: generalize this to be able to handle URL or different file locations/naming conventions
    private void getFileName(int year) {
        fileName = "yob" + year + ".txt";
    }

    //change data structure later
//    public List<String[]> generateList(Path path) {
//        List<String[]> profiles = new ArrayList<>();
//        String[] profile;
//        try {
//            for (String line : Files.readAllLines(path)) {
//                profile = line.split(",");
//                profiles.add(profile);
//            }
//            return profiles;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            System.out.println("That year is not in the database");
//            System.exit(0);
//        }
//        return profiles;
//    }

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

    public static void main(String[] args) throws Exception{

        ReadFiles test = new ReadFiles();
        test.generateMap("ssa_2000s");


    }
}

