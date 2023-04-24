package cookbook;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class UserLoginScene {
  private static TextField usernameField;
  private static PasswordField passwordField;
  private static Label errorLabel;

  public Scene getScene() {
    // Create UI elements
    Label usernameLabel = new Label("Username:");
    Label passwordLabel = new Label("Password:");
    usernameField = new TextField();
    passwordField = new PasswordField();
    Button loginButton = new Button("Login");
    errorLabel = new Label("");
    errorLabel.setStyle("-fx-text-fill: red;");

    // Add event handlers for username and password fields
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
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
    });

    // Create layout and add elements
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.add(usernameLabel, 0, 0);
    gridPane.add(usernameField, 1, 0);
    gridPane.add(passwordLabel, 0, 1);
    gridPane.add(passwordField, 1, 1);
    gridPane.add(loginButton, 1, 2);
    gridPane.add(errorLabel, 1, 3);

    // Create scene and add layout
    Scene scene = new Scene(gridPane, 300, 150);
    return scene;
}

private void login() throws SQLException {
    String username = usernameField.getText();
    String password = passwordField.getText();
    DataQuery dq = new DataQuery();
    boolean result = dq.checkCredentials(username, password);
    
    if (result) {
      Stage userStage = new Stage();
      userStage.setTitle("Welcome " + username + "!");

      UserPageScene userPage = new UserPageScene();
      userStage.setScene(userPage.getUserPage());
      userStage.show();
      
    } else {
        System.out.println("Invalid username or password.");
        clearFields();
        errorLabel.setText("Invalid username or password.") ;
    }
  }

  private static void clearFields() {
    usernameField.clear();
    passwordField.clear();
}
}




