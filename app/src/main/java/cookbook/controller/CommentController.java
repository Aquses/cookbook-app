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
  private TextArea myComment;

  @FXML
  private Label myDate;

  @FXML
  private Label myUsername;

  private Comment comment;

  private DisplayRecipeScene drsController;

  /**
   * a method to set the contents for the comment box
   *
   * @param comment       the comment object
   * @param parent        the anchorpane
   * @param drsController display recipe scene object
   */
  public void setData(Comment comment, AnchorPane parent, DisplayRecipeScene drsController) {
    this.drsController = drsController;
    int commentId = comment.getId();

    try {
      QueryMaker qm = new QueryMaker();
      User commentUser = qm.retrieveCommentUser(commentId);

      String username = commentUser.getFname() + " " + commentUser.getLname();
      myUsername.setText(username);

    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }

    this.comment = comment;
    this.myComment.setText(comment.getComment_text());

    // setting the format for the datetime to match the sql database datetime
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    myDate.setText(dateFormat.format(comment.getDate()));
    int userid = comment.getUser_id();

    // disables access to the comment buttons for non-authorized users
    if (userid != Session.getCurrentUser().getUserId()) {
      editButton.setVisible(false);
      deleteButton.setVisible(false);
      myComment.setDisable(true);

    }
  }

  /**
   * a method to edit an existing comment from the comment section
   *
   * @param event an fxml event that triggers when the relevant button is clicked
   * @throws SQLException
   */
  @FXML
  void editComment(ActionEvent event) throws SQLException {
    String updatedComment = myComment.getText();
    int userid = comment.getUser_id();

    if (userid == Session.getCurrentUser().getUserId()) { // only the creator of the comment can update/edit it

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

  /**
   * a method to delete an existing comment from the comment section
   *
   * @param event an fxml event that triggers when the relevant button is clicked
   */
  @FXML
  void removeComment(ActionEvent event) {
    int userid = comment.getUser_id(); // only the creator of the comment can delete it
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
    // a method to refresh the comments after they are altered.
    drsController.reloadComments();
  }

}
