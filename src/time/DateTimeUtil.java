import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String format(LocalDateTime dt) {
        return dt == null ? "" : dt.format(FMT);
    }

    public static String formatOpt(LocalDateTime dt) {
        return format(dt);
    }

    public static LocalDateTime parseOpt(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            return LocalDateTime.parse(s, FMT);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(s + " 00:00", FMT);
            } catch (Exception ex) {
                System.err.println("Warning: failed to parse date '" + s + "'");
                return null;
            }
        }
    }
}

