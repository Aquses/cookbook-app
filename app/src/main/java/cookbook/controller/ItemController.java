package cookbook.controller;

import cookbook.Cookbook;
import cookbook.model.Recipe;
import cookbook.view.DisplayRecipeScene;
import javafx.animation.FadeTransition;
//import javafx.animation.FillTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.paint.Color;

//import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private ImageView FoodImage;
    @FXML
    private Label FoodItemNameWhite;
    @FXML
    private Label FoodItemDescription;
    @FXML
    private Label ServingsCount;
    @FXML
    private Label TimeTakenCount;
    @FXML
    private Pane RecipeButton;
    @FXML
    private Pane RecipePane;
    @FXML
    private VBox NumberLabels;
    private AnchorPane parentAnchorPane;
    private Recipe recipe;

    public void setData(Recipe recipe, AnchorPane parent) {
      this.recipe = recipe;
      parentAnchorPane = parent;

      this.FoodItemNameWhite.setText(recipe.getName());
      this.ServingsCount.setText(Integer.toString(recipe.getServings()));
      this.TimeTakenCount.setText(Float.toString((recipe.getCookTime())));
      this.FoodItemDescription.setText(recipe.getDescription());
      //Image image = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
      //this.FoodImage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
			//the button invokes a new scene with the information of itself?
      //RecipeButton.setOnMouseClicked();
			FoodItemNameWhite.setWrapText(true);
			FoodItemDescription.setWrapText(true);
			ServingsCount.setTextFill(Color.BLACK);
			TimeTakenCount.setTextFill(Color.BLACK);

			RecipeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // fade pane start
                FadeTransition pane = new FadeTransition(Duration.millis(400), RecipePane);
                pane.setFromValue(0);
                pane.setToValue(0.6);
                pane.play();

                FadeTransition numbers = new FadeTransition(Duration.millis(200), NumberLabels);
                numbers.setFromValue(1);
                numbers.setToValue(0);
                numbers.play();

                FadeTransition desc = new FadeTransition(Duration.millis(300), FoodItemDescription);
                desc.setFromValue(0);
                desc.setToValue(1);
                desc.play();
            }
        });

        RecipeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // fade pane back
                FadeTransition pane = new FadeTransition(Duration.millis(400), RecipePane);
                pane.setFromValue(0.6);
                pane.setToValue(0);
                pane.play();

                FadeTransition numbers = new FadeTransition(Duration.millis(200), NumberLabels);
                numbers.setFromValue(0);
                numbers.setToValue(1);
                numbers.play();

                FadeTransition desc = new FadeTransition(Duration.millis(300), FoodItemDescription);
                desc.setFromValue(1);
                desc.setToValue(0);
                desc.play();


            }
        });

        RecipeButton.setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("DisplayRecipeScene.fxml"));
            DisplayRecipeScene drs;
            Node n;

            // load first
            try {
                n = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // then get controller
            drs = fxmlLoader.getController();
            drs.addRecipeObject(recipe, parentAnchorPane);
            drs.addIngredients();

            AnchorPane.setTopAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
            AnchorPane.setLeftAnchor(n, 0.0);

            parentAnchorPane.getChildren().clear();
            parentAnchorPane.getChildren().add(n);
        });
    }
}
