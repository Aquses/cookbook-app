package cookbook.controller;

import java.sql.SQLException;

import cookbook.model.Ingredient;
import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.ShoppingList;
import cookbook.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ShoppingListController {

    @FXML
    private AnchorPane ap;

    private User user;
    private ShoppingList shoppingList;

    @FXML
    public void initialize() {
        // testing out if shopping list object works
        this.user = Session.getCurrentUser();
        testingShoppingList();

        ObservableList<Ingredient> ingredientList = shoppingList.getIngredientList();

        System.out.println("Testing if ingredients exist in this shopping list: ");
        for (Ingredient i : ingredientList) {
            System.out.println(i.getIngredientName() + " " + i.getMeasurement());   
        }

    
    }

    private void testingShoppingList() {

        try {
            QueryMaker qm = new QueryMaker();
            ShoppingList shoppingList = qm.retrieveShoppingList(1, user);

            this.shoppingList = shoppingList;
        
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
