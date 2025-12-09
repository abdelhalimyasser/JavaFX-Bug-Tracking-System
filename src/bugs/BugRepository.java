import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
class BugRepository {
    private final CSVMaster csv;
    public BugRepository(CSVMaster csv){
        this.csv=csv;
    }
    public List<Bug> loadAllBugs(){
        List<Bug> bugs=new ArrayList<>();
        try{
            List<String[]> rows=csv.readAll();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(String[]r:rows){
            if(r.length<11)
                continue;
            int id=safeParseInt(r[0],-1);
            String title=r[1];
            String type=r[2];
            String priority=r[3];
            String level=r[4];
            String project=r[5];
            String dueDate=r[6];
            String status=r[7];
            String assignedTo=r[8];
            String screenshotPath=r[9];
            LocalDateTime createdAt = LocalDateTime.parse(r[10], dtf);
            LocalDateTime fixedAt = r[11].equals("null") ? null : LocalDateTime.parse(r[11], dtf);
            Bug b = new Bug(id, title, type, priority, level, project, dueDate, status, assignedTo, screenshotPath, createdAt,fixedAt);
            bugs.add(b);
        }
        }
        catch(IOException e){
            System.out.print("System cannot read CSV:"+e.getMessage());
        }
        return bugs;
    }
    private int safeParseInt(String s,int fallback){
        try{
            return Integer.parseInt(s);
        }
        catch(Exception e){
            return fallback;
        }
    }
}
