package bug.tracker;

import java.io.File;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import bug.tracker.bugs.Bug;
import bug.tracker.csv.BugsCSV;
import bug.tracker.users.Developer;

public class DeveloperController implements Initializable {

    @FXML
    private TableView<Bug> assignedBugsTable;
    @FXML
    private TableColumn<Bug, String> colName;
    @FXML
    private TableColumn<Bug, String> colProject;
    @FXML
    private TableColumn<Bug, String> colDesc;
    @FXML
    private TableColumn<Bug, String> colStatus;
    @FXML
    private TableColumn<Bug, String> colPriority;
    @FXML
    private TableColumn<Bug, String> colDate;

    @FXML
    private TableView<Bug> completedBugsTable;
    @FXML
    private TableColumn<Bug, String> colCompName;
    @FXML
    private TableColumn<Bug, String> colCompProject;
    @FXML
    private TableColumn<Bug, String> colCompDesc;
    @FXML
    private TableColumn<Bug, String> colCompStatus;
    @FXML
    private TableColumn<Bug, String> colCompPriority;
    @FXML
    private TableColumn<Bug, String> colCompDate;

    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private Button updateStatusButton;
    @FXML
    private ImageView screenshotView;

    private Developer currentDev = new Developer();
    private BugsCSV bugsCsv = new BugsCSV();

    private ObservableList<Bug> openBugsList = FXCollections.observableArrayList();
    private ObservableList<Bug> closedBugsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        statusCombo.setItems(FXCollections.observableArrayList("Open", "Closed"));
        loadBugs();
        assignedBugsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadImage(newSelection.getBugPhoto());
            } else {
                setDefaultImage();
            }
        });
    }

    private void loadImage(String path) {
        try {
            if (path != null && !path.equals("none") && !path.isEmpty()) {
                File file = new File(path);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    screenshotView.setImage(image);
                } else {
                    setDefaultImage();
                }
            } else {
                setDefaultImage();
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            setDefaultImage();
        }
    }

    private void setDefaultImage() {
        try {
            Image defaultImg = new Image(getClass().getResourceAsStream("/images/bug-preview.png"));
            screenshotView.setImage(defaultImg);
        } catch (Exception e) {
            screenshotView.setImage(null);
        }
    }

    private void setupTableColumns() {
        colName.setCellValueFactory(new PropertyValueFactory<>("bugName"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("bugDescription"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("bugPriority"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("bugDate"));

        colCompName.setCellValueFactory(new PropertyValueFactory<>("bugName"));
        colCompProject.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colCompDesc.setCellValueFactory(new PropertyValueFactory<>("bugDescription"));
        colCompStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        colCompPriority.setCellValueFactory(new PropertyValueFactory<>("bugPriority"));
        colCompDate.setCellValueFactory(new PropertyValueFactory<>("bugDate"));
    }

    private void loadBugs() {
        openBugsList.clear();
        closedBugsList.clear();

        ArrayList<Bug> allBugs = bugsCsv.loadBugs();

        for (Bug b : allBugs) {
            if (b.getBugStatus()) {
                closedBugsList.add(b);
            } else {
                openBugsList.add(b);
            }
        }

        assignedBugsTable.setItems(openBugsList);
        completedBugsTable.setItems(closedBugsList);
    }

    @FXML
    public void updateStatusAction(ActionEvent event) {
        Bug selectedBug = assignedBugsTable.getSelectionModel().getSelectedItem();
        String newStatusStr = statusCombo.getValue();

        if (selectedBug == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a bug from the table.");
            return;
        }

        if (newStatusStr == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a status.");
            return;
        }

        boolean isClosed = newStatusStr.equalsIgnoreCase("Closed");
        currentDev.updateBugStatus(selectedBug.getBugID(), isClosed);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Bug status updated.");
        loadBugs();
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
