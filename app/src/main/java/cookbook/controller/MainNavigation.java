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
  private Button menuClosed;
  @FXML
  private Button menuOpen;
  @FXML
  private HBox menuSlider;
  @FXML
  private Button recipesButton;
  @FXML
  private Button adminButton;
  @FXML
  private Pane darkenPane;
  @FXML
  private Button homeButton;

  @FXML
  private Button messagesButton;

  @FXML
  private Button helpButton;

  @FXML
  private AnchorPane contentAnchor;

  @FXML
  private Button favouritesButton;

  @FXML
  private Button weeklyPlanButton;

  @FXML
  private Button shoppingList;

  @FXML
  private Button logoutButton;

  /** Use this to load the scene.
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
    adminButton.setVisible(currentUser.getIsAdmin());
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
    menuSlider.setPrefWidth(50);
    darkenPane.setVisible(false);

    menuClosed.setOnMouseClicked(event -> {
      menuSlider.setPrefWidth(143);

      menuOpen.setVisible(true);
      menuClosed.setVisible(false);
      darkenPane.setVisible(true);
    });

    menuOpen.setOnMouseClicked(event -> {
      menuSlider.setPrefWidth(50);

      menuOpen.setVisible(false);
      menuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    darkenPane.setOnMouseClicked(event -> {
      menuSlider.setPrefWidth(50);

      menuOpen.setVisible(false);
      menuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    homeButton.setOnMouseClicked(event -> {
      menuSlider.setPrefWidth(50);

      menuOpen.setVisible(false);
      menuClosed.setVisible(true);
      darkenPane.setVisible(false);
    });

    recipesButton.setOnMouseClicked(event -> {
      try {
        loadScene(1);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    homeButton.setOnMouseClicked(event -> {
      try {
        loadScene(0);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    adminButton.setOnMouseClicked(event -> {
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

    messagesButton.setOnMouseClicked(event -> {
      try {
        loadScene(4);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    helpButton.setOnMouseClicked(event -> {
      try {
        loadScene(5);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    weeklyPlanButton.setOnMouseClicked(event -> {
      try {
        loadScene(6);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    shoppingList.setOnMouseClicked(event -> {
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
      case 0:
        fxmlLoader.setLocation(Cookbook.class.getResource("HubScene.fxml"));
        break;
      case 1:
        fxmlLoader.setLocation(Cookbook.class.getResource("RecipesScene.fxml"));
        break;
      case 2:
        fxmlLoader.setLocation(Cookbook.class.getResource("AdminScene.fxml"));
        break;
      case 3:
        fxmlLoader.setLocation(Cookbook.class.getResource("FavouritesScene.fxml"));
        break;
      case 4:
        fxmlLoader.setLocation(Cookbook.class.getResource("MessagesScene.fxml"));
        break;
      case 5:
        fxmlLoader.setLocation(Cookbook.class.getResource("HelpScene.fxml"));
        break;
      case 6:
        fxmlLoader.setLocation(Cookbook.class.getResource("WeeklyPlanScene.fxml"));
        break;
      case 7:
        fxmlLoader.setLocation(Cookbook.class.getResource("ShoppingListScene.fxml"));
        break;
      default:
        throw new IOException("The provided scene ID to load does not exist.");
    }

    n = fxmlLoader.load();
    AnchorPane.setTopAnchor(n, 0.0);
    AnchorPane.setRightAnchor(n, 0.0);
    AnchorPane.setBottomAnchor(n, 0.0);
    AnchorPane.setLeftAnchor(n, 0.0);

    contentAnchor.getChildren().clear();
    contentAnchor.getChildren().add(n);

    if (sceneId == 7) {
      ShoppingListController controller = fxmlLoader.getController();
      controller.setController(controller);
    }
  }
}
