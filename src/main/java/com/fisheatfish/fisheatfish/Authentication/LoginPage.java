/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;
import com.fisheatfish.fisheatfish.GameLobby.LobbyMainPage;

import com.fisheatfish.fisheatfish.Database.MongoDBConnection;
import com.fisheatfish.fisheatfish.GameLobby.GameMusic;
import com.mongodb.client.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author A S U S
 */
public class LoginPage {
    public Scene createLoginScene(Stage stage) {
        GameMusic buttonEffect = new GameMusic();

        // Create labels and input fields
        stage.setTitle("Login Page");

        Label usernameLabel = new Label("Username:            ");
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:            ");
        passwordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        PasswordField passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: Black;"); // Light blue background, white text

        
        System.out.println("usernname is " + usernameField);

        // Create the Register hyperlink
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: Black;"); // Light blue background, white text
        registerButton.setOnAction(e -> {
            // Handle the registration action (open registration window or show registration form)
             buttonEffect.playEffect("buttonEffect");
             RegisterPage registerPage = new RegisterPage();
             Scene registerScene = registerPage.createRegisterScene(stage);
             stage.setTitle("Register Page");
             stage.setScene(registerScene);
            // For now, just printing to the console
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: Black;"); // Light blue background, white text
        cancelButton.setOnAction(e->{
            buttonEffect.playEffect("buttonEffect");
            FirstPage firstPage = new FirstPage();
            Scene firstScene = firstPage.createFirstScene(stage);
            stage.setScene(firstScene); 
        });

        // Create the parent container
        StackPane root = new StackPane();

        // Create the smaller grid for the login form
        GridPane detailsGrid = new GridPane();
        detailsGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        detailsGrid.setPadding(new Insets(20)); // Add padding inside the smaller grid
        detailsGrid.setVgap(10);
        detailsGrid.setHgap(10);
        detailsGrid.setMaxWidth(400); // Optional: Limit the width of the smaller grid
        detailsGrid.setMaxHeight(200);
        detailsGrid.setAlignment(Pos.CENTER);

        // Add labels and fields to the smaller grid
        detailsGrid.add(usernameLabel, 0, 0);
        detailsGrid.add(usernameField, 1, 0);
        detailsGrid.add(passwordLabel, 0, 1);
        detailsGrid.add(passwordField, 1, 1);

        // Create an HBox for buttons
        HBox buttonBox = new HBox(10); // Spacing of 10 between buttons
        buttonBox.setAlignment(Pos.CENTER); // Center align buttons
        buttonBox.getChildren().addAll(loginButton, registerButton, cancelButton);

        // Add the button box to the smaller grid
        detailsGrid.add(buttonBox, 0, 2, 2, 1); // Spanning two columns for centering

        // Set the background image for the parent container
        Image backgroundImage = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/AuthenticationBackground.jpg");
        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bgImage));

        // Add the smaller grid to the parent container
        root.getChildren().add(detailsGrid);


        // Handle login button click
        loginButton.setOnAction(e -> {
            buttonEffect.playEffect("buttonEffect");
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
        return new Scene(root, 640, 480);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
