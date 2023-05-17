package cookbook.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cookbook.model.DataQuery;
import cookbook.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditUserController implements Initializable {

    @FXML
    private Label editUser;

    @FXML
    private Button addUserButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void setUser(User selectedUser) {
      user = selectedUser;
      editUser.setText("Edit user: " + user.getUsername());
      setTextfields();
    }

    public void setTextfields() {
      firstName.setText(user.getFname());
      lastName.setText(user.getLname());
      userName.setText(user.getUsername());
      password.setText(user.getPassword());
    } 
    
    @FXML
    public void editUser(ActionEvent event) {
        String firstNameInput = firstName.getText();
        String lastNameInput = lastName.getText();
        String usernameInput = userName.getText();
        String passwordInput = password.getText();
        
        DataQuery dq = new DataQuery();
        dq.editUser(user.getUserId(), firstNameInput, lastNameInput, usernameInput, passwordInput);

        Stage stage = (Stage) editUser.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
