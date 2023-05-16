package cookbook.model;

import cookbook.view.DisplayRecipeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;

import com.mysql.cj.xdevapi.DatabaseObject;

public class QueryMaker {
    Connection conn;
    Statement statement;
    ResultSet results;
    String query;
    PreparedStatement prepStatement;

    public QueryMaker() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
        statement = conn.createStatement();
    }

    /*
     * public List<Recipe> getAllRecipes() throws SQLException{
     * query = "SELECT * FROM recipes";
     * return setToList();
     * }
     */

    public ObservableList<Recipe> getAllRecipes() throws SQLException {
        query = "SELECT * FROM recipes";
        return setToList();
    }

    public ObservableList<Recipe> getRecipesFromSQLQuery(String query) throws SQLException {
        this.query = query;
        return setToList();
    }

    public ObservableList<Recipe> getFavoriteRecipes() throws SQLException {
        // the idea here is to find favorites based on the id of the users' favorites.
        query = "";
        return setToList();
    }

    public ObservableList<Recipe> getSearchResults() throws SQLException {
        // here we want a generic search, but the search text needs to be filtered and
        // found based on the query.
        query = "";
        return setToList();
    }

    public ObservableList<User> getAllusers() throws SQLException {
        // here we want a generic search, but the search text needs to be filtered and
        // found based on the query.
        query = "SELECT * FROM users;";
        return setUserToList();
    }

    /*
     * private List<Recipe> setToList() throws SQLException {
     * List<Recipe> list = new ArrayList<>();
     * Recipe recipe;
     * results = statement.executeQuery(query);
     * 
     * while (results.next()) {
     * recipe = new Recipe(results);
     * list.add(recipe);
     * }
     * return list;
     * }
     */

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

    public void editComment(Comment comment) {
        String query = "UPDATE comment SET body = ? WHERE id = ?";
        try {

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, comment.getComment_text());
            statement.setInt(2, comment.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Comment updated successfully!");
            } else {
                System.out.println("No comment found with the specified ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteComment(Comment comment) {
        String query = "DELETE FROM comments WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, comment.getId());
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Comment deleted successfully!");
            } else {
                System.out.println("No comment found with the specified ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Use comment object here in place of the string
    public void sendComment(String comment, Recipe recipe) {
        query = "INSERT INTO comments(user_id, recipe_id, content, comment_Date)" +
                "VALUES(?, ?, ?, ?)";
        int user_id, recipe_id;

        // Id is set to auto-increment, so it is not set here
        // User id who. Who is the current user? TODO: this is being auto-set to
        // anthony. Needs to get from context.
        user_id = 2;
        // Recipe id
        recipe_id = recipe.getId();
        // Comment is already available from function input
        // Get data on "sqlToday"
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        java.util.Date today = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());

        try {
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, user_id);
            prepStatement.setInt(2, recipe_id);
            prepStatement.setString(3, comment);
            prepStatement.setDate(4, sqlToday);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ObservableList<Comment> getThisRecipesComments(Recipe recipe) throws SQLException {
        query = "SELECT * FROM comments WHERE recipe_id = " + recipe.getId();
        return commentsToList();
    }

    private ObservableList<Comment> commentsToList() throws SQLException {
        ObservableList<Comment> list = FXCollections.observableArrayList();
        Comment comment;

        results = statement.executeQuery(query);

        while (results.next()) {
            comment = new Comment(results);
            list.add(comment);
        }
        return list;
    }
}
