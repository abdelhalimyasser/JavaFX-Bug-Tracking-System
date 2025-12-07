import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    public static String[] smartSplit(String line) {
        List<String> fields = new ArrayList<>();
        if (line == null || line.isEmpty()) return new String[0];
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i+1 < line.length() && line.charAt(i+1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        fields.add(cur.toString());
        for (int i = 0; i < fields.size(); i++) {
            fields.set(i, fields.get(i).trim());
        }
        return fields.toArray(new String[0]);
    }
}
