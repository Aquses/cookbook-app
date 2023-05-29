package cookbook.controller;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.Cookbook;
import cookbook.model.Recipe;
import cookbook.view.DisplayRecipeScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DailyRecipeController {

    @FXML
    private Label recipeName;

    private Recipe recipe;
    private AnchorPane parentAnchorPane;


    @FXML
    public void setRecipe(Recipe recipe, AnchorPane parent) {
        this.recipe = recipe;
        this.parentAnchorPane = parent;
        this.recipeName.setText(recipe.getName());
    }

    @FXML
    void transitionDisplayRecipe(MouseEvent event) throws SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("DisplayRecipeScene.fxml"));
        DisplayRecipeScene drs;
        Node n;

        try {
            n = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        drs = fxmlLoader.getController();
        drs.addRecipeObject(recipe, parentAnchorPane);
        drs.addIngredients();

        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);

        parentAnchorPane.getChildren().clear();
        parentAnchorPane.getChildren().add(n);
    }

}
