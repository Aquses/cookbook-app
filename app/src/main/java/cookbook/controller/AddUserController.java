package cookbook.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddUserController implements Initializable {

    @FXML
    private Button addUserButton;

    @FXML
    private ChoiceBox<String> admin;

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

    private String[] adminStrings = {"Admin", "User"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     admin.getItems().addAll(adminStrings);
    }

    @FXML
    public void addUser(ActionEvent event) {
        String firstNameInput = firstName.getText();
        String lastNameInput = lastName.getText();
        String usernameInput = userName.getText();
        String passwordInput = password.getText();
        int adminValue;
        if(admin.getValue().equals("Admin")){
            adminValue = 1;
        } else {
            adminValue = 0;
        }

        DataQuery dq = new DataQuery();
        dq.addUser(firstNameInput, lastNameInput, adminValue, usernameInput, passwordInput);

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