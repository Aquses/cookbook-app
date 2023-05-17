package cookbook.controller;

import cookbook.model.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DailyRecipeController {

    @FXML
    private Label recipeName;


    @FXML
    public void setRecipeName(Recipe recipe) {
        this.recipeName.setText(recipe.getName());
    }

}
