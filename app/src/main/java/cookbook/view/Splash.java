
package cookbook.view;

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
    static Circle rect = new Circle();
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
        
        int scale = 20;
        int dur = 500;
        rect = new Circle(100 - 3 * scale, 20, scale);
        rect.setFill(Color.YELLOWGREEN);
        Circle circle2 = new Circle(100 - 3 * scale, 20, scale);
        circle2.setFill(Color.GREEN);

        int[] rotins = {scale, 2 * scale, 3 * scale, 4 * scale, 5 * scale, -6 * scale, -5 * scale, -4 * scale, -3 * scale, -2 * scale};
        int[] rotins2 = {-scale, -2 * scale, -3 * scale, -4 * scale, -5 * scale, 6 * scale, 5 * scale, 4 * scale, 3 * scale, 2 * scale};
        int x, y;

        for (int i = 0; i < rotins.length; i++) {
            int rotationValue = rotins[i];
            int translationValue = rotins2[i];
            RotateTransition rt = new RotateTransition(Duration.millis(dur), rect);
            rt.setByAngle(rotationValue / Math.abs(rotationValue) * 90);
            rt.setCycleCount(2);
        
            TranslateTransition pt = new TranslateTransition(Duration.millis(dur), rect);
            x = (int) (rect.getRadius() + Math.abs(rotationValue));
            y = (int) (rect.getRadius() + Math.abs(rotationValue) + (Math.abs(rotationValue) / rotationValue) * scale);
            pt.setFromX(x);
            pt.setToX(y);
    
            RotateTransition rt2 = new RotateTransition(Duration.millis(dur), circle2);
            rt2.setByAngle(translationValue / Math.abs(translationValue) * 90);
            rt2.setCycleCount(2);
            TranslateTransition pt2 = new TranslateTransition(Duration.millis(dur), circle2);
            x = (int) (circle2.getRadius() + Math.abs(translationValue));
            y = (int) (circle2.getRadius() + Math.abs(translationValue) + (Math.abs(translationValue) / translationValue) * scale);
            pt2.setFromX(x);
            pt2.setToX(y);
        
            ParallelTransition pat = new ParallelTransition();
            pat.getChildren().addAll(pt, rt, pt2, rt2);
            pat.setCycleCount(1);
        
            seqT.getChildren().add(pat);
        }
        

        
        seqT.play();
        seqT.setNode(rect);
        //The text part
        Label label = new Label("Dish I.T");
        label.setFont(new Font("Verdana", 20));
        label.setStyle("-fx-text-fill:white");
        label.setLayoutX(10);
        label.setLayoutY(50);
        Label lab = new Label("Preparing Your Kitchen......");
        lab.setFont(new Font("Verdana", 12));
        lab.setStyle("-fx-text-fill:white");
        lab.setLayoutX(10);
        lab.setLayoutY(130);


        
        Image image = new Image("https://yt3.ggpht.com/a/AATXAJzbA6sPn9bCHxcXnQ_jlXFJ0ErDqpq2mOyiBg=s900-c-k-c0xffffffff-no-rj-mo");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(40);
        iv.setFitHeight(40);
        iv.setX(60);
        iv.setY(150);


        RotateTransition rt = new RotateTransition(Duration.millis(3000), iv);
        rt.setByAngle(360); 
        rt.setCycleCount(Timeline.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);

        rt.play();


        
        pane.getChildren().addAll(rect, label, lab, iv,circle2);
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
