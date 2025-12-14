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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import bug.tracker.auth.Login;

public class LoginController implements Initializable {    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    
    private Login loginAuth = new Login();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void loginButton(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        String role = loginAuth.loginUser(email, password);

        if (role != null) {
            showAlert(Alert.AlertType.INFORMATION, "Login Success", "Welcome, " + role + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Email or Password.");
        }
    }

    @FXML
    public void sendToCreateAccountAction(ActionEvent event) {
        navigate(event, "createAccount.fxml", "Create Account");
    }

    @FXML
    public void sentToForgetPasswordAction(ActionEvent event) {
        navigate(event, "forgetPassword.fxml", "Forget Password");
    }

    // Helper method to handle navigation safely
    private void navigate(ActionEvent event, String fxmlFile, String title) {
        try {
            // Check if file exists first
            URL fileUrl = getClass().getResource(fxmlFile);
            if (fileUrl == null) {
                System.err.println("CRITICAL ERROR: FXML file not found: " + fxmlFile);
                showAlert(Alert.AlertType.ERROR, "System Error", "Missing file: " + fxmlFile);
                return;
            }

            Parent root = FXMLLoader.load(fileUrl);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("CRASH while loading " + fxmlFile);
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load " + title + " page.");
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}