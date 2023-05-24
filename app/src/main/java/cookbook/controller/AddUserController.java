package cookbook.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cookbook.model.DataQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddUserController implements Initializable {

    @FXML
    private Button addUserButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button exitButton;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void addUser(ActionEvent event) {
        String firstNameInput = firstName.getText();
        String lastNameInput = lastName.getText();
        String usernameInput = userName.getText();
        String passwordInput = password.getText();

        DataQuery dq = new DataQuery();
        dq.addUser(firstNameInput, lastNameInput, usernameInput, passwordInput);

        firstName.clear();
        lastName.clear();
        userName.clear();
        password.clear();
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}