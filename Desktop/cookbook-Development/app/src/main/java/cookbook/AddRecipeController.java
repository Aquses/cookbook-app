package cookbook;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

public class AddRecipeController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField portionSize;

    @FXML
    private Label portionLabel;

    @FXML
    private Label descLabel;

    @FXML
    private TextField prepField;

    @FXML
    private TextField cookField;

    @FXML
    private Label prepLabel;

    @FXML
    private Label cookLabel;

    @FXML
    private TextField servingsField;

    @FXML
    private Label servingsLabel;

    @FXML
    private TextArea descField;

    @FXML
    private Button addRecipeButton;

    @FXML
    private Label insLabel;

    @FXML
    private Label tagsLabel;

    @FXML
    private TextField insField;

    @FXML
    private TextField tagsField;

    @FXML
    private Button insButton;

    @FXML
    private Button tagsButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRecipeButton.setOnAction(event -> {
            // Get values from text fields
            String recipeName = nameField.getText();
            String recipeDesc = descField.getText();
            String recipeInstructions = insField.getText();
            int servings = Integer.parseInt(servingsField.getText());
            int prepTime = Integer.parseInt(prepField.getText());
            int cookTime = Integer.parseInt(cookField.getText());
    
            try {
              Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
              Statement stmt = conn2.createStatement();
              String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes) " +
                      "VALUES ('" + recipeName + "', '" + recipeDesc + "', '" + recipeInstructions + "', " +
                      servings + ", " + prepTime + ", " + cookTime + ")";
              stmt.executeUpdate(query);
            } catch (SQLException e) {
              e.printStackTrace();
          }
    
          // Clear text fields
          nameField.clear();
          descField.clear();
          insField.clear();
          servingsField.clear();
          portionSize.clear();
          prepField.clear();
          cookField.clear();
        });

        insButton.setOnAction(event -> {
            
        });
    }
}
