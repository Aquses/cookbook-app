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

  }
}