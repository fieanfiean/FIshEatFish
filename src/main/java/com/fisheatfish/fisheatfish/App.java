package com.fisheatfish.fisheatfish;

import com.fisheatfish.fisheatfish.Authentication.FirstPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.fisheatfish.fisheatfish.Authentication.LoginPage;
import com.fisheatfish.fisheatfish.GameLobby.LobbyMainPage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {        
        FirstPage firstPage = new FirstPage();
        Scene firstScene = firstPage.createFirstScene(stage);
        stage.setScene(firstScene);
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}