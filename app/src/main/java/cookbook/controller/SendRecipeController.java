package cookbook.controller;

import java.sql.SQLException;

import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SendRecipeController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<User> reciever;

    @FXML
    private TextArea textArea;

    @FXML
    private Button sendButton;

    private Recipe recipe;
    private User sender;
    private ObservableList<User> userObservableList;

    public void initialize() throws SQLException {
      QueryMaker queryMaker;
      queryMaker = new QueryMaker();
      userObservableList = queryMaker.getAllusers();
  
      reciever.setItems(userObservableList);
      reciever.setConverter(new StringConverter<User>() {
          @Override
          public String toString(User user) {
            return user != null && user.getUsername() != null ? user.getUsername() : "";
          }
  
          @Override
          public User fromString(String string) {
              return null; 
          }
      }); 
    }

    public void setRecipe(Recipe selectedRecipe) {
      recipe = selectedRecipe;
    }

    public void setSender(User selectedUser) {
      sender = selectedUser;
    }

    @FXML
    void cancel(ActionEvent event) {
      Stage stage = (Stage) cancelButton.getScene().getWindow();
      stage.close();
    }

    @FXML
    void send(ActionEvent event) {

    }



}
