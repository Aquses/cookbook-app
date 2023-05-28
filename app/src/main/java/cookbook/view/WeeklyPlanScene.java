package cookbook.view;

import cookbook.controller.DailyRecipeController;
import cookbook.model.QueryMaker;
import cookbook.model.Recipe;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.model.WeeklyDinnerList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;


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
  private Label error;

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
  private Label youSure;

  @FXML
  private Button deleteSelection;

  @FXML
  private Button noDelete;

  @FXML
  private Button yesDelete;


  @FXML
  private GridPane mondayGrid;

  @FXML
  private GridPane tuesdayGrid;

  @FXML
  private GridPane wednesdayGrid;

  @FXML
  private GridPane thursdayGrid;

  @FXML
  private GridPane fridayGrid;

  @FXML
  private GridPane saturdayGrid;

  @FXML
  private GridPane sundayGrid;



  @FXML
  public void initialize() {
    this.user = Session.getCurrentUser();
    nameLabel.setVisible(false);
    numberLabel.setVisible(false);
    weekNumber.setVisible(false);
    weekName.setVisible(false);
    cancelButton.setVisible(false);
    createButton.setVisible(false);
    error.setVisible(false);
    youSure.setVisible(false);
    yesDelete.setVisible(false);
    noDelete.setVisible(false);



    loadTable();
    loadWeeklyPlans();


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
    int weekNum = Integer.parseInt(weekNumber.getText());

    if (weekNum >= 1 && weekNum <= 52) {
      try {
        QueryMaker queryMaker = new QueryMaker();
        queryMaker.insertWeeklyPlan(weekName.getText(), weekNum, user.getUserId() );
      } catch (SQLException e) {
        e.printStackTrace();
      }
      loadTable();
      loadWeeklyPlans();
      cancel(event);
    } else {
      error.setVisible(true);
    }
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
    error.setVisible(false);
  }

  @FXML
  void openDelete(ActionEvent event) {
    WeeklyDinnerList selectedWeeklyList = weeklyPlanTable.getSelectionModel().getSelectedItem();

    if (selectedWeeklyList != null) {
      youSure.setVisible(true);
      yesDelete.setVisible(true);
      noDelete.setVisible(true);
    }
  }

  @FXML
  void delete(ActionEvent event) {
    WeeklyDinnerList selectedWeeklyList = weeklyPlanTable.getSelectionModel().getSelectedItem();
    if (selectedWeeklyList != null) {
      int weekId = selectedWeeklyList.getWeekId();

      try {
        QueryMaker queryMaker = new QueryMaker();


        if (queryMaker.isShoppingList(weekId, user.getUserId())) {
          queryMaker.deleteShoppingList(weekId, user.getUserId());
        }
        queryMaker.deleteWeeklyPlan(weekId);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      // Refresh the table of weekly lists
      weeklyPlanTable.getItems().remove(selectedWeeklyList);
      weeklyPlanTable.refresh();

      deleteCancel(event);
    }
  }

  @FXML
  void deleteCancel(ActionEvent event) {
    youSure.setVisible(false);
    yesDelete.setVisible(false);
    noDelete.setVisible(false);
  }

  @FXML
  private void weeklyPlanClicked(MouseEvent event) {
    mondayGrid.getChildren().clear();
    tuesdayGrid.getChildren().clear();
    wednesdayGrid.getChildren().clear();
    thursdayGrid.getChildren().clear();
    fridayGrid.getChildren().clear();
    saturdayGrid.getChildren().clear();
    sundayGrid.getChildren().clear();


    WeeklyDinnerList selectedPlan = weeklyPlanTable.getSelectionModel().getSelectedItem();
    ObservableList<ObservableList<Recipe>> weeklyRecipes = selectedPlan.getWeeklyPlan();
    ObservableList<Recipe> mondayRecipes = weeklyRecipes.get(0);

    loadDailyRecipes(mondayRecipes, mondayGrid);

    ObservableList<Recipe> tuesdayRecipes = weeklyRecipes.get(1);
    loadDailyRecipes(tuesdayRecipes, tuesdayGrid);

    ObservableList<Recipe> wednesdayRecipes = weeklyRecipes.get(2);
    loadDailyRecipes(wednesdayRecipes, wednesdayGrid);

    ObservableList<Recipe> thursdayRecipes = weeklyRecipes.get(3);
    loadDailyRecipes(thursdayRecipes, thursdayGrid);

    ObservableList<Recipe> fridayRecipes = weeklyRecipes.get(4);
    loadDailyRecipes(fridayRecipes, fridayGrid);

    ObservableList<Recipe> saturadayRecipes = weeklyRecipes.get(5);
    loadDailyRecipes(saturadayRecipes, saturdayGrid);

    ObservableList<Recipe> sundayRecipes = weeklyRecipes.get(6);
    loadDailyRecipes(sundayRecipes, sundayGrid);

  }

  public void loadDailyRecipes(ObservableList<Recipe> dailyRecipeList, GridPane grid) {


    int row = 0, col = 0;

    for (int i=0; i < dailyRecipeList.size(); i++) {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/cookbook/DailyRecipeItem.fxml"));
      // fxmlLoader.setLocation(getClass().getResource("/cookbook/RecipeItem.fxml"));
      AnchorPane anchorPane = null;
      try {
        anchorPane = fxmlLoader.load();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      DailyRecipeController drc = fxmlLoader.getController();
      drc.setRecipe(dailyRecipeList.get(i), ap);

      grid.add(anchorPane, col++, row);
      grid.setMinWidth(Region.USE_COMPUTED_SIZE);
      grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
      grid.setMaxWidth(Region.USE_PREF_SIZE);

      grid.setMinHeight(Region.USE_COMPUTED_SIZE);
      grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
      grid.setMaxHeight(Region.USE_PREF_SIZE);
    }
  }

}