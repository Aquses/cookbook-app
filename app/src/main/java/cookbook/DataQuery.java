package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.PreparedStatement;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DataQuery {
  private String database = "jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false";
  private Connection conn;

  // this method closes database objects and helps reduce code repetition
  public void closeDatabaseObjects(ResultSet rs, Statement stmt, Connection conn) {
    try {
        if (rs != null) {
            rs.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    try {
        if (stmt != null) {
            stmt.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    try {
        if (conn != null) {
            conn.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  public DataQuery() {
    try {
    this.conn = DriverManager.getConnection(database);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean checkCredentials(String username, String password) {
    boolean credentials = false;
    Statement statement = null;
    ResultSet rs = null;
    String query = "SELECT * FROM users WHERE username = '"+ username+"' AND password = '"+ password+"';";  
    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);
      
      if (rs.next()) {
        credentials = true;
      } 
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        closeDatabaseObjects(rs, statement, conn);
      }
      return credentials;
  }

  // method to get a list of all recipes by name in the database
  public List<String> getAllRecipes() throws SQLException {
    String query = "SELECT recipe_name FROM recipes";
    List<String> recipes = new ArrayList<>();
    Statement statement = null;
    ResultSet rs = null;
    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);
      
      while (rs.next()) {
        recipes.add(rs.getString(1));
      } 
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        closeDatabaseObjects(rs, statement, conn);
      }
    
    return recipes;
  }

// method to return a formattetd recipe label -- this method will get very beefy **TODO**
// user story #13 for Sean
public VBox getFormattedRecipe(String recipe) throws SQLException {
  String query = "SELECT * FROM recipes WHERE recipe_name = \"" + recipe +"\"";
  VBox formattedRecipe = new VBox();
  Statement statement = null;
  ResultSet rs = null;
  try {
    statement = conn.createStatement();
    rs = statement.executeQuery(query);
    
    while (rs.next()) {
      Label recipeName = new Label(rs.getString(2) + "\n");
      recipeName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
      Label shortDescription = new Label(rs.getString(3) + "\n\n");
      shortDescription.setStyle("-fx-font-style: italic;");
      Label instructions = new Label(rs.getString(4));
      Label ingredientsLabel = new Label("\nIngredients");
      ingredientsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
      ingredientsLabel.setUnderline(true);
    
      //ingredients adding loop
      StringBuilder ingredientsString = new StringBuilder();
      for (String string : getIngredients(recipe)) {
        ingredientsString.append(string).append("\n");
      }
      Label ingredients = new Label(ingredientsString.toString());

      Label servings = new Label("\nServings: " + rs.getString(5) + "\n");

      Label prepTime = new Label("Prep Time: " + rs.getString(6) + " Minutes " + "\n");

      Label cookTime = new Label("Cook Time: " + rs.getString(7) + " Minutes " + "\n");

    formattedRecipe.getChildren().addAll(recipeName, shortDescription, instructions, ingredientsLabel,
      ingredients, servings, prepTime, cookTime);
    } 
    
    } catch (SQLException e1) {
      e1.printStackTrace();
    } finally {
      closeDatabaseObjects(rs, statement, conn);
    }
    return formattedRecipe;
  }

  public List<String> searchByName(String name) throws SQLException {
    String query = "SELECT * FROM recipes";
    Statement statement = null;
    ResultSet rs = null;
    List<String> recipeList = new ArrayList<>();
    try {
        statement = conn.createStatement();
        rs = statement.executeQuery(query);

        while (rs.next()) {
            if (rs.getString(2).toLowerCase().contains(name.toLowerCase())) {
                recipeList.add(rs.getString(2) + "\n\n" + rs.getString(3) + "\n" + rs.getString(4)
                        + "\n" + "Servings: " + rs.getString(5) + "\n" + "Prep Time: " + rs.getString(6) + " Minutes " + "\n"
                        + "Cook Time: " + rs.getString(7) + " Minutes");
            }
        }
    } catch (SQLException e1) {
        e1.printStackTrace();
    } finally {
        closeDatabaseObjects(rs, statement, conn);
    }
    return recipeList;
  }

  public List<String> searchByIngredient(String name) throws SQLException {
    String query = "SELECT r.recipe_name FROM recipes r " +
    "INNER JOIN r_ingredients ri ON r.recipe_id = ri.recipe_id " +
    "INNER JOIN ingredients i ON ri.ingredient_id = i.ingredient_id " +
    "WHERE i.i_name LIKE '%" + name + "%'"; 
    Statement statement = null;
    ResultSet rs = null;
    List<String> recipeList = new ArrayList<>();
    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);
      while (rs.next()) {
        if (rs.getString(2).toLowerCase().contains(name.toLowerCase())) {
            recipeList.add(rs.getString(2) + "\n\n" + rs.getString(3) + "\n" + rs.getString(4)
                    + "\n" + "Servings: " + rs.getString(5) + "\n" + "Prep Time: " + rs.getString(6) + " Minutes " + "\n"
                    + "Cook Time: " + rs.getString(7) + " Minutes");
        }
       }  
      } catch (SQLException e1) {
        e1.printStackTrace();
      } finally {
        closeDatabaseObjects(rs, statement, conn);
      }
      return  recipeList; 
  }

  public List<String> searchByTag(String tagName, List<String> listOfStrings) throws SQLException {
    String tagQuery = "SELECT * FROM tags WHERE tags_name = ?";
    PreparedStatement statement = null;
    ResultSet rs = null;

    try {
        statement = conn.prepareStatement(tagQuery);
        statement.setString(1, tagName);
        rs = statement.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    List<String> searchedTag = new ArrayList<>();
    if (rs.next()) {
        String tagString = rs.getString("tags_name");
        searchedTag = Arrays.asList(tagString.trim().split(" "));
    }

    List<String> resultList = new ArrayList<>();
    for (String input : listOfStrings) {
        if (searchedTag.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()))) {
            resultList.add(input);
        }
    }
    return resultList;
  }

  public List<String> getIngredients(String recipe) throws SQLException {
    List<String> ingredients = new ArrayList<>();
    String query = "SELECT i.i_name, ri.qty, ri.measurement " +
    "FROM ingredients i " +
    "INNER JOIN r_ingredients ri ON i.ingredient_id = ri.ingredient_id " +
    "INNER JOIN recipes r ON ri.recipe_id = r.recipe_id " +
    "WHERE r.recipe_name = \""+ recipe +"\"";

    Statement statement = null;
    ResultSet rs = null;

    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);

      while (rs.next()) {
        String line = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
        ingredients.add(line);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  public void addRecipe(String recipeName, String recipeDesc, String recipeInstructions,int servings, int prepTime, int cookTime) {
  
    String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes) " +
    "VALUES ('" + recipeName + "', '" + recipeDesc + "', '" + recipeInstructions + "', " +
    servings + ", " + prepTime + ", " + cookTime + ")";
    Statement statement = null;
    ResultSet rs = null;

    try {
      statement = conn.createStatement();
      statement.executeUpdate(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
    closeDatabaseObjects(rs, statement, conn);
}
  }



}
