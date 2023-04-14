package cookbook; 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label with the text "Hello World!"
        Label helloLabel = new Label("Hello World!");

        // Create a StackPane layout and add the label to it
        StackPane root = new StackPane();
        root.getChildren().add(helloLabel);

        // Create a Scene with the StackPane as the root and set its size
        Scene scene = new Scene(root, 300, 200);

        // Set the Scene to the primaryStage (main window) and set its title
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World App");

        // Show the primaryStage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
 