package bug.tracker;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import bug.tracker.bugs.Bug;
import bug.tracker.bugs.BugLevel;
import bug.tracker.bugs.BugPriority;
import bug.tracker.bugs.BugType;
import bug.tracker.csv.BugsCSV;
import bug.tracker.users.Tester;

public class TesterController implements Initializable {

    @FXML
    private TextField txtBugName;
    @FXML
    private TextField txtProjectName;
    @FXML
    private ComboBox<BugType> comboType;
    @FXML
    private ComboBox<BugPriority> comboPriority;
    @FXML
    private ComboBox<BugLevel> comboLevel;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label lblScreenshotStatus;

    @FXML
    private TableView<Bug> myBugsTable;
    @FXML
    private TableColumn<Bug, String> colName;
    @FXML
    private TableColumn<Bug, String> colProject;
    @FXML
    private TableColumn<Bug, String> colStatus;
    @FXML
    private TableColumn<Bug, String> colPriority;
    @FXML
    private TableColumn<Bug, String> colDate;
    private Tester currentTester = new Tester();
    private BugsCSV bugsCsv = new BugsCSV();
    private ObservableList<Bug> bugList = FXCollections.observableArrayList();
    private String selectedImagePath = "none";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboType.setItems(FXCollections.observableArrayList(BugType.values()));
        comboPriority.setItems(FXCollections.observableArrayList(BugPriority.values()));
        comboLevel.setItems(FXCollections.observableArrayList(BugLevel.values()));

        colName.setCellValueFactory(new PropertyValueFactory<>("bugName"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("bugPriority"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("bugDate"));

        loadBugsToTable();
    }

    @FXML
    public void attachScreenshotAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Screenshot");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            lblScreenshotStatus.setText(selectedFile.getName());
            lblScreenshotStatus.setStyle("-fx-text-fill: green;");
        } else {
            lblScreenshotStatus.setText("No file selected");
        }
    }

    @FXML
    public void submitBugAction(ActionEvent event) {
        if (txtBugName.getText().isEmpty() || comboType.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields.");
            return;
        }

        String name = txtBugName.getText();
        String project = txtProjectName.getText();
        BugType type = comboType.getValue();
        BugPriority priority = comboPriority.getValue();
        BugLevel level = comboLevel.getValue();
        String desc = txtDescription.getText();
        String date = (datePicker.getValue() != null)
                ? datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : java.time.LocalDate.now().toString();

        currentTester.addBug(name, project, type, priority, level, desc, date, selectedImagePath);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Bug Reported Successfully!");

        loadBugsToTable();
        clearForm();
    }

    private void loadBugsToTable() {
        ArrayList<Bug> bugs = bugsCsv.loadBugs();
        bugList.setAll(bugs);
        myBugsTable.setItems(bugList);
    }

    private void clearForm() {
        txtBugName.clear();
        txtProjectName.clear();
        txtDescription.clear();
        comboType.getSelectionModel().clearSelection();
        comboPriority.getSelectionModel().clearSelection();
        comboLevel.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        selectedImagePath = "none";
        lblScreenshotStatus.setText("No file selected");
        lblScreenshotStatus.setStyle("-fx-text-fill: black;");
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

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
