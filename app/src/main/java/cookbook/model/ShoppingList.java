package cookbook.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;


/*
 * ShppingList Class. 
 */
public class ShoppingList {
    private int listId; 
    private int userId;
    private int weekId;
    private String listName;
    private Date createdDate; 
    // private ObservableList<Ingredient> shoppingListIngredients;

    // public ShoppingList(ResultSet rs, ObservableList<Ingredient> ingredientList) throws SQLException {
    public ShoppingList(ResultSet rs) throws SQLException {
        setListId(rs.getInt(1));
        setUserId(rs.getInt(2));
        setWeekId(rs.getInt(3));
        setListName(rs.getString(4));
        setCreatedDate(rs.getDate(5));
        // setShoppingListIngredients(ingredientList);

    }


    /**
     * gets list_id.
     
     * @return list_id.
     */
    public int getListId() {
        return listId;
    }

    /**
     * sets list_id.
     
     * @param listId list_id.
     */
    public void setListId(int listId) {
        this.listId = listId;
    }
    
    /**
     * gets user_id.
     
     * @return user_id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * sets user_id.
     
     * @param userId user_id.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;

    }
    
    /**
     * gets list_name.
    
     * @return list_name.
     */
    public String getListName() {
        return listName;
    }

    /**
     * sets list_name.
     
     * @param listName list_name.
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * gets created-date.
     
     * @return created_date.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * sets created-date.
     
     * @param createdDate created_date.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    // public void setShoppingListIngredients(ObservableList<Ingredient> ingredientList) {
    //     this.shoppingListIngredients = ingredientList;

    // }
    
    // public ObservableList<Ingredient> getIngredientList() {
    //     return shoppingListIngredients;
    // }
}
