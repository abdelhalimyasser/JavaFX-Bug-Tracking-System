import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
class Bug {
    public int id;
    public String title;
    public String type;
    public String priority;
    public String level;
    public String projects;
    public String dueDate;
    public String status;
    public String assignedTo;
    public String screenshotPath;
    public LocalDateTime createdAt;
    public LocalDateTime fixedAt;
    public Bug(int id,String title,String type,String priority ,String level,String projects,String dueDate,String status,String assignedTo,String screenshotPath,LocalDateTime createdAt,LocalDateTime fixedAt){
        this.id=id;
        this.title=title;
        this.type=type;
        this.priority=priority;
        this.level=level;
        this.projects=projects;
        this.dueDate=dueDate;
        this.status=status;
        this.screenshotPath=screenshotPath;
        this.createdAt=createdAt;
        this.fixedAt=fixedAt;
    }
}
