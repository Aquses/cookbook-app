package cookbook.view;

import cookbook.model.Help;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DisplayTutorialScene {

    @FXML
    private ImageView tutorialImageView;
    public void addRecipeObject(Help help, AnchorPane parentAnchorPane) {
        Image tutorialImage = new Image(getClass().getResource(help.getImage()).toExternalForm());
        tutorialImageView.setImage(tutorialImage);
    }
}
