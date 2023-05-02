package cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRecipeController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField portionSize;

    @FXML
    private Label portionLabel;

    @FXML
    private Label descLabel;

    @FXML
    private TextField prepField;

    @FXML
    private TextField cookField;

    @FXML
    private Label prepLabel;

    @FXML
    private Label cookLabel;

    @FXML
    private TextField servingsField;

    @FXML
    private Label servingsLabel;

    @FXML
    private TextArea descField;

    @FXML
    private Button addRecipeButton;

    @FXML
    private Label insLabel;

    @FXML
    private Label tagsLabel;

    @FXML
    private TextField insField;

    @FXML
    private TextField tagsField;

    @FXML
    private Button insButton;

    @FXML
    private Button tagsButton;

    @FXML
    private Label ingLabel;

    @FXML
    private TextField ingField;

    @FXML
    private GridPane grid;

    @FXML
    private ListView<String> tagList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      String[] tags = loadListView();
      tagList.setItems(FXCollections.observableArrayList(tags));

      addRecipeButton.setOnAction(event -> {
        // Get values from text fields
        String recipeName = nameField.getText();
        String recipeDesc = descField.getText();
        String recipeInstructions = insField.getText();
        String recipeIngredients = ingField.getText();
        String recipeTags = tagsField.getText();
        int servings = Integer.parseInt(servingsField.getText());
        int prepTime = Integer.parseInt(prepField.getText());
        int cookTime = Integer.parseInt(cookField.getText());
    
        try {
          Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
          Statement stmt = conn2.createStatement();
            
          String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes) " +
                          "VALUES ('" + recipeName + "', '" + recipeDesc + "', '" + recipeInstructions + "', " +
                          servings + ", " + prepTime + ", " + cookTime + ")";
          stmt.executeUpdate(query);
            
          ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
          rs.next();
          int recipeId = rs.getInt(1);
    
          String[] tagList = recipeTags.split(",");
          for (String tagName : tagList) {
            tagName = tagName.trim();
            if (tagName.length() > 0) {
              ResultSet rsTag = stmt.executeQuery("SELECT tag_id FROM tags WHERE tag_name = '" + tagName + "'");
              if (rsTag.next()) {
                int tagId = rsTag.getInt(1);
                stmt.executeUpdate("INSERT INTO recipe_tags (recipe_id, tag_id) " +
                                  "VALUES ('" + recipeId + "', " + tagId + ")");
              } 
              else {
                stmt.executeUpdate("INSERT INTO tags (tag_name) " +
                                  "VALUES ('" + tagName + "')");
                rsTag = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                rsTag.next();
                int tagId = rsTag.getInt(1);
                stmt.executeUpdate("INSERT INTO recipe_tags (recipe_id, tag_id) " +
                                  "VALUES ('" + recipeId + "', " + tagId + ")");
              }
            }
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    
        // Clear text fields
        nameField.clear();
        descField.clear();
        insField.clear();
        ingField.clear();
        tagsField.clear();
        servingsField.clear();
        portionSize.clear();
        prepField.clear();
        cookField.clear();
      });
    
    

      insButton.setOnAction(event -> {
        int row = 3, col = 2;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("test.fxml"));
        AnchorPane anchorPane = null;

        try { 
          anchorPane = fxmlLoader.load();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        grid.add(anchorPane, col++, row);
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);
      });

      tagList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      tagList.setOnMouseClicked(event -> {
          ObservableList<String> selectedItems = tagList.getSelectionModel().getSelectedItems();
          if (!selectedItems.isEmpty()) {
            tagsField.setText(String.join(", ", selectedItems));
          }
      });
      
    // tagsButton.setOnAction(event -> {
    //   int row = 5, col = 4;
    //   FXMLLoader fxmlLoader = new FXMLLoader();
    //   fxmlLoader.setLocation(getClass().getResource("RecipeItem.fxml"));

    //   AnchorPane anchorPane = null;

    //   try { 
    //     anchorPane = fxmlLoader.load();
    //   } catch (IOException e) {
    //     throw new RuntimeException(e);
    //   }

    //   grid.add(anchorPane, col, row++);
    //   grid.setMinWidth(Region.USE_COMPUTED_SIZE);
    //   grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
    //   grid.setMaxWidth(Region.USE_PREF_SIZE);

    //   grid.setMinHeight(Region.USE_COMPUTED_SIZE);
    //   grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
    //   grid.setMaxHeight(Region.USE_PREF_SIZE);
    // });
  }

  public String[] loadListView() {

    Connection conn = null;
    Statement stmt = null;

    try {
      Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
      stmt = conn2.createStatement();
      String query = "SELECT * FROM tags";
      ResultSet rs = stmt.executeQuery(query);

      ArrayList<String> resultList = new ArrayList<>();
      while (rs.next()) {
        String resultString = rs.getString("tag_name");
        resultList.add(resultString);
      }

      String[] resultArray = resultList.toArray(new String[resultList.size()]);

      rs.close();
      stmt.close();
      conn2.close();

      return resultArray;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    } 
    finally {
      try {
        if (stmt != null) stmt.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
      try {
        if (conn != null) conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
  }
}
