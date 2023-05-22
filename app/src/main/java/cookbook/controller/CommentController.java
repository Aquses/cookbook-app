package cookbook.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import cookbook.model.Comment;
import cookbook.model.QueryMaker;
import cookbook.model.Session;
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

  private AnchorPane parentAnchorPane;

  private DisplayRecipeScene drsController;

  public void setData(Comment comment, AnchorPane parent, DisplayRecipeScene drsController) {
    this.drsController = drsController;
    String username = myusername.getText();
    this.comment = comment;
    parentAnchorPane = parent;
    this.mycomment.setText(comment.getComment_text());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    myDate.setText(dateFormat.format(comment.getDate()));

    if(comment.getUser_id() == Session.getCurrentUser().getUserId()){
      deleteButton.setVisible(true);
      editButton.setVisible(true);
    } else{
      deleteButton.setVisible(false);
      editButton.setVisible(false);
    }
  }

  @FXML
  void editComment(ActionEvent event) throws SQLException {
    int userid = comment.getUser_id();

    // Setting the edited comments text.
    this.comment.setComment_text(mycomment.getText());

    QueryMaker qm = new QueryMaker();
    qm.editComment(comment);
    drsController.reloadComments();

  }

  @FXML
  void removeComment(ActionEvent event) {
    try {
      QueryMaker qm = new QueryMaker();
      qm.deleteComment(comment);
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
    drsController.reloadComments();
  }

}
