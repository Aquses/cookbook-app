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
  }
}