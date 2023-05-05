package cookbook.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddRecipeStage {

  public static void addRecipeStage() {
    Stage stage = new Stage();
    stage.setTitle("Add Recipe");

    // Create UI controls
    Label nameLabel = new Label("Recipe Name:");
    TextField nameField = new TextField();

    Label descLabel = new Label("Recipe Description:");
    TextArea descArea = new TextArea();

    Label instrLabel = new Label("Recipe Instructions:");
    TextArea instrArea = new TextArea();

    Label servingsLabel = new Label("Number of Servings:");
    TextField servingsField = new TextField();

    Label prepLabel = new Label("Prep Time (mins):");
    TextField prepField = new TextField();

    Label cookLabel = new Label("Cook Time (mins):");
    TextField cookField = new TextField();

    Button addButton = new Button("Add Recipe");



    // Create layout
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    grid.add(nameLabel, 0, 0);
    grid.add(nameField, 1, 0);

    grid.add(descLabel, 0, 1);
    grid.add(descArea, 1, 1);

    grid.add(instrLabel, 0, 2);
    grid.add(instrArea, 1, 2);

    grid.add(servingsLabel, 0, 3);
    grid.add(servingsField, 1, 3);

    grid.add(prepLabel, 0, 4);
    grid.add(prepField, 1, 4);

    grid.add(cookLabel, 0, 5);
    grid.add(cookField, 1, 5);

    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().add(addButton);

    VBox root = new VBox(20);
    root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(grid, buttonBox);
   
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    // Set up button action
    addButton.setOnAction(event -> {
        // Get values from text fields
        String recipeName = nameField.getText();
        String recipeDesc = descArea.getText();
        String recipeInstructions = instrArea.getText();
        int servings = Integer.parseInt(servingsField.getText());
        int prepTime = Integer.parseInt(servingsField.getText());
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
      descArea.clear();
      instrArea.clear();
      servingsField.clear();
      prepField.clear();
      cookField.clear();
  });
}
}
