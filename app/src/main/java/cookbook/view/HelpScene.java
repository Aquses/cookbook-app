package cookbook.view;
import cookbook.Cookbook;
import cookbook.controller.AddRecipeController;
import cookbook.controller.HelpItemController;
import cookbook.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class HelpScene implements Initializable {
    @FXML
    private TextField RecipeSearchField;
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane ap;

    AddRecipeController addrecipe;


    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("HelpScene.fxml"));
        Scene helpScene = new Scene(fxmlLoader.load(), 1280, 720);
        return helpScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Help> helps = FXCollections.observableArrayList();
        helps.add(new Help("Search feature", "/tutorials/search.png"));
        helps.add(new Help("Favorites feature", "/tutorials/favorite.png"));
        helps.add(new Help("Sending a recipe", "/tutorials/send-recipe.png"));
        helps.add(new Help("Burger Menu", "/tutorials/burger-menu.png"));
        helps.add(new Help("Expanded Burger Menu", "/tutorials/burger-menu-extended.png"));
        helps.add(new Help("Login", "/tutorials/login.png"));

        FilteredList<Help> filteredHelps = new FilteredList<>(helps, b -> true);
        loadRecipes(helps);

        RecipeSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredHelps.setPredicate(recipe -> {
                // if no search value, display everything.
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Check if the recipe matches the search keyword
                if (recipe.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true; // found matches
                }

                return false; // no matches found
            });

            SortedList<Help> sortedHelps = new SortedList<>(filteredHelps);

            grid.getChildren().clear();

            loadRecipes(sortedHelps);
        });

    }


    private void loadRecipes(ObservableList<Help> allTutorials){
        int row = 1, col = 0;

        for(int i=0; i<allTutorials.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cookbook/HelpItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            HelpItemController helpItemController = fxmlLoader.getController();
            helpItemController.setData(allTutorials.get(i), ap);

            if(col == 1){
                col = 0;
                row++;
            }

            grid.add(anchorPane, col++, row++);
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            //GridPane.setMargin(anchorPane, new Insets(0,10,0,10));
        }
    }
}
