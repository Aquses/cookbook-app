package cookbook.view;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.controller.MainNavigation;
import cookbook.model.DataQuery;
import cookbook.model.Session;
import cookbook.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class UserLoginScene implements Initializable {

    @FXML private Label usernameLabel;

    @FXML private Label passwordLabel;

    @FXML private Label errorLabel; // errorlabel doesn't show fix later 

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private Button loginButton;

    @FXML private ImageView logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameField.setOnAction(e -> {
            try {
                login();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        passwordField.setOnAction(e -> {
            try {
                login();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        // Add event handler for login button
        loginButton.setOnAction(e -> {
            try {
                login();
                usernameField.clear();
                passwordField.clear();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

    }

    private void login() throws SQLException {

        String username = usernameField.getText();
        String password = passwordField.getText();
        Stage userStage = new Stage();
        DataQuery dq = new DataQuery();
        boolean result = dq.checkCredentials(username, password);
        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        if (result) {
            DataQuery userQuery = new DataQuery();
            // set logged in user object to pass to the hub scene
            User loggedUser = new User(userQuery.getUser(username, password));
            Session.setCurrentUser(loggedUser);
            userStage.setTitle("Welcome " + loggedUser.getFname() + " " + loggedUser.getLname() + "!");
      
            try {
                userStage.setScene(MainNavigation.getScene());
                userStage.show();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
              userStage.show();

        } else {
            System.out.println("Invalid username or password.");
            errorLabel.setText("Invalid username or password.");
        }
    }

}




