package bug.tracker;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import bug.tracker.bugs.*;
import bug.tracker.csv.BugsCSV;

public class AddBugController implements Initializable {

    @FXML
    private TextField txtBugName, txtProjectName;
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
    private Button btnScreenshot;

    private BugsCSV bugsCsv = new BugsCSV();
    private String imagePath = "none";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboType.setItems(FXCollections.observableArrayList(BugType.values()));
        comboPriority.setItems(FXCollections.observableArrayList(BugPriority.values()));
        comboLevel.setItems(FXCollections.observableArrayList(BugLevel.values()));
    }

    @FXML
    private void handleAttachScreenshot(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Screenshot");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fc.showOpenDialog(stage);

        if (file != null) {
            imagePath = file.getAbsolutePath();
            if (btnScreenshot != null) {
                btnScreenshot.setText("File Selected!");
                btnScreenshot.setStyle("-fx-background-color: #dff29b; -fx-background-radius: 15;");
            }
        }
    }

    @FXML
    private void handleSubmitBug(ActionEvent event) {
        String date = (datePicker.getValue() != null)
                ? datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : java.time.LocalDate.now().toString();

        bugsCsv.saveBug(
                txtBugName.getText(),
                txtProjectName.getText(),
                comboType.getValue(),
                comboPriority.getValue(),
                comboLevel.getValue(),
                txtDescription.getText(),
                date,
                imagePath,
                "Admin",
                "Unassigned"
        );

        ((Stage) txtBugName.getScene().getWindow()).close();
    }
}
