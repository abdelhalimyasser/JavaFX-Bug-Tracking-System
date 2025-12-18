package bug.tracker; // <--- This must match the folder structure

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Now this works because Main.java and login.fxml are in the same folder
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

            Image icon = new Image(getClass().getResourceAsStream("/images/bug.png"));
            stage.getIcons().add(icon);

            Scene scene = new Scene(root);

            stage.setTitle("Bug Tracker System");
            stage.setResizable(true);
            stage.setMinWidth(900);
            stage.setMinHeight(600);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error starting app. Check FXML file names/paths.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}