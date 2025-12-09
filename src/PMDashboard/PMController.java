import java.util.*;
class PMController {
    private List<Bug> bugs;
    private BugRepository repo;
    
    public PMController(String csvPath){
        CSVMaster csv = new CSVMaster(csvPath,true);
        this.repo=new BugRepository(csv);
        this.bugs=repo.loadAllBugs();
    }
        public void showPMModule() {
            System.out.println("\n===== PM DASHBOARD =====");
            DashboardService.showDashboard(bugs);
            System.out.println("\n===== PM REPORTS =====");
             ReportService.ShowReports(bugs);
    }
}
