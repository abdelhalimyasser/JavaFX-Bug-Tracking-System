package bug.tracker;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import bug.tracker.bugs.Bug;
import bug.tracker.users.ProjectManager;

public class ProjectManagerController implements Initializable {

    @FXML
    private TableView<Bug> bugsTable;
    @FXML
    private TableColumn<Bug, Integer> colID;
    @FXML
    private TableColumn<Bug, String> colProject;
    @FXML
    private TableColumn<Bug, String> colSummary;
    @FXML
    private TableColumn<Bug, String> colStatus;
    @FXML
    private TableColumn<Bug, String> colPriority;
    @FXML
    private TableColumn<Bug, String> colDate;
    @FXML
    private TableColumn<Bug, String> colTester;
    @FXML
    private TableColumn<Bug, String> colDeveloper;

    @FXML
    private TableView<TesterStat> testersTable;
    @FXML
    private TableColumn<TesterStat, String> colPTester;
    @FXML
    private TableColumn<TesterStat, Integer> colPReported;
    @FXML
    private TableColumn<TesterStat, Integer> colPOpen;
    @FXML
    private TableColumn<TesterStat, Integer> colPClosed;

    @FXML
    private TableView<DevStat> developersTable;
    @FXML
    private TableColumn<DevStat, String> colPDev;
    @FXML
    private TableColumn<DevStat, Integer> colPAssigned;
    @FXML
    private TableColumn<DevStat, String> colPStatus;

    private ProjectManager currentPM = new ProjectManager();
    private ObservableList<Bug> bugList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colID.setCellValueFactory(new PropertyValueFactory<>("bugID"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colSummary.setCellValueFactory(new PropertyValueFactory<>("bugDescription"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("bugPriority"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("bugDate"));
        colTester.setCellValueFactory(new PropertyValueFactory<>("tester"));
        colDeveloper.setCellValueFactory(new PropertyValueFactory<>("developer"));
        colPDev.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPAssigned.setCellValueFactory(new PropertyValueFactory<>("assigned"));
        colPStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPTester.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPReported.setCellValueFactory(new PropertyValueFactory<>("total"));
        colPOpen.setCellValueFactory(new PropertyValueFactory<>("open"));
        colPClosed.setCellValueFactory(new PropertyValueFactory<>("closed"));
        loadDashboardData();
    }

    private void loadDashboardData() {
        bugList.clear();
        bugList.addAll(currentPM.getAllBugs());
        bugsTable.setItems(bugList);
        calculatePerformance();
    }

    private void calculatePerformance() {
        HashMap<String, TesterStat> testerMap = new HashMap<>();
        HashMap<String, DevStat> devMap = new HashMap<>();

        for (Bug b : bugList) {
            String testerName = b.getTester();
            if (testerName != null && !testerName.equals("Unknown")) {
                testerMap.putIfAbsent(testerName, new TesterStat(testerName));
                testerMap.get(testerName).addBug(b.getBugStatus());
            }
            String devName = b.getDeveloper();
            if (devName != null && !devName.equals("Unassigned")) {
                devMap.putIfAbsent(devName, new DevStat(devName));
                devMap.get(devName).addAssignment();
            }
        }

        testersTable.setItems(FXCollections.observableArrayList(testerMap.values()));
        developersTable.setItems(FXCollections.observableArrayList(devMap.values()));
    }

    @FXML
    public void sendToLoginAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class TesterStat {

        private String name;
        private int total = 0, open = 0, closed = 0;

        public TesterStat(String name) {
            this.name = name;
        }

        public void addBug(boolean isClosed) {
            total++;
            if (isClosed) {
                closed++;
            } else {
                open++;
            }
        }

        public String getName() {
            return name;
        }

        public int getTotal() {
            return total;
        }

        public int getOpen() {
            return open;
        }

        public int getClosed() {
            return closed;
        }
    }

    public static class DevStat {

        private String name;
        private int assigned = 0;

        public DevStat(String name) {
            this.name = name;
        }

        public void addAssignment() {
            assigned++;
        }

        public String getName() {
            return name;
        }

        public int getAssigned() {
            return assigned;
        }

        public String getStatus() {
            return "Active";
        }
    }
}
