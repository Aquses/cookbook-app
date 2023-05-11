package cookbook.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.PreparedStatement;

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

  public ResultSet getUser(String username, String password) {
    Statement statement = null;
    ResultSet rs = null;

    String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";

    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);
      if(rs.next()) {
        return rs;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }


  

  // public int checkCredentials(String username, String password) {
  //   int user_id = -1;
  //   Statement statement = null;
  //   ResultSet rs = null;
  //   String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
  //   try {
  //     statement = conn.createStatement();
  //     rs = statement.executeQuery(query);

  //     if (rs.next()) {
  //       user_id = rs.getInt("user_id");
  //     }
  //   } catch (SQLException e) {
  //     e.printStackTrace();
  //   } finally {
  //     closeDatabaseObjects(rs, statement, conn);
  //   }
  //   return user_id;
  // }

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
  
  public void deleteUser(int id) {
    String query = "DELETE FROM users WHERE user_id = " + id;
    Statement statement = null;

    try {
      statement = conn.createStatement();

      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addUser(String fname, String lname, String username, String password) {
    String query = "INSERT INTO users (fname, lname, is_admin, username, password) VALUES ('" + fname + "', '" + lname + "', " + 0 + ", '" + username + "', '" + password + "')";
    Statement statement = null;

    try {
      statement = conn.createStatement();

      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void editUser(int userId, String fname, String lname, String username, String password) {
    String query = "UPDATE users SET fname='" + fname + "', lname='" + lname + "', username='"
       + username + "', password='" + password + "' WHERE user_id=" + userId;
    Statement statement = null;

    try {
      statement = conn.createStatement();
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

    public void addMessage(int senderId, int receiverId, int recipeId, String content) {
    String query = "INSERT INTO messages (sender_id, receiver_id, recipe_id, content, date_created) VALUES (" +
                   senderId + ", " + receiverId + ", " + recipeId + ", '" + content + "', CURRENT_TIMESTAMP)";
    Statement statement = null;

    try {
        statement = conn.createStatement();
        statement.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  public boolean isFavorite(int user_id, int recipe_id) throws SQLException {
    String query = "SELECT * FROM favorites WHERE user_id = " + user_id + " AND recipe_id = " + recipe_id + ";";
    Statement statement = null;
    ResultSet rs = null;
    List<String> recipeList = new ArrayList<>();
    try {
      statement = conn.createStatement();
      rs = statement.executeQuery(query);

      if (rs.next()) {
        return true;
      }

    } catch (SQLException e1) {
      e1.printStackTrace();
    } finally {
      closeDatabaseObjects(rs, statement, conn);
    }
    return false;
  }

  public void insertFavorite(int user_id, int recipe_id) {
    String query = "INSERT INTO favorites (user_id, recipe_id) VALUES (" + user_id + ", " + recipe_id + ");";
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

  public void removeFavorite(int user_id, int recipe_id) {
    String query = "DELETE FROM favorites WHERE user_id = " + user_id + " AND recipe_id = " + recipe_id + ";";
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
