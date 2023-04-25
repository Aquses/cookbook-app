package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RecipeEditor extends Application {

  private final StringProperty recipeName = new SimpleStringProperty("");
  private final StringProperty shortDescription = new SimpleStringProperty("");
  private final StringProperty detailedDescription = new SimpleStringProperty("");
  private final ObservableList<String> ingredients = FXCollections.observableArrayList();

  @Override
  public void start(Stage primaryStage) {
    // Create UI components
    Label lblRecipeName = new Label("Recipe Name:");
    TextField tfRecipeName = new TextField();
    tfRecipeName.textProperty().bindBidirectional(recipeName);

    Label lblShortDescription = new Label("Short Description:");
    TextArea taShortDescription = new TextArea();
    taShortDescription.setWrapText(true);
    taShortDescription.setMaxHeight(100);
    taShortDescription.textProperty().bindBidirectional(shortDescription);

    Label lblDetailedDescription = new Label("Detailed Description:");
    TextArea taDetailedDescription = new TextArea();
    taDetailedDescription.setWrapText(true);
    taDetailedDescription.textProperty().bindBidirectional(detailedDescription);

    Label lblIngredients = new Label("Ingredients:");
    ListView<String> lvIngredients = new ListView<>();
    lvIngredients.setItems(ingredients);

    TextField tfIngredient = new TextField();

    Button btnAddRecipeName = new Button("Add Recipe Name");
    btnAddRecipeName.setOnAction(event -> {
      String currentRecipeName = tfRecipeName.getText();
      if (!currentRecipeName.isEmpty()) {
        recipeName.set(currentRecipeName);
        tfRecipeName.clear();
      }
    });

    Button btnAddShortDescription = new Button("Add Short Description");
    btnAddShortDescription.setOnAction(event -> {
      String currentShortDescription = taShortDescription.getText();
      if (!currentShortDescription.isEmpty()) {
        shortDescription.set(currentShortDescription);
        taShortDescription.clear();
      }
    });

    Button btnAddDetailedDescription = new Button("Add Detailed Description");
    btnAddDetailedDescription.setOnAction(event -> {
      String currentDetailedDescription = taDetailedDescription.getText();
      if (!currentDetailedDescription.isEmpty()) {
        detailedDescription.set(currentDetailedDescription);
        taDetailedDescription.clear();
      }
    });

    Button btnAddIngredient = new Button("Add Ingredient");
    btnAddIngredient.setOnAction(event -> {
      String currentIngredient = tfIngredient.getText();
      if (!currentIngredient.isEmpty()) {
        ingredients.add(currentIngredient);
        tfIngredient.clear();
      }
    });

    Button btnSave = new Button("Save");

    // Save the recipe to a file or database
    Label mysql;
    try {
      Connection conn = DriverManager
          .getConnection("jdbc:mysql://localhost/StarWars?user=tobias&password=abcd1234&useSSL=false");
      mysql = new Label("Driver found and connected");
      Statement stmt = conn.createStatement();
      String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions)" + "VALUES ('"
          + recipeName + "', '" + shortDescription + "', '" + detailedDescription + ")";
      String query2 = "INSERT INTO ingredients (i_name)" + "VALUES ('" + ingredients + ")";
      stmt.executeUpdate(query);
      stmt.executeUpdate(query2);

    } catch (SQLException e) {
      mysql = new Label("An error has occurred: " + e.getMessage());
    }

    // Create UI layout
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(10));
    gridPane.setAlignment(Pos.TOP_LEFT);
    gridPane.setPrefSize(400, 600);
    gridPane.add(lblRecipeName, 0, 0);
    gridPane.add(tfRecipeName, 1, 0);
    gridPane.add(btnAddRecipeName, 2, 0);
    gridPane.add(lblShortDescription, 0, 1);
    gridPane.add(taShortDescription, 1, 1);
    gridPane.add(btnAddShortDescription, 2, 1);
    gridPane.add(lblDetailedDescription, 0, 2);
    gridPane.add(taDetailedDescription, 1, 2);
    gridPane.add(btnAddDetailedDescription, 2, 2);
    gridPane.add(lblIngredients, 0, 3);
    gridPane.add(lvIngredients, 1, 3);
    gridPane.add(tfIngredient, 1, 4);
    gridPane.add(btnAddIngredient, 2, 4);
    gridPane.add(btnSave, 0, 5, 3, 1);

    ColumnConstraints col1Constraints = new ColumnConstraints();
    col1Constraints.setHgrow(Priority.NEVER);
    col1Constraints.setMinWidth(150);

    ColumnConstraints col2Constraints = new ColumnConstraints();
    col2Constraints.setHgrow(Priority.ALWAYS);

    gridPane.getColumnConstraints().addAll(col1Constraints, col2Constraints);

    VBox vbox = new VBox(gridPane);
    vbox.setPadding(new Insets(10));
    Scene scene = new Scene(vbox, 400, 600);
    primaryStage.setTitle("Recipe Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}