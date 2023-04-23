package cookbook;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Search {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button backButton;

  @FXML
  private TextArea descriptionArea;

  @FXML
  private Label descriptionHeader;

  @FXML
  private Label recipeHeader;

  @FXML
  private ListView<String> recipeList;

  @FXML
  private TextField searchBar;

  @FXML
  private Button searchButton;

  @FXML
  private Button viewRecipe;

  @FXML
  void initialize() {
    assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert descriptionArea != null : "fx:id=\"descriptionArea\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert descriptionHeader != null : "fx:id=\"descriptionHeader\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert recipeHeader != null : "fx:id=\"recipeHeader\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'searchpage.fxml'.";
    assert viewRecipe != null : "fx:id=\"viewRecipe\" was not injected: check your FXML file 'searchpage.fxml'.";
    selectRecipe();
  }


  @FXML
  private void retrieveResults(ActionEvent event) {
    
    try {
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=admin&password=cookbook123&useSSL=false");
      recipeList.getItems().clear();
      String searchedWord = "%" + searchBar.getText() + "%";
      String query = "select distinct recipe_name "
                    + "from recipes as r "
                    + "join r_ingredients as ri on ri.recipe_id = r.recipe_id "
                    + "join ingredients as i on i.ingredient_id = ri.ingredient_id "
                    + "where r.recipe_name like ? or i.i_name like ?";

      PreparedStatement statement = conn.prepareStatement(query);
      statement.setString(1, searchedWord);
      statement.setString(2, searchedWord);
      ResultSet rs = statement.executeQuery();

      
      while (rs.next()) {
        String searchResult = rs.getString(1);
        recipeList.getItems().add(searchResult);
        System.out.println(searchResult);
        System.out.print(rs.getString((1)));
      }
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

          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=admin&password=cookbook123&useSSL=false");

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


}
