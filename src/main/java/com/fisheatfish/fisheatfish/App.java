package com.fisheatfish.fisheatfish;

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
        LoginPage loginPage = new LoginPage();
        Scene loginScene = loginPage.createLoginScene(stage);
        stage.setScene(loginScene);

//        LobbyMainPage lobbyPage = new LobbyMainPage();
//        Scene lobbyScene = lobbyPage.createLobbyScene(stage);
//        stage.setTitle("LobbyMainPage");
//        stage.setScene(lobbyScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}