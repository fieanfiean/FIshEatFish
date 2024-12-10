/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;
import com.fisheatfish.fisheatfish.GameLobby.LobbyMainPage;

import com.fisheatfish.fisheatfish.Database.MongoDBConnection;
import com.mongodb.client.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author A S U S
 */
public class LoginPage {
    public Scene createLoginScene(Stage stage) {
        // Create labels and input fields
        stage.setTitle("Login Page");

        Label usernameLabel = new Label("Username:            ");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:            ");
        PasswordField passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        
        System.out.println("usernname is " + usernameField);

        // Create the Register hyperlink
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // Handle the registration action (open registration window or show registration form)
             RegisterPage registerPage = new RegisterPage();
             Scene registerScene = registerPage.createRegisterScene(stage);
             stage.setTitle("Register Page");
             stage.setScene(registerScene);
            // For now, just printing to the console
        });

        // Create the GridPane for the layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.setAlignment(Pos.CENTER);
        
        // Add controls to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(registerButton, 1, 2); // Add the Register hyperlink to the grid
        
        GridPane.setHalignment(loginButton, HPos.RIGHT);

        // Handle login button click
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
            showAlert("Username and password must not be blank!");
            return;
    }
            
            try{
                MongoDatabase database = MongoDBConnection.connectToDatabase();
                MongoCollection<Document>  userCollection = database.getCollection("Users");
                
                Document user = userCollection.find(new Document("username", username)).first();
                
                if(user == null){
                    showAlert("User nor found");
                }
                else{
                    String storedPassword = user.getString("password");
                    if(BCrypt.checkpw(password, storedPassword)){
                        showAlert("Login successful!");
                        LobbyMainPage lobbyPage = new LobbyMainPage(username);
                        Scene lobbyScene = lobbyPage.createLobbyScene(stage);
                        stage.setScene(lobbyScene);
                    } else {
                        showAlert("Incorrect password!");
                    }
                }
                
            }catch(Exception ex){
                showAlert("An error occurred: " + ex.getMessage());
            }
        });

        // Return the Scene
        return new Scene(grid, 640, 480);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
