package cookbook.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.Cookbook;
import cookbook.controller.SendRecipeController;
import cookbook.model.Ingredient;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.RecipeEditor;
import cookbook.model.User;

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

    @FXML
    private Button sendRecipe;

    // for vic
    @FXML
    private Button EditRecipeButton;
    @FXML
    private Button FavouriteRecipeButton;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void setUser(User loggedUser) {
        user = loggedUser;
        System.out.println(user);
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
    @FXML
    void sendRecipe(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Cookbook.class.getResource("SendRecipe.fxml"));
        Parent sendRecipeRoot = loader.load();
        SendRecipeController sendController = loader.getController();

        sendController.setRecipe(recipe);
                
        Scene sendRecipeScene = new Scene(sendRecipeRoot);
        Stage sendRecipeStage = new Stage();
        sendRecipeStage.setScene(sendRecipeScene);
        sendRecipeStage.showAndWait();
    }
}
