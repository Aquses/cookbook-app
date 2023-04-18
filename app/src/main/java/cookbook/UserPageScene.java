package cookbook;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UserPageScene {

    public static Scene getUserPage() {
        // create some sample user data
        String username = "johndoe";
        String email = "johndoe@example.com";
        int age = 30;

        // create labels to display the user data
        Label usernameLabel = new Label("Username: " + username);
        Label emailLabel = new Label("Email: " + email);
        Label ageLabel = new Label("Age: " + age);

        // create a VBox to hold the labels
        VBox userBox = new VBox(usernameLabel, emailLabel, ageLabel);

        // create a scene with the user box as the root node
        Scene scene = new Scene(userBox, 400, 400);

        return scene;
    }
}
