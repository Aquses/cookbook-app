package cookbook.controller;

import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SelectWeeklyPlanWindowController implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private Button SelectButton;

    @FXML
    private TableColumn<WeeklyDinnerList, String> weekNameCol;

    @FXML
    private TableColumn<WeeklyDinnerList, Integer> weekNumberCol;

    @FXML
    private TableView<WeeklyDinnerList> weeklyPlanTable;

    private ObservableList<WeeklyDinnerList> weeklyList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Session.getCurrentUser();

        loadTable();
        loadWeeklyPlans(user);
    }

    public void loadWeeklyPlans(User user) {
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

    public void loadTable() {
        weekNumberCol.setCellValueFactory(new PropertyValueFactory<>("weekNumber"));
        weekNameCol.setCellValueFactory(new PropertyValueFactory<>("weekName"));
    }
}
