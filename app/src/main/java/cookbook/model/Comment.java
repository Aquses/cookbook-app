package cookbook.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
  private String comment_text;
  private Date date;

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

}
