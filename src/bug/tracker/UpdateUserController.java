package bug.tracker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bug.tracker.users.User;
import bug.tracker.users.Role;
import bug.tracker.csv.UsersCSV;

public class UpdateUserController implements Initializable {
    @FXML private TextField firstNameField, lastNameField, emailField, phoneField;
    @FXML private ChoiceBox<Role> roleChoiceBox;
    @FXML private Button updateButton;
    private User currentUser;
    private UsersCSV usersCsv = new UsersCSV();

    @Override public void initialize(URL url, ResourceBundle rb) {
        roleChoiceBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    public void initData(User user) {
        this.currentUser = user;
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        roleChoiceBox.setValue(user.getRole());
    }

    @FXML private void handleUpdateUser(ActionEvent event) {
        usersCsv.deleteUser(currentUser.getEmail());
        usersCsv.saveUser(firstNameField.getText(), lastNameField.getText(), phoneField.getText(), 
                          emailField.getText(), currentUser.getPassword(), roleChoiceBox.getValue().toString());
        ((Stage) updateButton.getScene().getWindow()).close();
    }
}