package cookbook;

// AddRecipe Controller made by Eldaras

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRecipeController implements Initializable {

    @FXML private TextField nameField;

    @FXML private Label nameLabel;

    @FXML private Label createLabel;

    @FXML private TextField portionSize;

    @FXML private Label portionLabel;

    @FXML private Label descLabel;

    @FXML private TextField prepField;

    @FXML private TextField cookField;

    @FXML private Label prepLabel;

    @FXML private Label cookLabel;

    @FXML private TextField servingsField;

    @FXML private Label servingsLabel;

    @FXML private TextArea descField;

    @FXML private Button addRecipeButton;

    @FXML private Label insLabel;

    @FXML private Label tagsLabel;

    @FXML private TextField insField;

    @FXML private TextField tagsField;

    @FXML private Button insButton;

    @FXML private Button tagsButton;

    @FXML private Label ingLabel;

    @FXML private TextField ingField;

    @FXML private Label measurementLabel;

    @FXML private TextField measurementField;

    @FXML private Label quantityLabel;

    @FXML private TextField quantityField;

    @FXML private TableView<Ingredient> tableView;

    @FXML private TableColumn<Ingredient, String> ingColumn;

    @FXML private TableColumn<Ingredient, Integer> quantityColumn;

    @FXML private TableColumn<Ingredient, String> measurementColumn;

    @FXML private Button submitButton;

    @FXML private Button removeButton;

    @FXML private GridPane grid;

    @FXML private ListView<String> tagList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      loadData();
      String[] tags = loadListView();
      tagList.setItems(FXCollections.observableArrayList(tags));

      addRecipeButton.setOnAction(event -> {
        // Get values from text fields
        String recipeName = nameField.getText();
        String recipeDesc = descField.getText();
        String recipeInstructions = insField.getText();
        String recipeTags = tagsField.getText();
        int servings = Integer.parseInt(servingsField.getText());
        int prepTime = Integer.parseInt(prepField.getText());
        int cookTime = Integer.parseInt(cookField.getText());
        int user_id = 2; // we do not have transfering user_id implemented, so far like this <<<<< FIX THIS LATER, DO NOT FORGET!!!
    
        try {
          Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");
          Statement stmt = conn2.createStatement();
            
          String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes, user_id) " +
                          "VALUES ('" + recipeName + "', '" + recipeDesc + "', '" + recipeInstructions + "', " +
                          servings + ", " + prepTime + ", " + cookTime + ", " + user_id + ")";
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

          ObservableList<Ingredient> ingredients = tableView.getItems();
          for (Ingredient ingredient : ingredients) {
            String ingName = ingredient.getName();
            int quantity = ingredient.getQuantity();
            String measurement = ingredient.getMeasurement();
            String ingredientQuery = "INSERT INTO ingredients (i_name, recipe_id, qty, measurement) " +
                                    "VALUES ('" + ingName + "', " + recipeId + ", " + quantity + ", '" + measurement + "')";
            stmt.executeUpdate(ingredientQuery);
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
        quantityField.clear();
        measurementField.clear();
      });

      tagList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      tagList.setOnMouseClicked(event -> {
          ObservableList<String> selectedItems = tagList.getSelectionModel().getSelectedItems();
          if (!selectedItems.isEmpty()) {
            tagsField.setText(String.join(", ", selectedItems));
          }
      });

      submitButton.setOnAction(event -> {
        Ingredient ingredient = new Ingredient(ingField.getText(),
                                              Integer.parseInt(quantityField.getText()),
                                              measurementField.getText());
        ObservableList<Ingredient> ingredients = tableView.getItems();
        ingredients.add(ingredient);
        tableView.setItems(ingredients);
      });

      removeButton.setOnAction(event -> {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedID);
      });
    }

    public void loadData() {
      ingColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
      quantityColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));
      measurementColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("measurement"));
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
