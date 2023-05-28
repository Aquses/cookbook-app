package cookbook.model;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class AddCustomTag implements Initializable {

    @FXML private Text tagText;

    @FXML private Label recipeLabel;

    @FXML private TextField cTagField;

    @FXML private Button addButton;

    @FXML private Button confirmButton;

    @FXML private TableView<Tags> cTagView;

    @FXML private TableColumn<Tags, String> cTagColumn;

    private Recipe recipe;

    private int recipe_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();

        confirmButton.setOnAction(event -> {
            addCustomTagsToRecipe();
            cTagView.getItems().clear();
        });

        addButton.setOnAction(event -> {
            Tags tag = new Tags(cTagField.getText());
            ObservableList<Tags> tags = cTagView.getItems();
            tags.add(tag);
            cTagView.setItems(tags);
            cTagField.clear();
        });
    }

    @FXML
    public void initialization(Recipe recipe) {
        this.recipe = recipe;
        this.recipe_id = recipe.getId();
    }

    public void addCustomTagsToRecipe() {
        User user = Session.getCurrentUser();
        int userId = user.getUserId();
        int recipe_id = recipe.getId();
    
        ObservableList<Tags> tags = cTagView.getItems();
        List<Integer> cTagList = new ArrayList<>();
    
        try (Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=123456&useSSL=false")) {
            String insQuery = "INSERT INTO custom_tags (user_id, ctag_name) VALUES (?, ?)";
            String selectQuery = "SELECT LAST_INSERT_ID()";
            String insTagQuery = "INSERT INTO recipe_ctags (recipe_id, ctag_id) VALUES (?, ?)";
    
            PreparedStatement insStmt = conn2.prepareStatement(insQuery);
            Statement selectStmt = conn2.createStatement();
            PreparedStatement insTagStmt = conn2.prepareStatement(insTagQuery);
    
            for (Tags tag : tags) {
                insStmt.setInt(1, userId);
                insStmt.setString(2, tag.getName());
                insStmt.executeUpdate();
            }
            ResultSet resultSet = selectStmt.executeQuery(selectQuery);
            while (resultSet.next()) {
                int ctagId = resultSet.getInt(1);
                cTagList.add(ctagId);
            }
            for (int ctag_id : cTagList) {
                insTagStmt.setInt(1, recipe_id);
                insTagStmt.setInt(2, ctag_id);
                insTagStmt.executeUpdate();
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    public void loadData() {
        cTagColumn.setCellValueFactory(new PropertyValueFactory<Tags, String>("Name"));
    }
    
}
