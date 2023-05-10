package cookbook.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cookbook.model.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CommentController {

  @FXML
  private Button deleteButton;

  @FXML
  private Button editButton;

  @FXML
  private TextArea mycomment;

  @FXML
  private Label myDate;

  @FXML
  private Label myusername;

  private Comment comment; // Assume Comment class represents a comment object

  private AnchorPane parentAnchorPane;

  public void setData(Comment comment, AnchorPane parent) {
    this.comment = comment;
    parentAnchorPane = parent;
    this.mycomment.setText(comment.getComment_text());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    myDate.setText(dateFormat.format(comment.getDate()));
  }

  @FXML
  void editComment(ActionEvent event) throws SQLException {

  }

  @FXML
  void removeComment(ActionEvent event) {

  }

}
