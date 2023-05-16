package cookbook.view;
import cookbook.Cookbook;
import cookbook.controller.AddRecipeController;
import cookbook.controller.HelpItemController;
import cookbook.controller.ItemController;
import cookbook.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
//import javafx.stage.Stage;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
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
