package bugs;

public class Bug {
    private int bugId;
    private String name;
    private String projectName;
    private String type;
    private String priority;
    private String level;
    private String description;
    private String dateReported;
    private String status;
    private String testerUsername;
    private String developerUsername;
    private String screenshotPath;

    // Constructor
    public Bug(int bugId, String name, String projectName, String type, String priority,
               String level, String description, String dateReported, String status,
               String testerUsername, String developerUsername, String screenshotPath) {
        this.bugId = bugId;
        this.name = name;
        this.projectName = projectName;
        this.type = type;
        this.priority = priority;
        this.level = level;
        this.description = description;
        this.dateReported = dateReported;
        this.status = status;
        this.testerUsername = testerUsername;
        this.developerUsername = developerUsername;
        this.screenshotPath = screenshotPath;
    }

    // Empty Constructor
    public Bug() {}

    // Getters and Setters
    public int getBugId() { return bugId; }
    public void setBugId(int bugId) { this.bugId = bugId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateReported() { return dateReported; }
    public void setDateReported(String dateReported) { this.dateReported = dateReported; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTesterUsername() { return testerUsername; }
    public void setTesterUsername(String testerUsername) { this.testerUsername = testerUsername; }

    public String getDeveloperUsername() { return developerUsername; }
    public void setDeveloperUsername(String developerUsername) { this.developerUsername = developerUsername; }

    public String getScreenshotPath() { return screenshotPath; }
    public void setScreenshotPath(String screenshotPath) { this.screenshotPath = screenshotPath; }

    public String toCsvLine() {
        return bugId + "," +
                name + "," +
                projectName + "," +
                type + "," +
                priority + "," +
                level + "," +
                description.replace(",", ";") + "," +   // avoid breaking CSV
                dateReported + "," +
                status + "," +
                testerUsername + "," +
                developerUsername + "," +
                screenshotPath;
    }

    public static Bug fromCsvLine(String line) {
        String[] parts = line.split(",", -1); // -1 keeps empty fields

        Bug bug = new Bug();
        bug.setBugId(Integer.parseInt(parts[0]));
        bug.setName(parts[1]);
        bug.setProjectName(parts[2]);
        bug.setType(parts[3]);
        bug.setPriority(parts[4]);
        bug.setLevel(parts[5]);
        bug.setDescription(parts[6]);
        bug.setDateReported(parts[7]);
        bug.setStatus(parts[8]);
        bug.setTesterUsername(parts[9]);
        bug.setDeveloperUsername(parts[10]);
        bug.setScreenshotPath(parts[11]);

        return bug;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "bugId=" + bugId +
                ", name='" + name + '\'' +
                ", projectName='" + projectName + '\'' +
                ", type='" + type + '\'' +
                ", priority='" + priority + '\'' +
                ", level='" + level + '\'' +
                ", description='" + description + '\'' +
                ", dateReported='" + dateReported + '\'' +
                ", status='" + status + '\'' +
                ", testerUsername='" + testerUsername + '\'' +
                ", developerUsername='" + developerUsername + '\'' +
                ", screenshotPath='" + screenshotPath + '\'' +
                "}\n";
    }
}

