package bug.tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import bug.tracker.auth.Register;

public class CreateAccountController implements Initializable {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton testerRadio;
    @FXML
    private RadioButton developerRadio;
    @FXML
    private RadioButton pmRadio;
    @FXML
    private RadioButton adminRadio;

    private ToggleGroup roleGroup;
    private Register register = new Register();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleGroup = new ToggleGroup();
        testerRadio.setToggleGroup(roleGroup);
        developerRadio.setToggleGroup(roleGroup);
        pmRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
    }

    private String getSelectedRole() {
        if (testerRadio.isSelected()) {
            return "TESTER";
        }
        if (developerRadio.isSelected()) {
            return "DEVELOPER";
        }
        if (pmRadio.isSelected()) {
            return "PROJECT_MANAGER";
        }
        if (adminRadio.isSelected()) {
            return "ADMIN";
        }
        return null;
    }

    @FXML
    public void sendToCreateAccountAction(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String role = getSelectedRole();

        if (role == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a job role.");
            return;
        }

        boolean success = register.registerUser(firstName, lastName, phone, email, role, password);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created! Please Login.");
            try {
                sendToLoginAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed", "Registration failed. Check inputs.");
        }
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
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load Login page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
