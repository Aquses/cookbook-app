package cookbook.controller;

import cookbook.Cookbook;
import cookbook.model.Session;
import cookbook.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class implements the navigation bar used on all scenes where the user is logged in.
 */
public class MainNavigation implements Initializable {

  @FXML
  private Button MenuClosed;
  @FXML
  private Button MenuOpen;
  @FXML
  private HBox MenuSlider;
  @FXML
  private Button RecipesButton;
  @FXML
  private Button AdminButton;
  @FXML
  private Pane darkenPane;
  @FXML
  private Button HomeButton;

  @FXML
  private Button MessagesButton;

  @FXML
  private Button HelpButton;

  @FXML
  private AnchorPane ContentAnchor;

  @FXML
  private Button favouritesButton;

  @FXML
  private Button WeeklyPlanButton;

  @FXML
  private Button ShoppingList;

  @FXML
  private Button logoutButton;

  /**
   * Use this to load the scene.
   * @return Returns the FXML file attributed to this controller.
   * @throws IOException The FXML file was not found or could not be loaded.
   */
  public static Scene getScene() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("NavBar.fxml"));
    return new Scene(fxmlLoader.load(), 1280, 700);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User currentUser = Session.getCurrentUser();
    AdminButton.setVisible(currentUser.getIsAdmin());
    try {
      loadScene(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    menuControls();
  }

  /**
   * This initializes and specifies the navbar menu controls.
   */
  private void menuControls() {
    MenuSlider.setPrefWidth(50);
    darkenPane.setVisible(false);

    MenuClosed.setOnMouseClicked(event -> {
      MenuSlider.setPrefWidth(143);

      MenuOpen.setVisible(true);
      MenuClosed.setVisible(false);
      darkenPane.setVisible(true);
    });

    MenuOpen.setOnMouseClicked(event -> {
      MenuSlider.setPrefWidth(50);

      MenuOpen.setVisible(false);
      MenuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    darkenPane.setOnMouseClicked(event -> {
      MenuSlider.setPrefWidth(50);

      MenuOpen.setVisible(false);
      MenuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    HomeButton.setOnMouseClicked(event -> {
      MenuSlider.setPrefWidth(50);

      MenuOpen.setVisible(false);
      MenuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    RecipesButton.setOnMouseClicked(event -> {
      try {
        loadScene(1);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    HomeButton.setOnMouseClicked(event -> {
      try {
        loadScene(0);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    AdminButton.setOnMouseClicked(event -> {
      try {
        loadScene(2);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    favouritesButton.setOnMouseClicked(event -> {
      try {
        loadScene(3);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    MessagesButton.setOnMouseClicked(event -> {
      try {
        loadScene(4);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    HelpButton.setOnMouseClicked(event -> {
      try {
        loadScene(5);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    WeeklyPlanButton.setOnMouseClicked(event -> {
      try {
        loadScene(6);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    ShoppingList.setOnMouseClicked(event -> {
      try {
        loadScene(7);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    logoutButton.setOnMouseClicked(event -> {
      Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
      Parent root;
      URL url;
      try {
        if ((url = getClass().getResource("/cookbook/LoginScreenScene.fxml")) != null) {
          root = FXMLLoader.load(url);
        } else {
          throw new RuntimeException();
        }
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Dish IT");
        primaryStage.setWidth(660);
        primaryStage.setHeight(540);
        primaryStage.show();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   * This function loads the scenes into this FXML file's Anchor Pane.
   * @param sceneId Specify the scene you are loading. <br>
   *                0: Load hub scene. <br>
   *                1: Load recipes scene. <br>
   *                2: Load admin scene. <br>
   *                3: Load favorites scene. <br>
   *                4: Load messages scene. <br>
   *                5: Load help scene. <br>
   *                6: Load weekly plan scene. <br>
   *                7: Load shopping list scene. <br>
   *                
   * @throws IOException The provided sceneId is not valid.
   */
  private void loadScene(int sceneId) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    Node n;

    switch (sceneId) {
      case 0 -> fxmlLoader.setLocation(Cookbook.class.getResource("HubScene.fxml"));
      case 1 -> fxmlLoader.setLocation(Cookbook.class.getResource("RecipesScene.fxml"));
      case 2 -> fxmlLoader.setLocation(Cookbook.class.getResource("AdminScene.fxml"));
      case 3 -> fxmlLoader.setLocation(Cookbook.class.getResource("FavouritesScene.fxml"));
      case 4 -> fxmlLoader.setLocation(Cookbook.class.getResource("MessagesScene.fxml"));
      case 5 -> fxmlLoader.setLocation(Cookbook.class.getResource("HelpScene.fxml"));
      case 6 -> fxmlLoader.setLocation(Cookbook.class.getResource("WeeklyPlanScene.fxml"));
      case 7 -> fxmlLoader.setLocation(Cookbook.class.getResource("ShoppingListScene.fxml"));
      default -> {
        throw new IOException("The provided scene ID to load does not exist.");
      }
    }

    n = fxmlLoader.load();
    AnchorPane.setTopAnchor(n, 0.0);
    AnchorPane.setRightAnchor(n, 0.0);
    AnchorPane.setBottomAnchor(n, 0.0);
    AnchorPane.setLeftAnchor(n, 0.0);

    ContentAnchor.getChildren().clear();
    ContentAnchor.getChildren().add(n);

    switch (sceneId) {
      case 7 -> {
        ShoppingListController controller = fxmlLoader.getController();
        controller.setController(controller);
      }
    }
  }
}
