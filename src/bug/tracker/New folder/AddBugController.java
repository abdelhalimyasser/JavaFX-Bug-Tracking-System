/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bug.tracker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author abdel
 */
public class AddBugController implements Initializable {

    @FXML
    private TextField txtBugName;
    @FXML
    private TextField txtProjectName;
    @FXML
    private ComboBox<?> comboType;
    @FXML
    private ComboBox<?> comboPriority;
    @FXML
    private ComboBox<?> comboLevel;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnSubmit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSubmitBug(ActionEvent event) {
    }
    
}
