package bugs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BugsService {
    private final static String BUGS_CSV_PATH = "./bugs.csv";

    public BugsService() {
    }

    public List<Bug> getAllBugs() {
        try {
            List<String> lines = Files.readAllLines(Path.of(BUGS_CSV_PATH));
            List<Bug> bugs = new ArrayList<>();
            for (String line : lines) {
                bugs.add(Bug.fromCsvLine(line));
            }
            return bugs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
