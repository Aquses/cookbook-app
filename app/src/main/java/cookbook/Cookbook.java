package cookbook;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
                button.setLayoutX(160);
                button.setLayoutY(150);
                button.setOnAction(e2 -> primaryStage.setScene(UserLoginScene.getScene()));
                start.getChildren().add(button);

                primaryStage.setTitle("Dish IT");
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
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