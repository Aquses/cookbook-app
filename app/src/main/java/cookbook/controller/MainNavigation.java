package cookbook.controller;

import cookbook.Cookbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private AnchorPane ContentAnchor;
    @FXML
    private Button favouritesButton;
    private static boolean admin;
    private static int user_id;
 
    public static Scene getScene(boolean isAdmin) throws IOException {
        admin = isAdmin;
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("NavBar.fxml"));
        Scene hub = new Scene(fxmlLoader.load(), 1280, 700);

        return hub;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public static int getUserId() {
        return user_id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AdminButton.setVisible(admin);
        try {
            loadScene(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        menuControls();
    }

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
    }

    private void loadScene(int sceneID) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Node n;

        switch (sceneID){
            // Load hub
            case 0:
                fxmlLoader.setLocation(Cookbook.class.getResource("HubScene.fxml"));
                break;
            // Load recipes scene
            case 1:
                fxmlLoader.setLocation(Cookbook.class.getResource("RecipesScene.fxml"));
                break;
            // Load admin scene
            case 2:
                fxmlLoader.setLocation(Cookbook.class.getResource("AdminScene.fxml"));
                break;
            case 3:
                fxmlLoader.setLocation(Cookbook.class.getResource("FavouritesScene.fxml"));
                break;// Load favourites scene
            // Load hub when given an invalid number
            default:
                IOException wrongSceneIDException = new IOException("The provided scene ID to load does not exist.");
                throw wrongSceneIDException;
        }

        n = fxmlLoader.load();
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);

        ContentAnchor.getChildren().clear();
        ContentAnchor.getChildren().add(n);

        return;
    }
}
