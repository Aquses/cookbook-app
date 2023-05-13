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

}
