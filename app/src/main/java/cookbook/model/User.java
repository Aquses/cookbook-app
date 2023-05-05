package cookbook.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
  private int userId;
  private String fname;
  private String lname;
  private boolean isAdmin;
  private String username;
  private String password;

  public User(ResultSet rt) {
    try {
      setUserId(rt.getInt(1));
      setFname(rt.getString(2));
      setLname(rt.getString(3));
      setIsAdmin(rt.getInt(4));
      setUsername(rt.getString(5));
      setPassword(rt.getString(6));
    } catch (SQLException e) {
      throw new RuntimeException(e);
        }
    }

  public int getUserId() {
    return userId;
  }

  private void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFname() {
    return fname;
  }

  private void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  private void setLname(String lname) {
    this.lname = lname;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

  private void setIsAdmin(int isAdmin) {
    if (isAdmin == 1) {
      this.isAdmin = true;
    } else {
      this.isAdmin = false;
    }
  }

  public String getUsername() {
    return username;
  }

  private void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  private void setPassword(String password) {
    this.password = password;
  }

  public String toString() {
    return String.format("%-5s %-30s %-30s %-30s %-30s %s", userId, fname, lname, username, password, isAdmin);
  }
}
