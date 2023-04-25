package cookbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecipesScene implements Initializable {
    @FXML
    private Button AddRecipeButton;
    @FXML
    private Button EditRecipeMenuButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Button MenuClosed;
    @FXML
    private Button MenuOpen;
    @FXML
    private HBox MenuSlider;
    @FXML
    private TextField RecipeSearchField;
    @FXML
    private Button RecipesButton;
    @FXML
    private AnchorPane ap;
    @FXML
    private Pane darkenPane;
    @FXML
    private GridPane grid;

    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("RecipesScene.fxml"));
        Scene recipesScene = new Scene(fxmlLoader.load(), 1280, 720);

        return recipesScene;
    }

    private List<Recipe> recipes = new ArrayList<>();

    private List<Recipe> getData(){
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe;

        // find out how many there are in the db
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");

            Statement statement = conn.createStatement();
            ResultSet sqlRecipeNames = statement.executeQuery("SELECT * FROM recipes");

            while (sqlRecipeNames.next()) {
                //String queryByRecipeName = sqlRecipeNames.getString(1);
                //Statement st = conn.createStatement();
                //ResultSet rt = st.executeQuery("SELECT * FROM recipes WHERE recipe_name = \"" + queryByRecipeName + "\"");
                //System.out.println(sqlRecipeNames.getString(2));
                recipe = new Recipe(sqlRecipeNames);
                recipes.add(recipe);

            }
        } catch (SQLException e) {
            System.out.println("An error has occurred");
        }

        return recipes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commonMenuControls();

        recipes.addAll(getData());
        int col = 0, row = 0;
        try {
            System.out.println(recipes.size());
            for(int i=0; i<recipes.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(recipes.get(i));

                if(col == 4){
                    col = 0;
                    row++;
                }

                grid.add(anchorPane, col++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                //GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void specificControls() {
        AddRecipeButton.setOnMouseClicked(event -> {
            AddRecipeStage.addRecipeStage();
        });
    }

    private void commonMenuControls() {
        MenuSlider.setPrefWidth(50);
        darkenPane.setVisible(false);
        darkenPane.setPickOnBounds(false);

        MenuClosed.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(143);

            MenuOpen.setVisible(true);
            MenuClosed.setVisible(false);
            darkenPane.setVisible(true);
            darkenPane.setPickOnBounds(true);
        });

        MenuOpen.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
            darkenPane.setPickOnBounds(false);
        });

        darkenPane.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
            darkenPane.setPickOnBounds(true);
        });

        HomeButton.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
            darkenPane.setPickOnBounds(false);
        });

        RecipesButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ap.getScene().getWindow();
            try {
                stage.setScene(RecipesScene.getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        HomeButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ap.getScene().getWindow();
            try {
                stage.setScene(HubScene.getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
