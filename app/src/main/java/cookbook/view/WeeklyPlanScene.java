package cookbook.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class WeeklyPlanScene {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private Button createPlan;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField weekName;

    @FXML
    private TextField weekNumber;

    @FXML
    private TableView<WeeklyDinnerList> weeklyPlanTable;
    
    @FXML
    private TableColumn<WeeklyDinnerList, String> weekNameCol;

    @FXML
    private TableColumn<WeeklyDinnerList, Integer> weekNumberCol;

    private ObservableList<WeeklyDinnerList> weeklyList = FXCollections.observableArrayList();

    private User user;
    

    @FXML
    public void initialize() {
        this.user = Session.getCurrentUser();
        nameLabel.setVisible(false);
        numberLabel.setVisible(false);
        weekNumber.setVisible(false);   
        weekName.setVisible(false);
        cancelButton.setVisible(false);
        createButton.setVisible(false);

        loadTable();
        loadWeeklyPlans();

        // Below is testing to see that the weekly plan contains the recipes for each day

        for (WeeklyDinnerList wdl : weeklyList) {
            System.out.println("Week Id: " + wdl.getWeekId() + "WeekName: " + wdl.getWeekName() + "User id: " + wdl.getUserId() + "Week number: " + wdl.getWeekNumber());
            for (ObservableList<Recipe> recipeList : wdl.getWeeklyPlan()) {
                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.getName());
                }
            }
        }
    }

    public void loadTable() {
        weekNumberCol.setCellValueFactory(new PropertyValueFactory<>("weekNumber"));
        weekNameCol.setCellValueFactory(new PropertyValueFactory<>("weekName"));    
    }

    public void loadWeeklyPlans() {
        weeklyList.clear();

        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<WeeklyDinnerList> databaseWeeklyPlans = qm.retrieveWeeklyListObjects(user);
            
            for (WeeklyDinnerList week : databaseWeeklyPlans) {
                weeklyList.add(week);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        weeklyPlanTable.setItems(weeklyList);
    }

    @FXML
    void createPlan(ActionEvent event) {
			nameLabel.setVisible(true);
			numberLabel.setVisible(true);
			weekNumber.setVisible(true);   
			weekName.setVisible(true);
			cancelButton.setVisible(true);
			createButton.setVisible(true);
    }

    @FXML
    void create(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
			weekNumber.clear();
			weekName.clear();
			nameLabel.setVisible(false);
			numberLabel.setVisible(false);
			weekNumber.setVisible(false);   
			weekName.setVisible(false);
			cancelButton.setVisible(false);
			createButton.setVisible(false);
    }
}