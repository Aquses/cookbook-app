package cookbook.model;
// this is the object class for weekly dinner list feature

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.ObservableList;

public class WeeklyDinnerList {
  private int week_id;
  private int user_id;
  private String week_name;
  private int week_number;

  private ObservableList<Recipe> dinnerList;

  public WeeklyDinnerList(ResultSet rt) throws SQLException {
    setWeek_id(rt.getInt(1));
    setUser_id(rt.getInt(3));

    setWeek_name(rt.getString(2));
    setWeek_number(rt.getInt(4));

  }

  /**
   * @return the week_id
   */
  public int getWeek_id() {
    return week_id;
  }

  /**
   * @param week_id the week_id to set
   */
  public void setWeek_id(int week_id) {
    this.week_id = week_id;
  }

  /**
   * @return the user_id
   */
  public int getUser_id() {
    return user_id;
  }

  /**
   * @param user_id the user_id to set
   */
  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  /**
   * @return the week_name
   */
  public String getWeek_name() {
    return week_name;
  }

  /**
   * @param week_name the week_name to set
   */
  public void setWeek_name(String week_name) {
    this.week_name = week_name;
  }

  /**
   * @return the week_number
   */
  public int getWeek_number() {
    return week_number;
  }

  /**
   * @param week_number the week_number to set
   */
  public void setWeek_number(int week_number) {
    this.week_number = week_number;
  }

}
