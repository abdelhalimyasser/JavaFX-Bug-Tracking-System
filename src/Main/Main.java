package bug.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Start at the Login Screen
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bug Tracker System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("Error starting app. Check FXML file names.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}