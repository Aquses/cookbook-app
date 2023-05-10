package cookbook.view;

import cookbook.controller.MainNavigation;
import cookbook.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.Cookbook;

public class DisplayRecipeScene implements Initializable {
    @FXML
    private Text RecipeDetails;
    @FXML
    private Text RecipeIngredients;
    @FXML
    private Text RecipeName;
    @FXML
    private Text RecipeShortDescription;
    @FXML
    private ImageView ReturnButton;
    @FXML
    private Text ServingsText;
    @FXML
    private Text TimeCookText;
    @FXML
    private Text TimePrepareText;
    @FXML
    private AnchorPane ap;
    private Recipe recipe;
    private AnchorPane parentAnchorPane;

    // for vic
    @FXML
    private Button EditRecipeButton;
    @FXML
    private Button FavouriteRecipeButton;
    @FXML
    private ImageView FavButtonIcon;


    private int recipe_id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FavouriteRecipeButton.setOnMouseClicked(event -> {
            int user_id = MainNavigation.getUserId();

            // check weather it is a favorite recipe or not
            DataQuery db = new DataQuery();
            boolean fav = false;
            try {
                fav = db.isFavorite(user_id, recipe_id);
            } catch (SQLException e) {}


            // set icon
            Image image;
            db = new DataQuery();
            if(!fav) {
                db.insertFavorite(user_id, recipe_id);
                image = new Image(getClass().getResource("/menuIcons/star-gold.png").toExternalForm());
            }else {
                db.removeFavorite(user_id, recipe_id);
                image = new Image(getClass().getResource("/menuIcons/star.png").toExternalForm());
            }
            FavButtonIcon.setImage(image);
        });
    }

    public void addRecipeObject(Recipe recipe, AnchorPane parentAnchorPane){
        this.recipe = recipe;
        this.parentAnchorPane = parentAnchorPane;

        RecipeName.setText(recipe.getName());
        RecipeShortDescription.setText(recipe.getDescription());
        RecipeDetails.setText(recipe.getInstructions());
        ServingsText.setText(String.valueOf(recipe.getServings()));

        // Uncomment below if recipe class prep time and cook time attributes are float type
        // TimePrepareText.setText(floatToMinutes(recipe.getPrepTime()));
        // TimeCookText.setText(floatToMinutes(recipe.getCookTime()));

        // For int type below
        TimePrepareText.setText(String.valueOf(recipe.getPrepTime()));
        TimeCookText.setText(String.valueOf(recipe.getCookTime()));

        int user_id = MainNavigation.getUserId();
        int recipe_id = recipe.getId();
        this.recipe_id = recipe_id;
        // check weather it is a favorite recipe or not
        DataQuery db = new DataQuery();
        boolean fav = false;
        try {
            fav = db.isFavorite(user_id, recipe_id);
        } catch (SQLException e) {}

        // set icon
        Image image;
        if(fav) {
            image = new Image(getClass().getResource("/menuIcons/star-gold.png").toExternalForm());
        }else {
            image = new Image(getClass().getResource("/menuIcons/star.png").toExternalForm());
        }
        FavButtonIcon.setImage(image);

        return;
    }

    public void addIngredients() {
        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Ingredient> ingredientsList = qm.retrieveIngredients(recipe.getId());
            StringBuilder sb = new StringBuilder();
            
            for (Ingredient i : ingredientsList) {
                sb.append(i.getIngredientName() + " | " + i.getQty() + " | " + i.getMeasurement() + "\n");
            }

            RecipeIngredients.setText(sb.toString());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Below method is used if the prep time and cook time attributes are float
    public String floatToMinutes(float time){
        String t = Float.toString(time);
        float remainder = (time * 60) % 60;

        if(remainder == 0){
            int place = t.indexOf(".");
            String subT = t.substring(0, place);
            return subT + " min";
        } else {
            float seconds = remainder * 60;
            int place = t.indexOf(".");
            String subT = t.substring(0, place);
            return subT + "min " + seconds + "s";
        }
    }

    @FXML
    private void transitionEditScene(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("RecipeEditor.fxml"));
        RecipeEditor editor;
        Node n;

        System.out.println(Cookbook.class.getResource("DisplayRecipeScene.fxml"));


        // load first
        try {
            n = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // then get controller
        editor = fxmlLoader.getController();
        editor.initialize(recipe);

        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);

        parentAnchorPane.getChildren().clear();
        parentAnchorPane.getChildren().add(n);

    }
}
