package cookbook.controller;

import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FavoriteController implements Initializable {
    @FXML
    private AnchorPane ap;
    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            User user = Session.getCurrentUser();
            int user_id = user.getUserId();
            QueryMaker qm = new QueryMaker();
            ObservableList<Recipe> recipes = qm.getFavoriteRecipes(user_id);
            FilteredList<Recipe> filteredRecipes = new FilteredList<>(recipes, b -> true);
            loadRecipes(recipes);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRecipes(ObservableList<Recipe> allRecipes){
        int row = 1, col = 0;

        for(int i=0; i<allRecipes.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ItemController itemController = fxmlLoader.getController();
            itemController.setData(allRecipes.get(i), ap);

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
    }
}
