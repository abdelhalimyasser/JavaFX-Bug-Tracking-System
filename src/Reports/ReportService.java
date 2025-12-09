import java.util.*;
class ReportService {
    public static void ShowReports(List<Bug>bugs){
        System.out.println("=====Bugs Per Person=====");
        StatisticsUtil.bugsPerPerson(bugs).forEach((dev,count)->System.out.println(dev+":"+count));
        System.out.println("=====Average Fix Time=====");
        System.out.println(StatisticsUtil.avgFixTimeHours(bugs)+"Hours");
        System.out.println("=====Recent Bugs=====");
        for (Bug b : StatisticsUtil.recentBugs(bugs)) {
            System.out.println("#" + b.id + " | " + b.title + " | " + b.createdAt);
        }
    }
}
