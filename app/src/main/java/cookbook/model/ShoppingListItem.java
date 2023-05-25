package cookbook.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingListItem {
    private int itemId;
    private int listId;
    private String ingredientName;
    private int recipeId;
    private int qty;
    private String measurement;
    private boolean itemPurchased;

    public ShoppingListItem(ResultSet rs) throws SQLException {
        setItemId(rs.getInt(1));
        setListId(rs.getInt(2));
        setIngredientName(rs.getString(3));
        setRecipeId(rs.getInt(4));
        setQty(rs.getInt(5));
        setMeasurement(rs.getString(6));
        setItemPurchased(rs.getBoolean(7));
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public boolean isItemPurchased() {
        return itemPurchased;
    }

    public void setItemPurchased(boolean itemPurchased) {
        this.itemPurchased = itemPurchased;
    }
    @Override
    public String toString() {
        return ingredientName + "\t" + String.valueOf(qty) + "\t" + measurement;
    }
    
}
