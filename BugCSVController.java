import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete controller for Bug CSV, extends CSVBase demonstrating Inheritance & Composition.
 */
public class BugCSVController extends CSVBase<Bug> {

    public BugCSVController(String filePath) {
        super(filePath, Bug.csvHeader());
    }

    @Override
    protected Bug fromCsvLine(String line) {
        return Bug.fromCsvLine(line);
    }

    @Override
    protected String toCsvLine(Bug obj) {
        return obj.toCsvLine();
    }

    public synchronized List<Bug> getAllBugs() {
        try {
            List<String> lines = readAllLines();
            List<Bug> res = new ArrayList<>();
            int start = 0;
            if (!lines.isEmpty() && lines.get(0).startsWith("id,")) start = 1;
            for (int i = start; i < lines.size(); i++) {
                Bug b = fromCsvLine(lines.get(i));
                if (b != null) res.add(b);
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException("Error reading bugs: " + e.getMessage(), e);
        }
    }

    public synchronized int getNextId() {
        List<Bug> all = getAllBugs();
        int max = 0;
        for (Bug b : all) {
            if (b.getId() > max) max = b.getId();
        }
        return max + 1;
    }

    public synchronized Bug addBug(Bug b) {
        try {
            if (b.getId() == 0) {
                b = new Bug(getNextId(), b.getTitle(), b.getType(), b.getPriority(), b.getLevel(),
                        b.getProjectName(), b.getDueDate(), b.getStatus(), b.getAssignedTo(), b.getScreenshotPath());
            }
            appendLine(toCsvLine(b));
            return b;
        } catch (IOException e) {
            throw new RuntimeException("Failed to append bug: " + e.getMessage(), e);
        }
    }

    public synchronized boolean updateBug(Bug updated) {
        try {
            List<String> lines = readAllLines();
            List<String> out = new ArrayList<>();
            int start = 0;
            if (!lines.isEmpty() && lines.get(0).startsWith("id,")) {
                out.add(lines.get(0)); // header
                start = 1;
            }
            boolean found = false;
            for (int i = start; i < lines.size(); i++) {
                Bug b = fromCsvLine(lines.get(i));
                if (b != null && b.getId() == updated.getId()) {
                    out.add(toCsvLine(updated));
                    found = true;
                } else {
                    out.add(lines.get(i));
                }
            }
            if (!found) return false;
            writeAllLines(out);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to update bug: " + e.getMessage(), e);
        }
    }

    public synchronized boolean deleteById(int id) {
        try {
            List<String> lines = readAllLines();
            List<String> out = new ArrayList<>();
            int start = 0;
            if (!lines.isEmpty() && lines.get(0).startsWith("id,")) {
                out.add(lines.get(0)); start = 1;
            }
            boolean found = false;
            for (int i = start; i < lines.size(); i++) {
                Bug b = fromCsvLine(lines.get(i));
                if (b != null && b.getId() == id) {
                    found = true;
                    continue;
                } else {
                    out.add(lines.get(i));
                }
            }
            if (!found) return false;
            writeAllLines(out);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete bug: " + e.getMessage(), e);
        }
    }

    public synchronized Bug findById(int id) {
        List<Bug> all = getAllBugs();
        for (Bug b : all) if (b.getId() == id) return b;
        return null;
    }
}
