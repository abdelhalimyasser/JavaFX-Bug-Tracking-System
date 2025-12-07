import java.time.LocalDateTime;

public class Bug extends Entity {
    private String title, type, priority, level, projectName, status, assignedTo, screenshotPath;
    private LocalDateTime dueDate;

    public Bug(int id, String title, String type, String priority, String level,
               String projectName, LocalDateTime dueDate, String status,
               String assignedTo, String screenshotPath) {
        super(id);
        this.title = title;
        this.type = type;
        this.priority = priority;
        this.level = level;
        this.projectName = projectName;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedTo = assignedTo;
        this.screenshotPath = screenshotPath;
    }



    // Getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getScreenshotPath() { return screenshotPath; }
    public void setScreenshotPath(String screenshotPath) { this.screenshotPath = screenshotPath; }

    public String toCsvLine() {
        return String.join(",",
                String.valueOf(id),
                quoteIfNeeded(title),
                quoteIfNeeded(type),
                quoteIfNeeded(priority),
                quoteIfNeeded(level),
                quoteIfNeeded(projectName),
                quoteIfNeeded(DateTimeUtil.formatOpt(dueDate)),
                quoteIfNeeded(status),
                quoteIfNeeded(assignedTo),
                quoteIfNeeded(screenshotPath),
                quoteIfNeeded(DateTimeUtil.format(createdAt))
        );
    }

    public static String csvHeader() {
        return "id,title,type,priority,level,project,dueDate,status,assignedTo,screenshotPath,createdAt";
    }

    private static String quoteIfNeeded(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            String escaped = field.replace("\"", "\"\"");
            return "\"" + escaped + "\"";
        } else {
            return field;
        }
    }

    public static Bug fromCsvLine(String line) {
        String[] parts = CSVUtils.smartSplit(line);
        String[] p = new String[11];
        for (int i = 0; i < 11; i++) p[i] = (i < parts.length) ? parts[i] : "";
        try {
            int id = Integer.parseInt(p[0].isEmpty() ? "0" : p[0]);
            String title = p[1];
            String type = p[2];
            String priority = p[3];
            String level = p[4];
            String project = p[5];
            java.time.LocalDateTime due = DateTimeUtil.parseOpt(p[6]);
            String status = p[7];
            String assignedTo = p[8];
            String screenshot = p[9];
            return new Bug(id, title, type, priority, level, project, due, status, assignedTo, screenshot);
        } catch (Exception ex) {
            System.err.println("Warning: failed to parse bug csv line -> " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Bug{id=%d, title=%s, assignedTo=%s, status=%s}", id, title, assignedTo, status);
    }
}
