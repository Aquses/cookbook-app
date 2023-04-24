/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cookbook extends Application {

    // [ COMMENT ]
    // This start method was to test the Browse and Search scenes
    // They are not connected to the rest of scenes as of right now
    // Currently it will simply open the Browse and Search scenes individually
    // Need Hub scene to transition to browse or search

    // [ START METHOD WITH RESPECTIVE BROWSE AND SEARCH ]
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hub.fxml"));

            // Uncomment below (searchpage.fxml) and comment above (browserecipe.fxml) to see search scene
            // Parent root = FXMLLoader.load(getClass().getResource("searchpage.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // [ COMMENT ]
    // Original app execution with animation > login > userpage (containing add recipe)
    // Now UserPageScene for displaying list of recipes is Browse class (potentially delete UserPageScene class)
    // Must implement transitions for Search and Browse scenes with the rest
    // AddRecipeStage class not implemented with Browse class "addRecipe" button

    // [ ORIGINAL START METHOD BELOW ]
    // @Override
    // public void start(Stage primaryStage) throws Exception {
    //     Splash splash = new Splash();
    

    //     splash.show();
    //     primaryStage.setScene(splash.getSplashScene());
       
    //     splash.getSequentialTransition().setOnFinished(e -> {
    //         Timeline timeline = new Timeline();
    //         KeyFrame key = new KeyFrame(Duration.millis(1500),
            
    //         new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
    //         timeline.getKeyFrames().add(key);
    //         timeline.setOnFinished((event1) -> {
    //             Group start = new Group();
    //             Scene startScene = new Scene(start, 200,150);
    //             Button button = new Button();

    //             button.setText("Login");
    //             button.setLayoutX(160);
    //             button.setLayoutY(150);
    //             button.setOnAction(e2 -> primaryStage.setScene(UserLoginScene.getScene()));
    //             start.getChildren().add(button);

    //             primaryStage.setTitle("Dish IT");
    //             primaryStage.setWidth(400);
    //             primaryStage.setHeight(400);
    //             primaryStage.setScene(startScene);
    //             primaryStage.show();
    //         });
    //         timeline.play();
    //     });
        
    //     primaryStage.show();
    // }

    public static void main(String[] args) {
        launch(args);
    }
}
