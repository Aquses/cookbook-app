package cookbook.model;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.controller.Cookbook;
import cookbook.controller.DeleteUserController;
import cookbook.controller.EditUserController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button addUser;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button deleteUser;

    @FXML
    private Button editUser;

    @FXML
    private ListView<User> userList;

    private ObservableList<User> userObservableList;

    /**
     * @throws SQLException
     */
    @ FXML
    void initialize() throws SQLException {
        // set up the user list on the list View field
        QueryMaker queryMaker = new QueryMaker();
        userObservableList = queryMaker.getAllusers();
        userList.setItems(userObservableList);

        userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                editUser.setDisable(false);
                deleteUser.setDisable(false);
            } else {
                editUser.setDisable(true);
                deleteUser.setDisable(true);
            }
        });
    }

    @FXML
    void deleteUser(ActionEvent event) throws IOException, SQLException {

    // Check if a user is selected
    User selectedUser = userList.getSelectionModel().getSelectedItem();
    if (selectedUser == null) {
        return;
    }
   
    FXMLLoader loader = new FXMLLoader(Cookbook.class.getResource("DeleteScene.fxml"));
    Parent deleteSceneRoot = loader.load();
    DeleteUserController deleteController = loader.getController();

    deleteController.setUser(selectedUser);

    Scene deleteScene = new Scene(deleteSceneRoot);
    Stage deleteStage = new Stage();
    deleteStage.setScene(deleteScene);
    deleteStage.showAndWait();

    // refresh list view
    QueryMaker queryMaker = new QueryMaker();
    userObservableList = queryMaker.getAllusers();
    userList.setItems(userObservableList);
}

@FXML
void addUser(ActionEvent event) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(Cookbook.class.getResource("AddUserScene.fxml"));
    Parent addUserSceneRoot = loader.load();
    
    Scene addUserScene = new Scene(addUserSceneRoot);
    Stage addUserStage = new Stage();
    addUserStage.setScene(addUserScene);
    addUserStage.showAndWait();

    // refresh list view
    QueryMaker queryMaker = new QueryMaker();
    userObservableList = queryMaker.getAllusers();
    userList.setItems(userObservableList);
}

@FXML
void editUser(ActionEvent event) throws IOException, SQLException {
    // Check if a user is selected
    User selectedUser = userList.getSelectionModel().getSelectedItem();
    if (selectedUser == null) {
        return;
    }
   
    FXMLLoader loader = new FXMLLoader(Cookbook.class.getResource("EditUserScene.fxml"));
    Parent editUserSceneRoot = loader.load();
    EditUserController editUserController = loader.getController();

    editUserController.setUser(selectedUser);
    System.out.println(selectedUser);

    Scene editUserScene = new Scene(editUserSceneRoot);
    Stage editUserStage = new Stage();
    editUserStage.setScene(editUserScene);
    editUserStage.showAndWait();

    // refresh list view
    QueryMaker queryMaker = new QueryMaker();
    userObservableList = queryMaker.getAllusers();
    userList.setItems(userObservableList);
}
}

