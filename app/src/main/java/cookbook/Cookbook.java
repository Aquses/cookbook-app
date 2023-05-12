/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import java.io.IOException;

//import cookbook.model.QueryMaker;
import cookbook.view.Splash;
import cookbook.view.UserLoginScene;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

//import javax.management.Query;

public class Cookbook extends Application {

    // [ COMMENT ]
    // This start method was to test the Browse and Search scenes
    // They are not connected to the rest of scenes as of right now
    // Currently it will simply open the Browse and Search scenes individually
    // Need Hub scene to transition to browse or search

    // [ START METHOD WITH RESPECTIVE BROWSE AND SEARCH ]
   /* @Override
    public void start(Stage stage) {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("browserecipe.fxml"));

            // Uncomment below (searchpage.fxml) and comment above (browserecipe.fxml) to see search scene
            Parent root = FXMLLoader.load(getClass().getResource("hub.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    // [ COMMENT ]
    // Original app execution with animation > login > userpage (containing add recipe)
    // Now UserPageScene for displaying list of recipes is Browse class (potentially delete UserPageScene class)
    // Must implement transitions for Search and Browse scenes with the rest
    // AddRecipeStage class not implemented with Browse class "addRecipe" button


    // [ ORIGINAL START METHOD BELOW ]
    // [ ORIGINAL START METHOD BELOW ]
    @Override
    public void start(Stage primaryStage) throws Exception {
     Splash splash = new Splash();
       

    splash.show();
    primaryStage.setScene(splash.getSplashScene());
       
    splash.getSequentialTransition().setOnFinished(e -> {
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(1500),
            
            new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((event1) -> {
                VBox start = new VBox();
                Scene startScene = new Scene(start, 50,50);
                Button button = new Button();
                Label header = new Label("Are you ready for DISH IT!!!");
                header.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-style: italic;");

                start.setSpacing(10);
                start.setAlignment(Pos.CENTER);
              
                button.setText("Login");

                button.setOnAction(e2 -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/cookbook/LoginScreenScene.fxml"));
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                
                start.getChildren().addAll(header, button);

                primaryStage.setTitle("Dish IT");
                primaryStage.setWidth(600);
                primaryStage.setHeight(400);
                primaryStage.setScene(startScene);
                primaryStage.show();
            });
            timeline.play();
        });
        
        primaryStage.show();
    }


    // @Override
    // public void start(Stage stage) {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddRecipeScene.fxml"));
    //         AnchorPane root = loader.load();
    //         Scene scene = new Scene(root, 1200, 800, false, null);
    //         stage.setScene(scene);
    //         stage.show();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    
    public static void main(String[] args) {
        launch(args);  
    }
}
