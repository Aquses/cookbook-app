package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Controller {

  @FXML
  private Label recipeHeader;

  @FXML
  private ListView<String> recipeList;

  @FXML
  void initialize() {
    assert recipeHeader != null : "fx:id=\"recipeHeader\" was not injected: check your FXML file 'browserecipe.fxml'.";
    assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'browserecipe.fxml'.";
    addRecipe();
    selectRecipe();
  }

  @FXML
  private TextArea descriptionArea;

  @FXML
  private void addRecipe() {

      try {
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=admin&password=cookbook123&useSSL=false");

          String query = "select recipe_name from recipes";
          Statement statement = conn.createStatement();
          ResultSet rs = statement.executeQuery(query);

          while (rs.next()) {
              String recipeName = rs.getString(1);
              recipeList.getItems().add(recipeName);
              System.out.print(rs.getString((1)));
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
