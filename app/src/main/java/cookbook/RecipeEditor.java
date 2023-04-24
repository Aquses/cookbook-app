package cookbook;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RecipeEditor extends Application {

  private final StringProperty recipeName = new SimpleStringProperty("");
  private final StringProperty ingredient = new SimpleStringProperty("");
  private final StringProperty shortDescription = new SimpleStringProperty("");
  private final StringProperty detailedDescription = new SimpleStringProperty("");

  @Override
  public void start(Stage primaryStage) {
    // Create UI components
    Label lblRecipeName = new Label("Recipe Name:");
    TextField tfRecipeName = new TextField();
    tfRecipeName.textProperty().bindBidirectional(recipeName);

    Label lblIngredient = new Label("Ingredient:");
    TextField tfIngredient = new TextField();
    tfIngredient.textProperty().bindBidirectional(ingredient);
    Label lblShortDescription = new Label("Short Description:");
    TextArea taShortDescription = new TextArea();
    taShortDescription.setWrapText(true);
    taShortDescription.setMaxHeight(100);
    taShortDescription.textProperty().bindBidirectional(shortDescription);

    Label lblDetailedDescription = new Label("Detailed Description:");
    TextArea taDetailedDescription = new TextArea();
    taDetailedDescription.setWrapText(true);
    taDetailedDescription.textProperty().bindBidirectional(detailedDescription);

    Button btnAddIngredient = new Button("Add Ingredient");
    btnAddIngredient.setOnAction(event -> {
      String currentIngredient = ingredient.get();
      if (!currentIngredient.isEmpty()) {
        String currentIngredients = recipeName.get();
        currentIngredients += "\n- " + currentIngredient;
        recipeName.set(currentIngredients);
        ingredient.set("");
      }
    });
    Button btnSave = new Button("Save");
    btnSave.disableProperty()
        .bind(Bindings.createBooleanBinding(() -> recipeName.get().isEmpty() || shortDescription.get().isEmpty()
            || detailedDescription.get().isEmpty(), recipeName, shortDescription, detailedDescription));
    btnSave.setOnAction(event -> {
      String recipe = "Recipe Name: " + recipeName.get()
          + "\nIngredients:\n" + recipeName.get()
          + "\nShort Description: " + shortDescription.get()
          + "\nDetailed Description: " + detailedDescription.get();
      System.out.println(recipe);
      // Save the recipe to a file or database
      // TODO: Add logic to save the recipe to a file or database
    });

    // Create UI layout
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(10));

    gridPane.add(lblRecipeName, 0, 0);
    gridPane.add(tfRecipeName, 1, 0);
    gridPane.add(lblIngredient, 0, 1);
    gridPane.add(tfIngredient, 1, 1);
    gridPane.add(btnAddIngredient, 2, 1);
    gridPane.add(lblShortDescription, 0, 2);
    gridPane.add(taShortDescription, 1, 2, 2, 1);
    gridPane.add(lblDetailedDescription, 0, 3);
    gridPane.add(taDetailedDescription, 1, 3, 2, 1);
    gridPane.add(btnSave, 1, 4);
  }
}