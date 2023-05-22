package cookbook.controller;

import java.sql.SQLException;

import cookbook.model.Ingredient;
import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.ShoppingList;
import cookbook.model.ShoppingListItem;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ShoppingListController {

    @FXML
    private AnchorPane ap;

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
