import java.util.*;
class DashboardService {
    public static void showDashboard(List<Bug> bugs){
        long open=StatisticsUtil.countOpen(bugs);
        long closed=StatisticsUtil.countClosed(bugs);
        System.out.println("===== PM DASHBOARD =====");
        System.out.println("Total Bugs : " + bugs.size());
        System.out.println("Open Bugs : " + open);
        System.out.println("Closed Bugs : " + closed);
        System.out.println("Open % : " + (open * 100 / Math.max(1, bugs.size())) + "%");
        System.out.println("Closed % : " + (closed * 100 / Math.max(1, bugs.size())) + "%");
    }
}
