package cookbook.view;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.controller.MessageController;
import cookbook.model.Message;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MessagesScene {

    @FXML
    private AnchorPane ap;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        retrieveMessages();
        retrieveRecipes();
        loadMessages();
    }

    ObservableList<Message> messageList =  FXCollections.observableArrayList();
    ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

    public void retrieveRecipes() {

        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Recipe> databaseRecipes = qm.retrieveMessageRecipes(2);
            this.recipeList = databaseRecipes;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void retrieveMessages() {
        
        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Message> databaseMessages = qm.retrieveMessages(2);
            this.messageList = databaseMessages;

            // for (Message msg : databaseMessages) {
            //     messageList.add(msg);
            // }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void loadMessages(){
        int row = 1, col = 0;

        for(int i=0; i < messageList.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cookbook/MsgItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MessageController mc = fxmlLoader.getController();
            mc.setMessage(messageList.get(i));
            // mc.setData(allRecipes.get(i), ap);
            mc.setRecipe(recipeList.get(i));

            if(col == 1){
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

            //GridPane.setMargin(anchorPane, new Insets(0,10,0,10));
        }
    }

    // private void loadRecipes(ObservableList<Recipe> allRecipes){
    //     int row = 1, col = 0;

    //     for(int i=0; i<allRecipes.size(); i++){
    //         FXMLLoader fxmlLoader = new FXMLLoader();
    //         fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
    //         AnchorPane anchorPane = null;
    //         try {
    //             anchorPane = fxmlLoader.load();
    //         } catch (IOException e) {
    //             throw new RuntimeException(e);
    //         }

    //         MessageController mc = fxmlLoader.getController();
    //         itemController.setData(allRecipes.get(i), ap);

    //         if(col == 4){
    //             col = 0;
    //             row++;
    //         }

    //         grid.add(anchorPane, col++, row);
    //         grid.setMinWidth(Region.USE_COMPUTED_SIZE);
    //         grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
    //         grid.setMaxWidth(Region.USE_PREF_SIZE);

    //         grid.setMinHeight(Region.USE_COMPUTED_SIZE);
    //         grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
    //         grid.setMaxHeight(Region.USE_PREF_SIZE);

    //         //GridPane.setMargin(anchorPane, new Insets(0,10,0,10));
    //     }
    // }



    

}
