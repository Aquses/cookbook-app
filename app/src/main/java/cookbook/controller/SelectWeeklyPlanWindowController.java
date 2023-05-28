package cookbook.controller;

import cookbook.model.QueryMaker;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Gustavo edited this page.

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

    private WeeklyDinnerList selectedPlan;

    private ShoppingListController callerController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Session.getCurrentUser();
        loadTable();
        loadWeeklyPlans(user);  
        //Stage stage = (Stage) SelectButton.getScene().getWindow();

        SelectButton.setOnMouseClicked(event -> {
            if(selectedPlan != null){
                callerController.setSelectedPlan(selectedPlan);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else{
                SelectButton.setDisable(true);
            }
        });

        CancelButton.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
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

    @FXML
    private void weeklyPlanClicked(){
        //first, make select button clickable (enable)
        if(SelectButton.isDisabled()){
            SelectButton.setDisable(false);
        }
        //then set the variable that will retain the values from the click
        selectedPlan = weeklyPlanTable.getSelectionModel().getSelectedItem();
        //submission is handled in initialization
    }

    public void setCallerController(ShoppingListController controller){
        callerController = controller;
    }
}
