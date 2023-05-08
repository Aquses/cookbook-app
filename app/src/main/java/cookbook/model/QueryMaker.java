package cookbook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

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

    public ObservableList<User> getAllusers() throws SQLException{
        // here we want a generic search, but the search text needs to be filtered and found based on the query.
        query = "SELECT * FROM users;";
        return setUserToList();
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


    public void updateIngredient(Ingredient updatedIngredient, String originalName, int recipeId) {
        String newName = updatedIngredient.getIngredientName();
        int newQty = updatedIngredient.getQty();
        String newMeasurement = updatedIngredient.getMeasurement();

    
        String query = "UPDATE ingredients "
                    + "SET i_name = ?, qty = ?, measurement = ? "
                    + "WHERE i_name = ? and recipe_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newName);
            statement.setInt(2, newQty);
            statement.setString(3, newMeasurement);
            statement.setString(4, originalName);
            statement.setInt(5, recipeId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    public void deleteIngredient(Ingredient ingredient, int recipeId) {
        String ingredientName = ingredient.getIngredientName();
        String query = "DELETE from ingredients "
                    + "WHERE i_name = ? and recipe_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, ingredientName);
            statement.setInt(2, recipeId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    public void addIngredient(Ingredient ingredient) {
        String query = "INSERT INTO ingredients VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, ingredient.getIngredientName());
            statement.setInt(2, ingredient.getRecipeId());
            statement.setInt(3, ingredient.getQty());
            statement.setString(4, ingredient.getMeasurement());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void updateRecipe(Recipe recipe) {
        String query = "UPDATE recipes "
                    + "SET recipe_name = ?, recipe_description = ?, recipe_instructions = ?, "
                    + "servings = ?, prep_time_minutes = ?, cook_time_minutes = ? "
                    + "WHERE recipe_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getDescription());
            statement.setString(3, recipe.getInstructions());
            statement.setInt(4, recipe.getServings());
            statement.setFloat(5, recipe.getPrepTime());
            statement.setFloat(6, recipe.getCookTime());
            statement.setInt(7, recipe.getId());

            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ObservableList<User> setUserToList() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        User user;
        results = statement.executeQuery(query);

        while (results.next()) {
            user = new User(results);
            list.add(user);
        }
        return list;
    }

    
    /**
     * Inserts new messages into message table.
     
     * @param message message. 
     * @throws SQLException
     */
    public void saveMessage(Message message) throws SQLException {
        String sqlQuery = "INSERT INTO messages(message_id, sender_id, receiver_id, recipe_id, content, date_created) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement  statement = conn.prepareStatement(sqlQuery)) {
    
            statement.setInt(1, message.getSenderId());
            statement.setInt(2, message.getReceiverId());
            statement.setString(3, message.getContent());
            statement.setInt(4, message.getRecipeId());
            statement.setInt(5, message.getMessageId());
            statement.setDate(6, message.getDateCreated());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
   
    }


    /**
     * gets the messages.
    
     * @param loggedInUser loggedInUser.
     * @return messages.
     * @throws SQLException
     */
    public ObservableList<Message> getMessagesForUser(String loggedInUser) throws SQLException {
        String sqlQuery = "SELECT * FROM messages WHERE sender_id = ? OR receiver_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setString(1, loggedInUser);
            statement.setString(2, loggedInUser);
            ResultSet rs = statement.executeQuery();
            ObservableList<Message> messages = FXCollections.observableArrayList();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("sender_id"), rs.getInt("receiver_id"), rs.getString("content"), rs.getInt("recipe_id"), rs.getDate("date_created", null));
                messages.add(message);
            }
            return messages;
        }
    }
    



}
