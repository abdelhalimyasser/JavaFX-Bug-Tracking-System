import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
class StatisticsUtil {
    public static long countOpen(List<Bug> bugs){
        return bugs.stream().filter(b->"open".equalsIgnoreCase(b.status)).count();
    }
    public static long countClosed(List<Bug> bugs){
        return bugs.stream().filter(b->"closed".equalsIgnoreCase(b.status)).count();
    }
    public static Map<String,Long> bugsPerPerson(List<Bug> bugs){
        return bugs.stream().collect(Collectors.groupingBy(b->(b.assignedTo),Collectors.counting()));
    }
    public static double avgFixTimeHours(List<Bug> bugs){
        List<Long> durations=bugs.stream().filter(b->b.status.equals("closed")&&b.fixedAt!=null).map(b->Duration.between(b.createdAt,b.fixedAt)
                .toHours()).collect(Collectors.toList());
        if(durations.isEmpty())
            return 0;
        return durations.stream().mapToLong(l -> l).average().orElse(0);
    }
    public static List<Bug> recentBugs(List<Bug> bugs){
        return bugs.stream().sorted((a,b)->b.createdAt.compareTo(a.createdAt)).limit(5).collect(Collectors.toList());
    }
    }
