package cookbook;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddRecipeStage implements Initializable {

  @FXML
  private TextField nameField;

  @FXML
  private Label nameLabel;

  @FXML
  private TextArea textArea;

  @FXML
  private Button addRecipeButton;

  @FXML
  private Label descLabel;

  @FXML
  private TextField ingField;

  @FXML
  private static Button ingButton;

  @FXML
  private Label ingLabel;

  @FXML
  private TextField tagsField;

  @FXML
  private Button tagsButton;

  @FXML
  private Label tagsLabel;

  @FXML
  private TextField portionSizeField;

  @FXML
  private Label portionSizeLabel;

  @FXML
  private TextField prepTimeField;

  @FXML
  private Label prepTimeLabel;

  @FXML
  private TextField cookTimeField;

  @FXML
  private Label cookTimeLabel;

  @FXML
  private TextField servingsField;

  @FXML
  private Label servingsLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
      // initialize the controller
  }

}
  
//   public static void addRecipeStage() {
    
//     ingButton.setOnAction(event -> {
//       TextField newTextField = new TextField();
//       newTextField.setPrefWidth(150);
//       int index = root.getChildren().indexOf(ingField);
//       root.getChildren().add(index + 2, newTextField);
//     });
  

//     tagsButton.setOnAction(event -> {

//     });


//     // Set up button action
//     addButton.setOnAction(event -> {
//         // Get values from text fields
//         String recipeName = nameField.getText();
//         String recipeDesc = descArea.getText();
//         String recipeInstructions = instrArea.getText();
//         int servings = Integer.parseInt(servingsField.getText());
//         int prepTime = Integer.parseInt(servingsField.getText());
//         int cookTime = Integer.parseInt(cookField.getText());

//         try {
//           Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
//           Statement stmt = conn2.createStatement();
//           String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes) " +
//                   "VALUES ('" + recipeName + "', '" + recipeDesc + "', '" + recipeInstructions + "', " +
//                   servings + ", " + prepTime + ", " + cookTime + ")";
//           stmt.executeUpdate(query);
//         } catch (SQLException e) {
//           e.printStackTrace();
//       }

//       // Clear text fields
//       nameField.clear();
//       descArea.clear();
//       instrArea.clear();
//       servingsField.clear();
//       prepField.clear();
//       cookField.clear();
//   });
// }
// }
