package cookbook.controller;

import cookbook.Cookbook;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //int col = 0, row = 0;

        commonMenuControls();
        specificControls();

        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Recipe> recipes = qm.getAllRecipes();
            FilteredList<Recipe> filteredRecipes = new FilteredList<>(recipes, b -> true);

            RecipeSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredRecipes.setPredicate(Recipe -> {
                    // if no search value, display everything.
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();
                    if (    Recipe.getName().toLowerCase().indexOf(searchKeyword) > -1 ||
                            Recipe.getDescription().toLowerCase().indexOf(searchKeyword) > -1 ||
                            Recipe.getInstructions().toLowerCase().indexOf(searchKeyword) > -1) {
                        return  true; //found matches
                    } else {
                        return false;
                    }
                });
                SortedList<Recipe> sortedRecipes = new SortedList<>(filteredRecipes);

                // horribly clears the grid to spawn new nodes
                for (int i = 0; i < grid.getRowCount(); i++) {
                    for (int j = 0; j < grid.getRowCount(); j++) {
                        int k = i+j;
                        grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == k);
                    }
                }

                int col = 0, row = 1;

                for(int i=0; i<sortedRecipes.size(); i++){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(sortedRecipes.get(i));

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

                    //GridPane.setMargin(anchorPane, new Insets(0,10,0,10));
                }
            });

            //SortedList<Recipe> sortedRecipes = new SortedList<>(filteredRecipes);

            //sortedRecipes.comparatorProperty().bind(grid.)
            /*
            for(int i=0; i<sortedRecipes.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(sortedRecipes.get(i));

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
            }*/
        } catch (SQLException e) {
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
