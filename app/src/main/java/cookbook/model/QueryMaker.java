package cookbook.model;

import cookbook.view.DisplayRecipeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.ArrayList;
//import java.util.List;
import java.util.List;

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

    public ObservableList<Recipe> getFavoriteRecipes(int user_id) throws SQLException {
        query = "SELECT recipes.recipe_id, recipes.recipe_name, recipes.recipe_description, recipes.recipe_instructions, recipes.servings, recipes.servings, recipes.cook_time_minutes FROM favorites JOIN recipes ON favorites.recipe_id = recipes.recipe_id WHERE favorites.user_id = " + user_id + ";";
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

    // user story 8, Eldaras, query loads tags and custom_tags. 
    // I'm afraid of optimization. at some point it was lagging due to overload of recipes.
    public List<String> getCustomTagsForRecipe(int recipe_id, int user_id) throws SQLException {
        List<String> customTags = new ArrayList<>();
        Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
    
        String tagsQuery = "SELECT tags.tag_name " +
                           "FROM tags " +
                           "JOIN recipe_tags ON tags.tag_id = recipe_tags.tag_id " +
                           "WHERE recipe_tags.recipe_id = ?";
    
        String customTagsQuery = "SELECT custom_tags.ctag_name " +
                                 "FROM custom_tags " +
                                 "JOIN recipe_ctags ON custom_tags.ctag_id = recipe_ctags.ctag_id " +
                                 "WHERE recipe_ctags.recipe_id = ? AND custom_tags.user_id = ?";
    
        try (PreparedStatement tagsStatement = conn2.prepareStatement(tagsQuery);
             PreparedStatement customTagsStatement = conn2.prepareStatement(customTagsQuery)) {
            tagsStatement.setInt(1, recipe_id);
            customTagsStatement.setInt(1, recipe_id);
            customTagsStatement.setInt(2, user_id);
    
            ResultSet tagsResultSet = tagsStatement.executeQuery();
            ResultSet customTagsResultSet = customTagsStatement.executeQuery();
    
            while (tagsResultSet.next()) {
                String tagName = tagsResultSet.getString("tag_name");
                customTags.add(tagName);
            }
    
            while (customTagsResultSet.next()) {
                String ctagName = customTagsResultSet.getString("ctag_name");
                customTags.add(ctagName);
            }
        } finally {
            conn2.close();
        }
        return customTags;
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

    // private ObservableList<Tags> setTagsToList() throws SQLException {
    //     ObservableList<Tags> list = FXCollections.observableArrayList();
    //     Tags tag;
    //     results = statement.executeQuery(query);

    //     while (results.next()) {
    //         tag = new Tags(results);
    //         list.add(tag);
    //     }
    //     return list;
    // }


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

    
    // public ObservableList<Message> retrieveMessages(int userId) {
    public ObservableList<Message> retrieveMessages(User user) {       
        //Change parameter to user object
        ObservableList<Message> messageList = FXCollections.observableArrayList();
        String query = "SELECT * FROM messages WHERE receiver_id = ? ORDER BY date_created DESC";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            // statement.setInt(1, userId);
            
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Message msg = new Message(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                                          rs.getInt(4), rs.getString(5), rs.getTimestamp(6));

                // Maybe convert rs.getTimestamp to localDateTime object
                messageList.add(msg);
            }

            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return messageList;
    }

    public ObservableList<Recipe> retrieveMessageRecipes(User user) {
        ObservableList<Recipe> recipeList = FXCollections.observableArrayList();
        String query = "SELECT * "
                     + "FROM recipes as r "
                     + "JOIN messages as m on m.recipe_id = r.recipe_id "
                     + "WHERE m.receiver_id = ? "
                     + "ORDER BY m.date_created DESC";
        
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            // statement.setInt(1, user.getUserId());
            // statement.setInt(1, userId);
            statement.setInt(1, user.getUserId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Recipe recipe = new Recipe(rs);
                recipeList.add(recipe);
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return recipeList;
    }

    public User retrieveSender(Message msg) {
        String query = "SELECT u.* "
                     + "FROM users as u "
                     + "JOIN messages as m on m.sender_id = u.user_id "
                     + "WHERE m.message_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, msg.getMessageId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User(rs);
                rs.close();
                statement.close();
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public ObservableList<WeeklyDinnerList> retrieveWeeklyListObjects(User user) {

        ObservableList<WeeklyDinnerList> weeklyPlansList = FXCollections.observableArrayList();
        
        String query = "SELECT * "
                     + "FROM week_plan "
                     + "WHERE user_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getUserId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ObservableList<ObservableList<Recipe>> weeklyListRecipes = FXCollections.observableArrayList();

                int weekId = rs.getInt(1);
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Monday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Tuesday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Wednesday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Thursday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Friday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Saturday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Sunday", weekId));
                
                WeeklyDinnerList weeklyList = new WeeklyDinnerList(rs, weeklyListRecipes);
                weeklyPlansList.add(weeklyList);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return weeklyPlansList;
    }

    public ObservableList<Recipe> retrieveDailyRecipes(User user, String day, int weekId) {
        ObservableList<Recipe> dayRecipeList = FXCollections.observableArrayList();

        String query = "SELECT r.* "
                     + "FROM daily_recipes as dr "
                     + "JOIN week_plan as wp on wp.week_id = dr.week_id "
                     + "JOIN recipes as r on r.recipe_id = dr.recipe_id "
                     + "JOIN users as u on u.user_id = wp.user_id "
                     + "WHERE u.user_id = ? and dr.day_of_week = ? and wp.week_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            statement.setString(2, day);
            statement.setInt(3, weekId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Recipe recipe = new Recipe(rs);
                dayRecipeList.add(recipe);
            }

            rs.close();
            statement.close();


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        

        return dayRecipeList;
        
    }

    public void editComment(Comment comment) {
        String query = "UPDATE comments SET content = ? WHERE comment_id = ?";
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
        String query = "DELETE FROM comments WHERE comment_id = ?";
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
        user_id = Session.getCurrentUser().getUserId();
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


    public User retrieveCommentUser(int commentId) {
        String query = "SELECT u.* "
                     + "FROM users as u "
                     + "JOIN comments as c on c.user_id = u.user_id "
                     + "WHERE c.comment_id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, commentId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User(rs);
                rs.close();
                statement.close();
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        
        return null;
    }

    

    public void insertWeeklyPlan(String weekName, int weekNumber, int userId) throws SQLException {
        String query = "INSERT INTO week_plan (week_name, week_number, user_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, weekName);
            statement.setInt(2, weekNumber);
            statement.setInt(3, userId);

            statement.executeUpdate();
        }
    }

    public void deleteWeeklyPlan(int weekId) throws SQLException {
        String query = "DELETE FROM week_plan WHERE week_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, weekId);
            statement.executeUpdate();
        }
    }

    public void insertDailyRecipe(int weekId, String dayOfWeek, int recipeId) throws SQLException {
        String query = "INSERT INTO daily_recipes (week_id, day_of_week, recipe_id) VALUES (?, ?, ?)";
    
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, weekId);
            statement.setString(2, dayOfWeek);
            statement.setInt(3, recipeId);
    
            statement.executeUpdate();
        }
        // }
    }

    public WeeklyDinnerList retrieveCurrentWeeklyPlan(int weekNumber, User user) {
        String query = "SELECT * FROM week_plan WHERE week_number = ? AND user_id = ?";


        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, weekNumber);
            statement.setInt(2, user.getUserId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                
                ObservableList<ObservableList<Recipe>> weeklyListRecipes = FXCollections.observableArrayList();

                int weekId = rs.getInt(1);
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Monday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Tuesday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Wednesday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Thursday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Friday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Saturday", weekId));
                weeklyListRecipes.add(retrieveDailyRecipes(user, "Sunday", weekId));
                
                WeeklyDinnerList currentWeeklyList = new WeeklyDinnerList(rs, weeklyListRecipes);
                return currentWeeklyList;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * 
     * @param numberOfWeeks numberOfWeeks.
     * @return dateList.
     */
    /*public static List<LocalDate> getNextWeeks(int numberOfWeeks) {
        List<LocalDate> dateList = new ArrayList<>();
        final ZonedDateTime input = ZonedDateTime.now();
    
        for (int i = 0; i < numberOfWeeks; i++) {
            final ZonedDateTime startOfLastWeek = input.plusWeeks(i).with(DayOfWeek.MONDAY);
            dateList.add(startOfLastWeek.toLocalDate());
        }

        while (results.next()) {
            comment = new Comment(results);
            list.add(comment);
        }
        return list;
    }
        return dateList;
    }
}*/
}