package cookbook.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import cookbook.Cookbook;
import cookbook.controller.DailyRecipeController;
import cookbook.controller.HubItemController;
import cookbook.controller.ItemController;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;

public class HubScene implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private GridPane fridayGrid;

    @FXML
    private GridPane mondayGrid;

    @FXML
    private GridPane saturdayGrid;

    @FXML
    private GridPane sundayGrid;

    @FXML
    private GridPane thursdayGrid;

    @FXML
    private GridPane tuesdayGrid;

    @FXML
    private GridPane wednesdayGrid;

    private User user;

    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("HubScene.fxml"));
        Scene hub = new Scene(fxmlLoader.load(), 1280, 700);

        return hub;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = Session.getCurrentUser();
        this.user = currentUser;
        System.out.println(getCurrentWeek());
        loadWeek();
    }

    private int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());

        return weekNumber;
    }

    public void loadWeek() {

        System.out.println(getCurrentWeek());

        try {
            QueryMaker qm = new QueryMaker();
            // WeeklyDinnerList currentWeeklyPlan = qm.retrieveCurrentWeeklyPlan(getCurrentWeek(), user);
            WeeklyDinnerList currentWeeklyPlan = qm.retrieveCurrentWeeklyPlan(19, user);

            if (currentWeeklyPlan != null) {
                ObservableList<ObservableList<Recipe>> weeklyRecipes = currentWeeklyPlan.getWeeklyPlan();

                ObservableList<Recipe> mondayRecipes = weeklyRecipes.get(0);
                loadDailyRecipes(mondayRecipes, mondayGrid);

                ObservableList<Recipe> tuesdayRecipes = weeklyRecipes.get(1);
                loadDailyRecipes(tuesdayRecipes, tuesdayGrid);

                ObservableList<Recipe> wednesdayRecipes = weeklyRecipes.get(2);
                loadDailyRecipes(wednesdayRecipes, wednesdayGrid);

                ObservableList<Recipe> thursdayRecipes = weeklyRecipes.get(3);
                loadDailyRecipes(thursdayRecipes, thursdayGrid);

                ObservableList<Recipe> fridayRecipes = weeklyRecipes.get(4);
                loadDailyRecipes(fridayRecipes, fridayGrid);

                ObservableList<Recipe> saturadayRecipes = weeklyRecipes.get(5);
                loadDailyRecipes(saturadayRecipes, saturdayGrid);

                ObservableList<Recipe> sundayRecipes = weeklyRecipes.get(6);
                loadDailyRecipes(sundayRecipes, sundayGrid);
            } 

            

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        


        
        
        

    }

    public void loadDailyRecipes(ObservableList<Recipe> dailyRecipeList, GridPane grid) {

        int row = 0, col = 0;

        for(int i=0; i < dailyRecipeList.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cookbook/HubItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // DailyRecipeController drc = fxmlLoader.getController();
            // drc.setRecipe(dailyRecipeList.get(i), ap);

            HubItemController hubController = fxmlLoader.getController();
            hubController.setRecipe(dailyRecipeList.get(i), ap);

            grid.add(anchorPane, col++, row);            
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        }
    }

    // private displayEmptyWeek() {
    //     FXMLLoader fxmlLoader = new FXMLLoader();
    //     fxmlLoader.setLocation(getClass().getResource("/cookbook/DailyRecipeItem.fxml"));
    //     AnchorPane anchorPane = null;
    //     try {
    //         anchorPane = fxmlLoader.load();
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }

    //     DailyRecipeController drc = fxmlLoader.getController();
    //     drc.setRecipe(dailyRecipeList.get(i), ap);

    //     grid.add(anchorPane, col++, row);            
    //     grid.setMinWidth(Region.USE_COMPUTED_SIZE);
    //     grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
    //     grid.setMaxWidth(Region.USE_PREF_SIZE);

    //     grid.setMinHeight(Region.USE_COMPUTED_SIZE);
    //     grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
    //     grid.setMaxHeight(Region.USE_PREF_SIZE);


    // }
}
