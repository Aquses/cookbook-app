package cookbook.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import cookbook.model.Comment;
import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.view.DisplayRecipeScene;
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
  private User user;

  private DisplayRecipeScene drsController;

  public void setData(Comment comment, AnchorPane parent, DisplayRecipeScene drsController) {
    this.drsController = drsController;
    int commentId = comment.getId();

    try {
      QueryMaker qm = new QueryMaker();
      User commentUser = qm.retrieveCommentUser(commentId);

      //String username = commentUser.getUsername();
      String username = commentUser.getFname() + " " + commentUser.getLname();
      myusername.setText(username);

    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }

    this.comment = comment;

    this.mycomment.setText(comment.getComment_text());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    myDate.setText(dateFormat.format(comment.getDate()));
    int userid = comment.getUser_id();

    if (userid != Session.getCurrentUser().getUserId()) {
      editButton.setVisible(false);
      deleteButton.setVisible(false);
      mycomment.setDisable(true);

    }
  }

  @FXML
  void editComment(ActionEvent event) throws SQLException {
    String updatedComment = mycomment.getText();
    int userid = comment.getUser_id();

    if (userid == Session.getCurrentUser().getUserId()) {

      try {

        comment.setComment_text(updatedComment);
        QueryMaker qm = new QueryMaker();
        qm.editComment(comment);
      } catch (SQLException e) {

      }

    } else {
      System.out.println("only the creator of the comment can edit it.");
    }

    drsController.reloadComments();
  }

  @FXML
  void removeComment(ActionEvent event) {
    int userid = comment.getUser_id();
    if (userid == Session.getCurrentUser().getUserId()) {
      try {
        QueryMaker qm = new QueryMaker();
        qm.deleteComment(comment);
      } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
      }
    } else {
      System.out.println("only the creator of the comment can delete it");
    }

    drsController.reloadComments();
  }

}
