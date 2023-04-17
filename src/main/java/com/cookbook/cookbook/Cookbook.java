package com.cookbook.cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cookbook extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("primary-stage.fxml"));
        primaryStage.setTitle("App name");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

//        VBox root = new VBox();
//        root.setPadding(new Insets(5));
//        Label title = new Label("JavaFX");
//        Label mysql;
//
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Cookbook?user=root&password=root&useSSL=false");
//            mysql = new Label("Driver found and connected");
//
//        } catch (SQLException e) {
//            mysql = new Label("An error has occurred: " + e.getMessage());
//        }
//
//        root.getChildren().addAll(title, mysql);
//
//        primaryStage.setScene(new Scene(root, 400, 200));
//        primaryStage.setTitle("JavaFX");
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


