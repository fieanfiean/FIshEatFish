package com.fisheatfish.fisheatfish;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        LoginPage loginPage = new LoginPage();
        Scene loginScene = loginPage.createLoginScene(stage);
        stage.setTitle("Login Page");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}