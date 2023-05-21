package cookbook.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


// Have not yet implemented back button and handleBackButton event - requires hub
public class Controller {

  @FXML
  private Button addRecipeButton;

  @FXML
  private Button viewRecipeButton;

  @FXML
  private Label recipeHeader;

  @FXML
  private ListView<String> recipeList;

  @FXML
  void initialize() {
    assert addRecipeButton != null : "fx:id=\"addRecipeButton\" was not injected: check your FXML file 'browserecipe.fxml'.";
    assert descriptionArea != null : "fx:id=\"descriptionArea\" was not injected: check your FXML file 'browserecipe.fxml'.";
    assert recipeHeader != null : "fx:id=\"recipeHeader\" was not injected: check your FXML file 'browserecipe.fxml'.";
    assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'browserecipe.fxml'.";
    populateRecipeList();
    selectRecipe();
  }

  @FXML
  private TextArea descriptionArea;

  @FXML
  private void populateRecipeList() {

      try {
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");

          String query = "select recipe_name from recipes";
          Statement statement = conn.createStatement();
          ResultSet rs = statement.executeQuery(query);

          while (rs.next()) {
              String recipeName = rs.getString(1);
              recipeList.getItems().add(recipeName);
          }
      
          recipeList.refresh();

      } catch (SQLException e) {
          System.out.println("Error: " + e.getMessage());
      }
                  
  }

  @FXML
  private void selectRecipe() {
    recipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        try {

          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");

          String currentRecipe = recipeList.getSelectionModel().getSelectedItem();

          String query = "select recipe_description from recipes where recipe_name = ?";
          PreparedStatement statement = conn.prepareStatement(query);
          statement.setString(1, currentRecipe);
          ResultSet rs = statement.executeQuery();

          while (rs.next()) {
            descriptionArea.setText(rs.getString(1));

          }

        } catch (SQLException e) {
          System.out.println("Error:" + e.getMessage());
        }




      }
    });
    
  }

  // Still need to add code below so that it knows which recipe is being selected
  @FXML
  private void handleViewRecipe(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("viewrecipe.fxml"));

      Stage stage = new Stage();
      stage.setTitle("Viewing Recipe");
      stage.setScene(new Scene(root));
      stage.show();
      
    } catch (Exception e) {
        e.printStackTrace();
    }


  }

  @FXML
  private void handleAddRecipe(ActionEvent event) {
    try {

      // Contains temp fxml file
      // Mohammed: change the fxml file to addRecipe fxml page when you create it
      Parent root = FXMLLoader.load(getClass().getResource("viewrecipe.fxml"));

      Stage stage = new Stage();
      stage.setTitle("Adding Recipe");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }



  

}
