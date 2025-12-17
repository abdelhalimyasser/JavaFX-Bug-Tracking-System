package bug.tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bug.tracker.auth.Register;

public class AddUserController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private RadioButton rbTester;
    @FXML
    private RadioButton rbDev;
    @FXML
    private RadioButton rbPM;
    @FXML
    private RadioButton rbAdmin;

    private ToggleGroup roleGroup;
    private Register register = new Register();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleGroup = new ToggleGroup();
        rbTester.setToggleGroup(roleGroup);
        rbDev.setToggleGroup(roleGroup);
        rbPM.setToggleGroup(roleGroup);
        rbAdmin.setToggleGroup(roleGroup);

        rbTester.setSelected(true);
    }

    private String getSelectedRole() {
        if (rbTester.isSelected()) {
            return "TESTER";
        }
        if (rbDev.isSelected()) {
            return "DEVELOPER";
        }
        if (rbPM.isSelected()) {
            return "PROJECT_MANAGER";
        }
        if (rbAdmin.isSelected()) {
            return "ADMIN";
        }
        return "TESTER";
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill in all fields.");
            return;
        }

        String firstNameData = txtFirstName.getText();
        String lastNameData = txtLastName.getText();
        String emailData = txtEmail.getText();
        String phoneData = txtPhone.getText();
        String passwordData = txtPassword.getText();

        String roleStr = getSelectedRole();

        boolean success = register.registerUser(firstNameData, lastNameData, phoneData, emailData, roleStr, passwordData);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "User could not be added.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }
}
