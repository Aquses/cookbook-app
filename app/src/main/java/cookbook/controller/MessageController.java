package cookbook.controller;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.Cookbook;
import cookbook.model.Message;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.User;
import cookbook.view.DisplayRecipeScene;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MessageController {

    @FXML
    private ImageView FoodImage;

    @FXML
    private Label FoodItemDescription;

    @FXML
    private Label FoodItemNameWhite;

    @FXML
    private VBox NumberLabels;

    @FXML
    private Pane RecipeButton;

    @FXML
    private Pane RecipePane;

    @FXML
    private Label ServingsCount;

    @FXML
    private Label TimeTakenCount;

    @FXML
    private Label messageContent;

    @FXML
    private Label messageDate;

    @FXML
    private Label messageHeader;

    @FXML
    private Label SenderName;

    Recipe recipe;
    Message message;
    private AnchorPane parentAnchorPane;

    
    @FXML
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.FoodItemNameWhite.setText(recipe.getName());
        this.ServingsCount.setText(String.valueOf(recipe.getServings()));
        this.TimeTakenCount.setText(String.valueOf((recipe.getCookTime())));
        this.FoodItemDescription.setText(recipe.getDescription());

    }

    public void setMessage(Message msg, AnchorPane parent) {
        this.parentAnchorPane = parent;
        this.message = msg;
        messageContent.setText(message.getContent());
        messageDate.setText(String.valueOf(message.getDateCreated()));  
    }

    public void setSenderName(Message msg) {
        try {
            QueryMaker qm = new QueryMaker();
            User sender = qm.retrieveSender(msg);

            SenderName.setText(sender.getFname() + " " + sender.getLname());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    void mouseHover(MouseEvent event) {
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

    @FXML
    void mouseHoverExit(MouseEvent event) {
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

    @FXML
    void transitionDisplayRecipe(MouseEvent event) throws SQLException {
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

    }

}
