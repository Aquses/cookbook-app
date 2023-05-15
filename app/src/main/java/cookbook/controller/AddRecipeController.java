package cookbook.controller;

// AddRecipe Controller made by Eldaras

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cookbook.model.IngredientsAddRecipe;
import cookbook.model.Session;
import cookbook.model.Ingredient;

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

    @FXML private TextArea insField;

    @FXML private TextField tagsField;

    @FXML private Button insButton;

    @FXML private Button tagsButton;

    @FXML private Label ingLabel;

    @FXML private TextField ingField;

    @FXML private Label measurementLabel;


    @FXML private ChoiceBox<String> measurementField;
    // load from database or custom array?
    private String[] measurements = {"kg", "g", "l", "ml", "tbsp", "tsp", "cup", "cups", "cloves", "large", "head"};

    @FXML private Label quantityLabel;

    @FXML private TextField quantityField;

    @FXML private TableView<IngredientsAddRecipe> tableView;

    @FXML private TableColumn<IngredientsAddRecipe, String> ingColumn;

    @FXML private TableView<Ingredient> tableView1;

    @FXML private TableColumn<IngredientsAddRecipe, Integer> quantityColumn;

    @FXML private TableColumn<IngredientsAddRecipe, String> measurementColumn;

    @FXML private Button submitButton;

    @FXML private Button removeButton;

    @FXML private GridPane grid;

    @FXML private ListView<String> tagList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      loadData();
      String[] tags = loadListView();
      tagList.setItems(FXCollections.observableArrayList(tags));
      measurementField.getItems().addAll(measurements);

      addRecipeButton.setOnAction(event -> {
        // Get values from text fields
        String recipeName = nameField.getText();
        String recipeDesc = descField.getText();
        String recipeInstructions = insField.getText();
        String recipeTags = tagsField.getText();
        int servings = Integer.parseInt(servingsField.getText());
        int prepTime = Integer.parseInt(prepField.getText());
        int cookTime = Integer.parseInt(cookField.getText());
        int user_id = Session.getCurrentUser().getUserId();
      
        if (recipeName.trim().isEmpty() != true) {

        try {
          Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false");

          String query = "INSERT INTO recipes (recipe_name, recipe_description, recipe_instructions, servings, prep_time_minutes, cook_time_minutes, user_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
          PreparedStatement pstmt = conn2.prepareStatement(query);
          pstmt.setString(1, recipeName);
          pstmt.setString(2, recipeDesc);
          pstmt.setString(3, recipeInstructions);
          pstmt.setInt(4, servings);
          pstmt.setInt(5, prepTime);
          pstmt.setInt(6, cookTime);
          pstmt.setInt(7, user_id);
          
          pstmt.executeUpdate();
                
          Statement stmt = conn2.createStatement();
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
                stmt.executeUpdate("INSERT INTO custom_tags (user_id, ctag_name) " +
                                   "VALUES (" + user_id + ", '" + tagName + "')");
                ResultSet rsCtag = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                rsCtag.next();
                int ctagId = rsCtag.getInt(1);
                stmt.executeUpdate("INSERT INTO recipe_ctags (recipe_id, ctag_id) " +
                                   "VALUES ('" + recipeId + "', " + ctagId + ")");
              }
            }
          }
          
          ObservableList<IngredientsAddRecipe> ingredients = tableView.getItems();
          for (IngredientsAddRecipe ingredient : ingredients) {
            String ingName = ingredient.getName();
            int quantity = ingredient.getQuantity();
            String measurement = ingredient.getMeasurement();
            String ingredientQuery = "INSERT INTO ingredients (i_name, recipe_id, qty, measurement) " +
                                     "VALUES ('" + ingName + "', " + recipeId + ", " + quantity + ", '" + measurement + "')";
            stmt.executeUpdate(ingredientQuery);
          }
          tableView.getItems().clear();
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
        prepField.clear();
        cookField.clear();
        quantityField.clear();
      }});
      

      tagList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      tagList.setOnMouseClicked(event -> {
        ObservableList<String> selectedItems = tagList.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty()) {
          tagsField.setText(String.join(", ", selectedItems));
        }
      });

      submitButton.setOnAction(event -> {
        IngredientsAddRecipe ingredient = new IngredientsAddRecipe(ingField.getText(),
                                              Integer.parseInt(quantityField.getText()),
                                              measurementField.getValue());
        ObservableList<IngredientsAddRecipe> ingredients = tableView.getItems();
        ingredients.add(ingredient);
        tableView.setItems(ingredients);
      });

      removeButton.setOnAction(event -> {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedID);
      });
    }

    public void loadData() {
      ingColumn.setCellValueFactory(new PropertyValueFactory<IngredientsAddRecipe, String>("name"));
      quantityColumn.setCellValueFactory(new PropertyValueFactory<IngredientsAddRecipe, Integer>("quantity"));
      measurementColumn.setCellValueFactory(new PropertyValueFactory<IngredientsAddRecipe, String>("measurement"));
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
