package bug.tracker.bugs;

public class Bug {

    private int bugID;
    private String bugName;
    private String projectName;
    private BugType bugType;
    private BugPriority bugPriority;
    private BugLevel bugLevel;
    private String bugDescription;
    private String bugDate;
    private String bugPhoto;
    private String tester;
    private String developer;
    private boolean bugStatus;

    public Bug(int bugID, String bugName, String projectName,
            BugType bugType, BugPriority bugPriority, BugLevel bugLevel,
            String bugDescription, String bugDate, String bugPhoto,
            String tester, String developer) {

        this.bugID = bugID;
        this.bugName = bugName;
        this.projectName = projectName;
        this.bugType = bugType;
        this.bugPriority = bugPriority;
        this.bugLevel = bugLevel;
        this.bugDescription = bugDescription;
        this.bugDate = bugDate;
        this.bugPhoto = bugPhoto;
        this.tester = tester;
        this.developer = developer;
        this.bugStatus = false;
    }

    public int getBugID() {
        return bugID;
    }

    public String getBugName() {
        return bugName;
    }

    public String getProjectName() {
        return projectName;
    }

    public BugType getBugType() {
        return bugType;
    }

    public BugPriority getBugPriority() {
        return bugPriority;
    }

    public BugLevel getBugLevel() {
        return bugLevel;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public String getBugDate() {
        return bugDate;
    }

    public String getBugPhoto() {
        return bugPhoto;
    }

    public boolean getBugStatus() {
        return bugStatus;
    }

    public String getTester() {
        return tester;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setBugType(BugType bugType) {
        this.bugType = bugType;
    }

    public void setBugPriority(BugPriority bugPriority) {
        this.bugPriority = bugPriority;
    }

    public void setBugLevel(BugLevel bugLevel) {
        this.bugLevel = bugLevel;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    public void setBugDate(String bugDate) {
        this.bugDate = bugDate;
    }

    public void setBugPhoto(String bugPhoto) {
        this.bugPhoto = bugPhoto;
    }

    public void setBugStatus(boolean bugStatus) {
        this.bugStatus = bugStatus;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @Override
    public String toString() {
        return "ID: " + bugID + " Name: " + bugName;
    }
}
