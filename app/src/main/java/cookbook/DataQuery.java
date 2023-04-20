package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataQuery {
  private String database = "jdbc:mysql://localhost/Cookbook?user=root&password=!!@@qqww3344EERR&useSSL=false";
  private Connection conn;

  public DataQuery() throws SQLException {
    this.conn = DriverManager.getConnection(database);
  }

  public boolean checkCredentials(String username, String password) throws SQLException {
    boolean credentials = false;
    String query = "SELECT * FROM users WHERE username = '"+ username+"' AND password = '"+ password+"';";  
    try {
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(query);
      
      if (rs.next()) {
        credentials = true;
      } 
      } catch (SQLException e) {
        e.printStackTrace();
      }
    return credentials;
  }

  public void close() {
    try {
        conn.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
  }
}
