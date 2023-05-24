/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//import javax.management.Query;

public class Cookbook extends Application {

    private static final int WINDOW_WIDTH = 1170;
    private static final int WINDOW_HEIGHT = 617;
    private static final String APP_TITLE = "Dish IT welcomes you";
    private static final String BACKGROUND_IMAGE_PATH = "https://jooinn.com/images/color-background-5.png";
    private static final String LOGO_IMAGE_PATH = "https://pin.it/4cIJnkW";
    private static final String APP_NAME = "Dish IT Welcomes you";
    private static final Font APP_NAME_FONT = new Font("Arial", 28);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL backgroundImageUrl = new URL(
                "https://cdn.discordapp.com/attachments/1110126286715756604/1110924197850534039/IMG_2556.png");
        Image backgroundImage = new Image(backgroundImageUrl.toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);

        URL logoImageUrl = new URL(
                "https://www.owl-marketing.com/wp-content/uploads/2010/08/29.png");
        Image image = new Image("file:" + LOGO_IMAGE_PATH);
        ImageView logoImageView = new ImageView(image);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), logoImageView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setDelay(Duration.seconds(0.5));

        // Slide up animation for the app name text
        Text appNameText = new Text(APP_NAME);
        appNameText.setFont(APP_NAME_FONT);
        appNameText.setFill(Color.WHITESMOKE);

        VBox vbox = new VBox(20, logoImageView, appNameText);
        vbox.setAlignment(Pos.CENTER);

        vbox.setTranslateX(-300);
        vbox.setTranslateY(300);

    }
    // @Override
    // public void start(Stage stage) {
    // try {
    // FXMLLoader loader = new
    // FXMLLoader(getClass().getClassLoader().getResource("AddRecipeScene.fxml"));
    // AnchorPane root = loader.load();
    // Scene scene = new Scene(root, 1200, 800, false, null);
    // stage.setScene(scene);
    // stage.show();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

}
