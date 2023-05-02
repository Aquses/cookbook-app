package cookbook.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    // for vic
    @FXML
    private Button EditRecipeButton;
    @FXML
    private Button FavouriteRecipeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addRecipeObject(Recipe recipe){
        this.recipe = recipe;

        RecipeName.setText(recipe.getName());
        RecipeShortDescription.setText(recipe.getDescription());
        RecipeDetails.setText(recipe.getInstructions());
        //RecipeIngredients.setText(recipe.getIngredients); //TODO: missing ingredients as text
        ServingsText.setText(String.valueOf(recipe.getServings()));
        TimePrepareText.setText(floatToMinutes(recipe.getPrepTime()));
        TimeCookText.setText(floatToMinutes(recipe.getCookTime()));

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

    private String floatToMinutes(float time){
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
}
