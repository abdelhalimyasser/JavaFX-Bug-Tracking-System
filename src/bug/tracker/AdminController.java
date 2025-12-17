package bug.tracker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import bug.tracker.bugs.Bug;
import bug.tracker.csv.BugsCSV;
import bug.tracker.csv.UsersCSV;
import bug.tracker.users.User;

public class AdminController implements Initializable {

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> colFirstName;
    @FXML
    private TableColumn<User, String> colLastName;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableView<Bug> allBugsTable;
    @FXML
    private TableColumn<Bug, Integer> colBugID;
    @FXML
    private TableColumn<Bug, String> colSummary;
    @FXML
    private TableColumn<Bug, String> colProject;
    @FXML
    private TableColumn<Bug, String> colStatus;
    @FXML
    private TableColumn<Bug, String> colPriority;
    @FXML
    private TableColumn<Bug, String> colTester;
    @FXML
    private TableColumn<Bug, String> colDeveloper;
    @FXML
    private TableColumn<Bug, String> colDate;
    @FXML
    private ImageView screenshotView;

    private UsersCSV usersCsv = new UsersCSV();
    private BugsCSV bugsCsv = new BugsCSV();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        colBugID.setCellValueFactory(new PropertyValueFactory<>("bugID"));
        colSummary.setCellValueFactory(new PropertyValueFactory<>("bugDescription"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("bugPriority"));
        colTester.setCellValueFactory(new PropertyValueFactory<>("tester"));
        colDeveloper.setCellValueFactory(new PropertyValueFactory<>("developer"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("bugDate"));

        allBugsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldBug, newBug) -> {
            if (newBug != null) {
                loadImage(newBug.getBugPhoto());
            } else {
                setDefaultImage();
            }
        });

        loadData();
    }

    public void loadData() {
        usersTable.setItems(FXCollections.observableArrayList(usersCsv.loadUsers()));
        allBugsTable.setItems(FXCollections.observableArrayList(bugsCsv.loadBugs()));
    }

    @FXML
    public void handleAddUser(ActionEvent event) {
        openDialog("addUser.fxml", "Add New User", null);
    }

    @FXML
    public void handleUpdateUser(ActionEvent event) {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Select a user.");
            return;
        }
        openDialog("updateUser.fxml", "Update User", c -> ((UpdateUserController) c).initData(selected));
    }

    @FXML
    public void handleDeleteUser(ActionEvent event) {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Select a user.");
            return;
        }

        if (confirmAction("Delete User", "Are you sure you want to delete " + selected.getEmail() + "?")) {
            usersCsv.deleteUser(selected.getEmail());
            loadData();
        }
    }

    @FXML
    public void handleAddBug(ActionEvent event) {
        openDialog("addBug.fxml", "Add New Bug", null);
    }

    @FXML
    public void handleUpdateBug(ActionEvent event) {
        Bug selected = allBugsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Select a bug.");
            return;
        }
        openDialog("updateBug.fxml", "Update Bug", c -> ((UpdateBugController) c).initData(selected));
    }

    @FXML
    public void handleDeleteBug(ActionEvent event) {
        Bug selected = allBugsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Select a bug.");
            return;
        }

        if (confirmAction("Delete Bug", "Are you sure you want to delete Bug #" + selected.getBugID() + "?")) {
            bugsCsv.deleteBug(selected.getBugID());
            loadData();
        }
    }

    interface ControllerSetup {

        void setup(Object controller);
    }

    private void openDialog(String fxml, String title, ControllerSetup setup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (setup != null) {
                setup.setup(loader.getController());
            }
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean confirmAction(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void loadImage(String path) {
        try {
            if (path != null && !path.equals("none") && !path.isEmpty()) {
                File file = new File(path);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    screenshotView.setImage(image);
                } else {
                    System.out.println("Image file not found at: " + path);
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

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void sendToLoginAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
