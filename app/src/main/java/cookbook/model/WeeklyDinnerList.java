package cookbook.model;
// this is the object class for weekly dinner list feature

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.ObservableList;

public class WeeklyDinnerList {
  private int weekId;
  private int userId;
  private String weekName;
  private int weekNumber;
  private ObservableList<ObservableList<Recipe>> weeklyMeals;

  public WeeklyDinnerList(ResultSet rt, ObservableList<ObservableList<Recipe>> mealList) throws SQLException {
    setWeekId(rt.getInt(1));
    setWeekName(rt.getString(2));
    setUserId(rt.getInt(3));
    setWeekNumber(rt.getInt(4));
    setWeeklyMeals(mealList);
  }

  /**
   * @return the week_id
   */
  public int getWeekId() {
    return weekId;
  }

  /**
   * @param week_id the week_id to set
   */
  public void setWeekId(int weekId) {
    this.weekId = weekId;
  }

  /**
   * @return the user_id
   */
  public int getUserId() {
    return userId;
  }

  /**
   * @param userId the user_id to set
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * @return the week_name
   */
  public String getWeekName() {
    return weekName;
  }

  /**
   * @param week_name the week_name to set
   */
  public void setWeekName(String weekName) {
    this.weekName = weekName;
  }

  /**
   * @return the week_number
   */
  public int getWeekNumber() {
    return weekNumber;
  }

  /**
   * @param week_number the week_number to set
   */
  public void setWeekNumber(int weekNumber) {
    this.weekNumber = weekNumber;
  }

  public void setWeeklyMeals(ObservableList<ObservableList<Recipe>> mealList) {
    this.weeklyMeals = mealList;
  }

  // public ObservableList<Recipe> getWeeklyMeals() {
  //   return weeklyMeals;
  // }

  public ObservableList<ObservableList<Recipe>> getWeeklyPlan() {
    return weeklyMeals;
  }

}
