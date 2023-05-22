package cookbook.model;

import javafx.fxml.FXML;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
  private String comment_text;
  private Date date;
  private int id;
  private int User_id;
  private int recipe_id;


  public Comment(ResultSet rt) throws SQLException {
    setId(rt.getInt(1));
    setUser_id(rt.getInt(2));
    setRecipe_id(rt.getInt(3));
    setComment_text(rt.getString(4));
    setDate(rt.getDate(5));

  }

  // a method to set the date format for the date of comments
  public Date StringToDate(String s) {

    Date result = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      result = dateFormat.parse(s);
    }

    catch (ParseException e) {
      e.printStackTrace();

    }
    return result;
  }

  /**
   * @return the comment_text
   */
  public String getComment_text() {
    return comment_text;
  }

  /**
   * @param comment_text the comment_text to set
   */
  public void setComment_text(String comment_text) {
    this.comment_text = comment_text;
  }

  /**
   * @return the date
   */
  public Date getDate() {

    return date;

  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the user_id
   */
  public int getUser_id() {
    return User_id;
  }

  /**
   * @param user_id the user_id to set
   */
  public void setUser_id(int user_id) {
    User_id = user_id;
  }

  /**
   * @return the recipe_id
   */
  public int getRecipe_id() {
    return recipe_id;
  }

  /**
   * @param recipe_id the recipe_id to set
   */
  public void setRecipe_id(int recipe_id) {
    this.recipe_id = recipe_id;
  }

}
