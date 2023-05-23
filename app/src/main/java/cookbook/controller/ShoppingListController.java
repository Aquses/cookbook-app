package cookbook.controller;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.Cookbook;
import cookbook.model.Ingredient;
import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.ShoppingList;
import cookbook.model.ShoppingListItem;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShoppingListController {

    @FXML
    private AnchorPane ap;
    @FXML
    private Label InstructionLabel;
    @FXML
    private Button NewListButton;
    @FXML
    private Button ClearListButton;


    private User user;
    private ShoppingList shoppingList;
    private ObservableList<ShoppingListItem> shoppingListItems;
    private WeeklyDinnerList weeklyPlan;

    @FXML
    public void initialize() {
        this.user = Session.getCurrentUser();
        testingShoppingList();
        testingWeeklyPlan();
        testingInsertItems();

        // Testing in console if ingredients are retrieved. Currently does not handle duplicates or combine qty
        for (ShoppingListItem item : shoppingListItems) {
            System.out.println(item.getIngredientName() + " " + item.getQty() + " " + item.getMeasurement());
        }

        NewListButton.setOnMouseClicked(event -> {
            openListChoiceWindow();
        });
    }

    private void openListChoiceWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Cookbook.class.getResource("SelectWeeklyPlanWindow.fxml"));

            Parent window = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Select a Weekly Plan");
            stage.setScene(new Scene(window, 339, 508));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void testingShoppingList() {

        try {
            QueryMaker qm = new QueryMaker();
            ShoppingList shoppingList = qm.retrieveShoppingList(1, user);
            this.shoppingList = shoppingList;

            ObservableList<ShoppingListItem> shoppingListItems = qm.retrieveShoppingListItems(1);
            this.shoppingListItems = shoppingListItems;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // This should eventually not be here. When the user selects a weekly plan to create a shopping list, the weeklyplan object should be passed into 
    // the parameter of a method that should be used in this controller class. This will allow the inserting of shopping list items and the
    // "retrieveShoppingWeeklyPlan()" method can be removed.

    private void testingWeeklyPlan() {

        try {
            QueryMaker qm = new QueryMaker();
            WeeklyDinnerList weeklyPlan = qm.retrieveShoppingWeeklyPlan(1, user);
            this.weeklyPlan = weeklyPlan;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void testingInsertItems() {

        try {
            QueryMaker qm = new QueryMaker();
            qm.insertShoppingListItems(weeklyPlan, shoppingList.getListId());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }            

}
