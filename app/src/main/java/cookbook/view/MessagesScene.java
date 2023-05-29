package cookbook.view;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.controller.MessageController;
import cookbook.model.Message;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * {@summary} Class responsible for managing and displaying messages sent to logged-in user. 
 * Retrieves messages along with the associated recipes, and populates the interface with them.
 */
public class MessagesScene {

  @FXML
  private AnchorPane ap;

  @FXML
  private GridPane grid;

  User user;

  /**
   * {@summary} Initializes the ui and functionality by retrieving and loading the messages
   *  and associated recipes.
   */
  @FXML
  public void initialize() {
    User currentUser = Session.getCurrentUser();
    this.user = currentUser;
    
    retrieveMessages();
    retrieveRecipes();
    loadMessages();
  }

  ObservableList<Message> messageList =  FXCollections.observableArrayList();
  ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

  /**
   * {@summary} Retrieves associated recipes to messages that have been sent to the user.
   */
  public void retrieveRecipes() {
    try {
      QueryMaker qm = new QueryMaker();
      ObservableList<Recipe> databaseRecipes = qm.retrieveMessageRecipes(user);
      this.recipeList = databaseRecipes;
        
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * {@summary} Retrieve messages that have been sent to the user.
   */
  public void retrieveMessages() {
    try {
      QueryMaker qm = new QueryMaker();
      ObservableList<Message> databaseMessages = qm.retrieveMessages(user);
      this.messageList = databaseMessages;

    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }

  }

  /**
   * {@summary} Loads the message item fxml containing message content and clickable attached recipe
   * to a grid pane.
   */
  private void loadMessages() {
    int row = 1;
    int col = 0;

    for (int i = 0; i < messageList.size(); i++) {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/cookbook/MsgItem.fxml"));
      AnchorPane anchorPane = null;
      try {
        anchorPane = fxmlLoader.load();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      MessageController mc = fxmlLoader.getController();
      
      mc.setMessage(messageList.get(i), ap);
      // mc.setData(allRecipes.get(i), ap);
      mc.setRecipe(recipeList.get(i));
      mc.setSenderName(messageList.get(i));

      if (col == 1) {
        col = 0;
        row++;
      }

      grid.add(anchorPane, col++, row);
      grid.setMinWidth(Region.USE_COMPUTED_SIZE);
      grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
      grid.setMaxWidth(Region.USE_PREF_SIZE);

      grid.setMinHeight(Region.USE_COMPUTED_SIZE);
      grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
      grid.setMaxHeight(Region.USE_PREF_SIZE);
    }
  }  
}
