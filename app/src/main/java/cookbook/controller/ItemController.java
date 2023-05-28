package cookbook.controller;

import cookbook.Cookbook;
import cookbook.model.Recipe;
import cookbook.view.DisplayRecipeScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This is the controller class for each item spawned in the recipe scene.
 */
public class ItemController implements Initializable {
  @FXML
  private ImageView foodImage;
  @FXML
  private Label foodItemNameWhite;
  @FXML
  private Label foodItemDescription;
  @FXML
  private Label servingsCount;
  @FXML
  private Label timeTakenCount;
  @FXML
  private Pane recipeButton;
  @FXML
  private Pane recipePane;
  @FXML
  private VBox numberLabels;
  private AnchorPane parentAnchorPane;
  private Recipe recipe;

  /**
   * This sets the data contained within the spawned box.

   * @param recipe Recipe object used as reference.
   * @param parent Retrieves the page where this item was spawned. Makes the "back" button function.
   */
  public void setData(Recipe recipe, AnchorPane parent) {
    this.recipe = recipe;
    parentAnchorPane = parent;

    this.foodItemNameWhite.setText(recipe.getName());
    this.servingsCount.setText(Integer.toString(recipe.getServings()));
    this.timeTakenCount.setText(Float.toString((recipe.getCookTime())));
    this.foodItemDescription.setText(recipe.getDescription());
    //Image image = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
    //this.FoodImage.setImage(image);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //the button invokes a new scene with the information of itself?
    //RecipeButton.setOnMouseClicked();
    foodItemNameWhite.setWrapText(true);
    foodItemDescription.setWrapText(true);
    servingsCount.setTextFill(Color.BLACK);
    timeTakenCount.setTextFill(Color.BLACK);

    recipeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        // fade pane start
        FadeTransition pane = new FadeTransition(Duration.millis(400), recipePane);
        pane.setFromValue(0);
        pane.setToValue(0.6);
        pane.play();

        FadeTransition numbers = new FadeTransition(Duration.millis(200), numberLabels);
        numbers.setFromValue(1);
        numbers.setToValue(0);
        numbers.play();

        FadeTransition desc = new FadeTransition(Duration.millis(300), foodItemDescription);
        desc.setFromValue(0);
        desc.setToValue(1);
        desc.play();
      }
    });

    recipeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        // fade pane back
        FadeTransition pane = new FadeTransition(Duration.millis(400), recipePane);
        pane.setFromValue(0.6);
        pane.setToValue(0);
        pane.play();

        FadeTransition numbers = new FadeTransition(Duration.millis(200), numberLabels);
        numbers.setFromValue(0);
        numbers.setToValue(1);
        numbers.play();

        FadeTransition desc = new FadeTransition(Duration.millis(300), foodItemDescription);
        desc.setFromValue(1);
        desc.setToValue(0);
        desc.play();


      }
    });

    recipeButton.setOnMouseClicked(event -> {
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
      try {
        drs.addRecipeObject(recipe, parentAnchorPane);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      drs.addIngredients();
      drs.addThisScenesController(drs);
      drs.reloadComments();

      AnchorPane.setTopAnchor(n, 0.0);
      AnchorPane.setRightAnchor(n, 0.0);
      AnchorPane.setBottomAnchor(n, 0.0);
      AnchorPane.setLeftAnchor(n, 0.0);

      parentAnchorPane.getChildren().clear();
      parentAnchorPane.getChildren().add(n);
    });
  }
}
