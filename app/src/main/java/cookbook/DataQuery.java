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
public Label getFormattedRecipe(String recipe) throws SQLException {
  String query = "SELECT * FROM recipes WHERE recipe_name = \"" + recipe +"\"";
  Label formattedRecipe = new Label();
  Statement statement = null;
  ResultSet rs = null;
  try {
    statement = conn.createStatement();
    rs = statement.executeQuery(query);
    
    while (rs.next()) {
      formattedRecipe.setText(rs.getString(2) + "\n\n" + rs.getString(3) + "\n" + rs.getString(4)
      + "\n" + "Servings: " + rs.getString(5) + "\n" + "Prep Time: " + rs.getString(6) + " Minutes " + "\n"
      + " Cook Time: " + rs.getString(7) + " Minutes");
    } 
    } catch (SQLException e1) {
      e1.printStackTrace();
    } finally {
      closeDatabaseObjects(rs, statement, conn);
    }
    return formattedRecipe;
  }

  public List<String> searchByName(String name) throws SQLException {
    // TODO: implement a search by name method user story 6 
    return null;
  }

  public Label searchByIngredient(String ingredientName) throws SQLException {
    String query = "SELECT r.recipe_name FROM recipes r " +
    "INNER JOIN r_ingredients ri ON r.recipe_id = ri.recipe_id " +
    "INNER JOIN ingredients i ON ri.ingredient_id = i.ingredient_id " +
    "WHERE i.i_name LIKE '%" + ingredientName + "%'"; 
    Label  searchByIngredient = new Label();
    Statement statement = null;
    ResultSet rs = null;
    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);

      while (rs.next()) {
        searchByIngredient.setText(rs.getString(2) + "\n\n" + rs.getString(3) + "\n" + rs.getString(4)
        + "\n" + "Servings: " + rs.getString(5) + "\n" + "Prep Time: " + rs.getString(6) + " Minutes " + "\n"
        + " Cook Time: " + rs.getString(7) + " Minutes");
      } 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } finally {
        closeDatabaseObjects(rs, statement, conn);
      }
      return  searchByIngredient; 
      
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
}
