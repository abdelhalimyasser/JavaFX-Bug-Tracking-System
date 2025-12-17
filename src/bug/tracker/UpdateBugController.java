package bug.tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bug.tracker.bugs.*;
import bug.tracker.csv.BugsCSV;

public class UpdateBugController implements Initializable {

    @FXML
    private TextField txtBugName, txtProjectName;
    @FXML
    private ComboBox<BugType> comboType;
    @FXML
    private ComboBox<BugPriority> comboPriority;
    @FXML
    private ComboBox<BugLevel> comboLevel;
    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private TextArea txtDescription;

    private Bug currentBug;
    private BugsCSV bugsCsv = new BugsCSV();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboType.setItems(FXCollections.observableArrayList(BugType.values()));
        comboPriority.setItems(FXCollections.observableArrayList(BugPriority.values()));
        comboLevel.setItems(FXCollections.observableArrayList(BugLevel.values()));
        comboStatus.setItems(FXCollections.observableArrayList("Open", "Closed"));
    }

    public void initData(Bug bug) {
        this.currentBug = bug;
        txtBugName.setText(bug.getBugName());
        txtProjectName.setText(bug.getProjectName());
        txtDescription.setText(bug.getBugDescription());
        comboType.setValue(bug.getBugType());
        comboPriority.setValue(bug.getBugPriority());
        comboLevel.setValue(bug.getBugLevel());
        comboStatus.setValue(bug.getBugStatus() ? "Closed" : "Open");
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        currentBug.setBugName(txtBugName.getText());
        currentBug.setProjectName(txtProjectName.getText());
        currentBug.setBugDescription(txtDescription.getText());
        currentBug.setBugType(comboType.getValue());
        currentBug.setBugPriority(comboPriority.getValue());
        currentBug.setBugLevel(comboLevel.getValue());

        String statusStr = comboStatus.getValue();
        currentBug.setBugStatus(statusStr != null && statusStr.equalsIgnoreCase("Closed"));
        bugsCsv.updateFullBug(currentBug);
        ((Stage) txtBugName.getScene().getWindow()).close();
    }
}
