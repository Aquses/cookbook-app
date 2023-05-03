package cookbook.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteUserController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button no;

    @FXML
    private Label ruSure;

    @FXML
    private Button yes;

    private User user;

    @ FXML
    void initialize() {}


    public void setUser(User selectedUser) {
      user = selectedUser;
      ruSure.setText("Are you sure you want to delete: " + user.getUsername());
    }

    public void yes(ActionEvent event) {
      DataQuery dq = new DataQuery();
      dq.deleteUser(user.getUserId());

      Stage stage = (Stage) yes.getScene().getWindow();
      stage.close();
    }

    public void no(ActionEvent event) {
      Stage stage = (Stage) no.getScene().getWindow();
      stage.close();
    }
}
