/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cookbook extends Application {
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
                Group start = new Group();
                Scene startScene = new Scene(start, 200,150);
                Button button = new Button();

                button.setText("Login");
                button.setLayoutX(50);
                button.setLayoutY(50);
                button.setOnAction(e2 -> primaryStage.setScene(UserPageScene.getUserPage()));
                start.getChildren().add(button);

                primaryStage.setTitle("Dish IT");
                primaryStage.setScene(startScene);
                primaryStage.show();
            });
            timeline.play();
        });
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
