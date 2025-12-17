package bug.tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
            Parent root = loader.load();
            
            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 1. CAPTURE CURRENT STATE (Width, Height, Maximized)
            boolean isMaximized = stage.isMaximized();
            double width = stage.getWidth();
            double height = stage.getHeight();

            // 2. LOAD NEW SCENE
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // 3. RESTORE STATE
            // If we don't do this, the window will shrink back to default size
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setMaximized(isMaximized);
            
            stage.show();
            
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load FXML file: " + fxmlFile);
            e.printStackTrace();
        }
    }
}