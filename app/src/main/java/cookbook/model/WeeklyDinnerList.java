package cookbook.model;
// this is the object class for weekly dinner list feature

import java.sql.Date;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

public class WeeklyDinnerList {
  private int week_id;
  private int user_id;
  private Date start_Date;
  private Date end_date;

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
   * @return the start_Date
   */
  public Date getStart_Date() {
    return start_Date;
  }

  /**
   * @param start_Date the start_Date to set
   */
  public void setStart_Date(Date start_Date) {
    this.start_Date = start_Date;
  }

}
