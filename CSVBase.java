import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract generic CSV base class demonstrating Abstraction and code reuse.
 */
public abstract class CSVBase<T> {
    protected Path filePath;
    protected String header;

    public CSVBase(String filePathStr, String header) {
        this.filePath = Paths.get(filePathStr);
        this.header = header;
        ensureFileExists();
    }

    protected void ensureFileExists() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent() == null ? Paths.get(".") : filePath.getParent());
                try (BufferedWriter w = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
                    if (header != null && !header.isEmpty()) {
                        w.write(header);
                        w.newLine();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to create CSV file: " + filePath + " -> " + e.getMessage(), e);
        }
    }

    protected List<String> readAllLines() throws IOException {
        ensureFileExists();
        List<String> all = Files.readAllLines(filePath);
        List<String> clean = new ArrayList<>();
        for (String s : all) if (s != null && !s.trim().isEmpty()) clean.add(s);
        return clean;
    }

    protected void writeAllLines(List<String> lines) throws IOException {
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    protected void appendLine(String line) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            w.write(line);
            w.newLine();
        }
    }

    protected abstract T fromCsvLine(String line);
    protected abstract String toCsvLine(T obj);
}
