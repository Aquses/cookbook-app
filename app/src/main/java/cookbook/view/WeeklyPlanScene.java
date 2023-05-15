package cookbook.view;

import java.sql.SQLException;

import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class WeeklyPlanScene {

    @FXML
    private AnchorPane ap;

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
        User currentUser = Session.getCurrentUser();
        this.user = currentUser;

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

}
