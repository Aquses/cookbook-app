
package cookbook;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * This is my own splash screen, that I made myself.
 *
 */
public class Splash
{

    static Scene splash;
    static Rectangle rect = new Rectangle();
    final private Pane pane;
    final private SequentialTransition seqT;

    public Splash()
    {
        pane = new Pane();
        pane.setStyle("-fx-background-color:black");

        splash = new Scene(pane);
        seqT = new SequentialTransition();
    }

    public void show()
    {
        
        int scale = 30;
        int dur = 800;
        rect = new Rectangle(100 - 2 * scale, 20, scale, scale);
        rect.setFill(Color.AQUAMARINE);

        int[] rotins = {scale, 2 * scale, 3 * scale, 4 * scale, 5 * scale, -6 * scale, -5 * scale, -4 * scale, -3 * scale, -2 * scale};
        int x, y;
        for (int i : rotins) {
            //rotating the square
            RotateTransition rt = new RotateTransition(Duration.millis(dur), rect);
            rt.setByAngle(i / Math.abs(i) * 90);
            rt.setCycleCount(1);
            //moving the square horizontally
            TranslateTransition pt = new TranslateTransition(Duration.millis(dur), rect);
            x = (int) (rect.getX() + Math.abs(i));
            y = (int) (rect.getX() + Math.abs(i) + (Math.abs(i) / i) * scale);
            pt.setFromX(x);
            pt.setToX(y);
            //parallelly execute them and you get a rolling square
            ParallelTransition pat = new ParallelTransition();
            pat.getChildren().addAll(pt, rt);
            pat.setCycleCount(1);
            seqT.getChildren().add(pat);
        }
        //playing the animation
        seqT.play();
        //lambda code sourced from StackOverflow, fades away stage
        seqT.setNode(rect);
        //The text part
        Label label = new Label("Dish.IT");
        label.setFont(new Font("Verdana", 30));
        label.setStyle("-fx-text-fill:white");
        label.setLayoutX(140);
        label.setLayoutY(70);
        Label lab = new Label("Loading...");
        lab.setFont(new Font("Times New Roman", 10));
        lab.setStyle("-fx-text-fill:white");
        lab.setLayoutX(70);
        lab.setLayoutY(80);
        //A complimentary image

        Image image = new Image("https://s3.amazonaws.com/media.eremedia.com/uploads/2012/08/24111405/stackoverflow-logo-700x467.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(32);
        iv.setFitHeight(32);
        iv.setX(174);
        iv.setY(130);
        //now adding everything to position, opening the stage, start the animation
        pane.getChildren().addAll(rect, label, lab, iv);

        seqT.play();
    }

    public Scene getSplashScene()
    {
        return splash;
    }

    public SequentialTransition getSequentialTransition()
    {
        return seqT;
    }
}
