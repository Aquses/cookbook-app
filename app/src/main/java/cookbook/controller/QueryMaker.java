package cookbook.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryMaker {
    Connection conn;
    Statement statement;
    ResultSet results;
    String query;

    public QueryMaker() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
        statement = conn.createStatement();
    }

    /*
    public List<Recipe> getAllRecipes() throws SQLException{
        query = "SELECT * FROM recipes";
        return setToList();
    }*/

    public ObservableList<Recipe> getAllRecipes() throws SQLException{
        query = "SELECT * FROM recipes";
        return setToList();
    }

    public ObservableList<Recipe> getRecipesFromSQLQuery(String query) throws SQLException{
        this.query = query;
        return setToList();
    }

    public ObservableList<Recipe> getFavoriteRecipes() throws SQLException{
        // the idea here is to find favorites based on the id of the users' favorites.
        query = "";
        return setToList();
    }

    public ObservableList<Recipe> getSearchResults() throws SQLException{
        // here we want a generic search, but the search text needs to be filtered and found based on the query.
        query = "";
        return setToList();
    }

    /*
    private List<Recipe> setToList() throws SQLException {
        List<Recipe> list = new ArrayList<>();
        Recipe recipe;
        results = statement.executeQuery(query);

        while (results.next()) {
            recipe = new Recipe(results);
            list.add(recipe);
        }
        return list;
    }*/

    private ObservableList<Recipe> setToList() throws SQLException {
        ObservableList<Recipe> list = FXCollections.observableArrayList();
        Recipe recipe;
        results = statement.executeQuery(query);

        while (results.next()) {
            recipe = new Recipe(results);
            list.add(recipe);
        }
        return list;
    }


    public ObservableList<Ingredient> retrieveIngredients(int recipeId) {
        ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
        String query = "SELECT * FROM ingredients WHERE recipe_id = ?";    
        try { 
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, recipeId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
    
                Ingredient ingredient = new Ingredient(rs.getString(1), rs.getInt(2),
                                                         rs.getInt(3), rs.getString(4));
                ingredientList.add(ingredient);
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientList;


    }
}
