/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

import com.fisheatfish.fisheatfish.GameLobby.Player;
import com.fisheatfish.fisheatfish.GameLobby.Enemy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;


/**
 *
 * @author A S U S
 */
public class GamePage {
    
    private String username;
    private final Set<KeyCode> activeKeys = new HashSet();
    private Map<Integer, double[]> enemyLevelProbabilities = new HashMap<>();
    private boolean isPaused = false;
    
    public GamePage(String username) {
        this.username = username;
    }
    
    public Scene createGameScene(Stage stage){
        
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 640, 480);
        Label score = new Label("Score: 0");
        Label level = new Label("Level: 1");
    
        //background
        Image backgroundImage = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/gameBackground.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        
        backgroundImageView.fitWidthProperty().bind(scene.widthProperty());
        backgroundImageView.fitHeightProperty().bind(scene.heightProperty());
        backgroundImageView.setStyle("-fx-border-color: red;");
        
        pane.getChildren().add(backgroundImageView);
        
        score.setLayoutX(10);  // X position from the left edge of the pane
        score.setLayoutY(0);  // Y position from the top edge of the pane

        // Position the level label below the score
        level.setLayoutX(10);  // Align level label with score on the X axis
        level.setLayoutY(score.getLayoutY() + score.getHeight() + 13);
        pane.getChildren().add(score);
        pane.getChildren().add(level);
        
        //Enemy
        Enemy level1Fish = new Enemy(1,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy1Left.png","file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy1Right.png");
        Enemy level2Fish = new Enemy(2,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy2Left.png","file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy2Right.png");
        Enemy level3Fish = new Enemy(3,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy3Left.jpeg","file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy3Right.jpeg");
        Enemy level4Fish = new Enemy(4,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy4Left.jpeg","file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy4Right.jpeg");
        
        level1Fish.setX(0);
        level1Fish.setY(Math.random());
//        level2Fish.setX(0);
//        level2Fish.setY(0);
//        level3Fish.setX(0);
//        level3Fish.setY(0);
//        level4Fish.setX(0);
//        level4Fish.setY(0);
//        
//        pane.getChildren().add(level1Fish.getImageView());

        // Get the scene width and height
//        double sceneWidth = scene.getWidth();
//        double sceneHeight = scene.getHeight();
//
//        // Set random X and Y positions for each enemy
//        level1Fish.setX(randomizeX(sceneWidth, "left"));
//        level1Fish.setY(Math.random() * sceneHeight);  // Random Y position within the height
//
//        level2Fish.setX(randomizeX(sceneWidth, "left"));
//        level2Fish.setY(Math.random() * sceneHeight);  // Random Y position within the height
//
//        level3Fish.setX(randomizeX(sceneWidth, "left"));
//        level3Fish.setY(Math.random() * sceneHeight);  // Random Y position within the height
//
//        level4Fish.setX(randomizeX(sceneWidth, "left"));
//        level4Fish.setY(Math.random() * sceneHeight);  // Random Y position within the height

        
//        for (Enemy enemy : enemies) {
//            pane.getChildren().add(enemy.getImageView());
//        }
//        String[] images = {
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy1.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy2.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy3.jpeg",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy4.jpeg"
//        };
//        
//        for (int i = 1; i <= 4; i++) {
//            Enemy enemy = new Enemy(i, images[i - 1]);
//            // Randomize vertical position
//            double initialY = Math.random() * (scene.getHeight() - enemy.getImageView().getFitHeight());
//
//            // Randomize whether the enemy starts on the left or right
//            double initialX = Math.random() < 0.5 ? 0 : scene.getWidth() - enemy.getImageView().getFitWidth();
//
//            enemy.setX(initialX);
//            enemy.setY(initialY);
//            
//            if (initialX == 0) {
//                enemy.setDirection("RIGHT"); // Starts moving to the right if on the left edge
//            } else {
//                enemy.setDirection("LEFT"); // Starts moving to the left if on the right edge
//            }
//            
//            double speed = 2 + Math.random() * 2; // Random speed between 2 and 4
//            enemy.setSpeed((int) (initialX == 0 ? speed : -speed)); 
//
//            enemies.add(enemy);
//            pane.getChildren().add(enemy.getImageView());
//        }
        
        //Player
        Player playerFish = new Player(1,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playerRight.jpeg");
        
        List<Enemy> enemies = new ArrayList<>();
        
        Timeline spawnEnemiesTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            // Create a new enemy and add it to the scene
            Enemy newEnemy = createNewEnemy(scene, pane, playerFish.getLevel());
            enemies.add(newEnemy);  // Add the new enemy to the list
            pane.getChildren().add(newEnemy.getImageView()); // Add the new enemy's image view to the pane
        }));

        // Set the timeline to repeat indefinitely
        spawnEnemiesTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnEnemiesTimeline.play();
    
        playerFish.getImageView().setX(200);
        playerFish.getImageView().setY(200);
        
        pane.getChildren().add(playerFish.getImageView());
                
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
        
        
         Timeline enemyMovement = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            // Move each enemy fish based on its speed
            for (Enemy enemy : enemies) {
                moveEnemy(enemy, scene, pane); // Move each enemy
            }
        }));
        enemyMovement.setCycleCount(Timeline.INDEFINITE);
        enemyMovement.play();
        
        final Timeline[] gameLoopWrapper = new Timeline[1];
        gameLoopWrapper[0] = new Timeline(new KeyFrame(Duration.millis(16),e->{
            double dx = 0;
            double dy = 0;
            
            if(activeKeys.contains(KeyCode.UP)) dy -= playerFish.getSpeed();
            if(activeKeys.contains(KeyCode.DOWN)) dy += playerFish.getSpeed();
            if(activeKeys.contains(KeyCode.LEFT)){
                dx -= playerFish.getSpeed();
                playerFish.changeDirection("LEFT");
            }
            if(activeKeys.contains(KeyCode.RIGHT)){
                dx += playerFish.getSpeed();
                playerFish.changeDirection("RIGHT");
            }
            
            playerFish.move(dx, dy, scene.getWidth()+1, scene.getHeight());
            
            for (Enemy enemy : new ArrayList<>(enemies)) { // Use a copy to avoid `ConcurrentModificationException`
                if (isCollision(playerFish, enemy)) {
                    System.out.println("Collision with enemy level " + enemy.getLevel());
                    if (playerFish.getLevel()>= enemy.getLevel()) {
                        System.out.println("Player eats the enemy!");
                        pane.getChildren().remove(enemy.getImageView());
                        enemies.remove(enemy);
                        
                        int points = enemy.getLevel() * 2; // Example: Score = enemy level * 2
                        playerFish.addScore(points);
                        score.setText("Score: " + Integer.toString(playerFish.getScore()));
                        level.setText("Level: " + Integer.toString(playerFish.getLevel()));
                        System.out.println("Score: " + playerFish.getScore());
                    } else {
                        System.out.println("Game Over!");
//                        stopGame(gameLoopWrapper[0], enemyMovement,spawnEnemiesTimeline, pane, stage); // Reference is now safe
                        showGameOverPopup(gameLoopWrapper[0], enemyMovement,spawnEnemiesTimeline,pane, stage, playerFish);
                    }
                    break;
                }
            }
            
            if(activeKeys.contains(KeyCode.ESCAPE)){
                System.out.print("Game Paused");
                pauseGame(gameLoopWrapper[0], enemyMovement,spawnEnemiesTimeline,pane,stage,playerFish);
            }
            
        }));
        gameLoopWrapper[0].setCycleCount(Timeline.INDEFINITE);
        gameLoopWrapper[0].play();        
        
        
        pane.setFocusTraversable(true);
        
        stage.setTitle("Fish Eat Fish");
        
        return scene;
    }
    
    private void moveEnemy(Enemy enemy, Scene scene,Pane pane) {
        double currentX = enemy.getX();
        double currentY = enemy.getY();

        double speed = enemy.getSpeed(); // Speed should be set in the Enemy object

        // Move the enemy based on its direction
        if ("LEFT".equals(enemy.getDirection())) {
            currentX -= speed;
        } else if ("RIGHT".equals(enemy.getDirection())) {
            currentX += speed;
        }

        // If the enemy goes beyond the left or right boundary, reset its position (offscreen)
        if (currentX < -enemy.getImageView().getFitWidth()) {
            // Respawn on the right side
            currentX = scene.getWidth();
            currentY = Math.random() * (scene.getHeight() - enemy.getImageView().getFitHeight()); // Random Y within bounds
        } else if (currentX > scene.getWidth()) {
            // Respawn on the left side
            currentX = -enemy.getImageView().getFitWidth();
            currentY = Math.random() * (scene.getHeight() - enemy.getImageView().getFitHeight()); // Random Y within bounds
        }

        // Set the new position of the enemy
        enemy.setX(currentX);
        enemy.setY(currentY);

        // Update the position of the enemy's image view
        enemy.getImageView().setX(currentX);
        enemy.getImageView().setY(currentY);
    }
    
    private Enemy createNewEnemy(Scene scene, Pane pane, int playerLevel) {
        int enemyLevel = getEnemyLevel(playerLevel);
    // Image arrays for left and right directions
//        String[] rightImages = {
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy1Right.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy2Right.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy3Right.jpeg",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy4Right.jpeg"
//        };
//
//        String[] leftImages = {
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy1Left.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy2Left.png",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy3Left.jpeg",
//            "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy4Left.jpeg"
//        };
            
        String leftImage = String.format("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy%dLeft.png", enemyLevel);
        String rightImage = String.format("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy%dRight.png", enemyLevel);

        // Randomly choose an image for the enemy (either left or right direction)
//        int randomLevel = (int) (Math.random() * 4);  // Random level between 0 and 3 for 4 possible images
//        String leftImage = leftImages[randomLevel];  // Image for left direction
//        String rightImage = rightImages[randomLevel]; // Image for right direction

        // Create a new enemy with random position
        Enemy newEnemy = new Enemy(enemyLevel, leftImage, rightImage);

        double enemyWidth = newEnemy.getImageView().getFitWidth();
        double enemyHeight = newEnemy.getImageView().getFitHeight();    

        // Randomly choose if the enemy starts on the left or right side
        double spawnX;
        String direction;

        if (Math.random() < 0.5) {
            // Spawn on the left side
            spawnX = -enemyWidth;  // Just off the left edge
            direction = "RIGHT"; // Moving right
        } else {
            // Spawn on the right side
            spawnX = scene.getWidth(); // Start just off the right edge
            direction = "LEFT"; // Moving left
        }

        // Set the direction for the enemy
        newEnemy.setDirection(direction);

        // Random Y position, ensuring it's within the scene bounds
        double spawnY = Math.random() * (scene.getHeight() - enemyHeight);

        newEnemy.setX(spawnX);
        newEnemy.setY(Math.max(0, Math.min(spawnY, scene.getHeight() - enemyHeight)));

        // Debug: Print out the new enemy position and direction
        System.out.println("New enemy(Level" + newEnemy.getLevel() + ") spawned at X=" + newEnemy.getX() + ", Y=" + newEnemy.getY() + " Moving: " + direction);

        return newEnemy;
    }

    private double randomizeX(double sceneWidth, String side) {
        if ("left".equals(side)) {
            return -Math.random() * 100; // Enemy starts just off-screen to the left
        } else {
            return sceneWidth + Math.random() * 100; // Enemy starts just off-screen to the right
        }
    }
    
    private boolean isCollision(Player player, Enemy enemy) {
//        System.out.println("Player bounds: " + player.getImageView().getBoundsInParent());
//        System.out.println("Enemy bounds: " + enemy.getImageView().getBoundsInParent());
        return player.getImageView().getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent());
    }
    
    public void initializeProbabilities() {
        // Player level -> Probabilities for enemy levels 1, 2, 3, 4
        enemyLevelProbabilities.put(1, new double[] {0.8, 0.2, 0.0, 0.0}); // Mostly level 1 enemies
        enemyLevelProbabilities.put(2, new double[] {0.5, 0.3, 0.2, 0.0}); // Higher chance of level 2
        enemyLevelProbabilities.put(3, new double[] {0.4, 0.3, 0.2, 0.1}); // Mix of levels 1, 2, and 3
        enemyLevelProbabilities.put(4, new double[] {0.0, 0.3, 0.4, 0.3}); // Mix of all levels
    }
    
    private int getEnemyLevel(int playerLevel) {
        System.out.println("Player level " + playerLevel);
        initializeProbabilities();
        // Get the probabilities for the current player level
        double[] probabilities = enemyLevelProbabilities.getOrDefault(playerLevel, new double[] {0.25, 0.25, 0.25, 0.25}); // Default: equal chance
//        double[] probabilities = enemyLevelProbabilities.getOrDefault(playerLevel-1, new double[] {0,1,0,0}); // Default: equal chance
        // Generate a random number between 0 and 1
        double random = Math.random();
        double cumulativeProbability = 0.0;

        System.out.println("Random number: " + random);
        System.out.println("Probabilities: " + Arrays.toString(probabilities));

        // Determine the enemy level based on the random number
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (random <= cumulativeProbability) {
                return i + 1; // Levels are 1-indexed
            }
        }

        // Fallback (shouldn't happen if probabilities sum to 1)
        return 1;
    }
    
    private void showGameOverPopup(Timeline gameLoop, Timeline enemyMovement, Timeline spawnEnemiesTimeline, Pane pane, Stage stage, Player playerFish) {
    // Create a new GridPane for the pop-up
        GridPane popupGrid = new GridPane();
        popupGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent background
        gameLoop.stop();
        enemyMovement.stop();
        spawnEnemiesTimeline.stop();

        // Create a label for the "Game Over" message
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        // Create the "Restart" and "Quit" buttons
        Button restartButton = new Button("Restart");
        Button quitButton = new Button("Quit");

        // Add the label and buttons to the grid (place them in specific rows and columns)
        popupGrid.setAlignment(Pos.CENTER);
        popupGrid.setHgap(65);
        popupGrid.add(gameOverLabel, 0, 0, 2, 1); // Label spans across two columns
        popupGrid.add(restartButton, 0, 1);
        popupGrid.add(quitButton, 1, 1);

        // Center the GridPane in the window
        popupGrid.setPrefSize(480, 360);
        double centerX = (stage.getWidth() - popupGrid.getPrefWidth()) / 2;
        double centerY = (stage.getHeight() - popupGrid.getPrefHeight()) / 2;
        popupGrid.setLayoutX(centerX);
        popupGrid.setLayoutY(centerY);

        // Add the grid to the main pane
        pane.getChildren().add(popupGrid);

        // Set up button actions
        restartButton.setOnAction(e -> {
            // Restart the game
            restartGame(stage);
            pane.getChildren().remove(popupGrid); // Remove the popup
        });

        quitButton.setOnAction(e -> {
            // Quit the game
//            System.exit(0);
            saveGameDataToCSV(playerFish.getScore(), username);
            LobbyMainPage lobbyPage = new LobbyMainPage(username);
            Scene lobbyScene = lobbyPage.createLobbyScene(stage);
            stage.setTitle("LobbyMainPage");
            stage.setScene(lobbyScene);
        });
    }
    
    private void restartGame(Stage stage) {
    // Reload the scene or create a new one
        GamePage gamePage = new GamePage(username);  // Assuming you have a GamePage class
        Scene newGameScene = gamePage.createGameScene(stage);

        // Set the new scene to the stage
        stage.setScene(newGameScene);
        stage.show();
    }

    private void pauseGame(Timeline gameLoop, Timeline enemyMovement, Timeline spawnEnemiesTimeline,Pane pane, Stage stage, Player playerFish) {
        GridPane popupGrid = new GridPane();
        popupGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        Label gamePauseLabel = new Label("Game Pause");
        gamePauseLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");
        
        Button continueButton = new Button("Continue");
        Button quitButton = new Button("Quit");
        
        gameLoop.pause();
        enemyMovement.pause();
        spawnEnemiesTimeline.pause();
        
        continueButton.setOnAction(event ->{
            gameLoop.play();
            enemyMovement.play();
            spawnEnemiesTimeline.play();
            pane.getChildren().remove(popupGrid);
        });
        
         quitButton.setOnAction(e -> {
            // Quit the game
//            System.exit(0);
            saveGameDataToCSV(playerFish.getScore(), username);
            LobbyMainPage lobbyPage = new LobbyMainPage(username);
            Scene lobbyScene = lobbyPage.createLobbyScene(stage);
            stage.setTitle("LobbyMainPage");
            stage.setScene(lobbyScene);
        });
        
        popupGrid.setAlignment(Pos.CENTER);
        popupGrid.setHgap(60);
        popupGrid.add(gamePauseLabel, 0, 0, 2, 1); // Label spans across two columns
        popupGrid.add(continueButton, 0, 1);
        popupGrid.add(quitButton, 1, 1);
        

        // Center the GridPane in the window
        popupGrid.setPrefSize(480, 360);
        double centerX = (stage.getWidth() - popupGrid.getPrefWidth()) / 2;
        double centerY = (stage.getHeight() - popupGrid.getPrefHeight()) / 2;
        popupGrid.setLayoutX(centerX);
        popupGrid.setLayoutY(centerY);

        // Add the grid to the main pane
        pane.getChildren().add(popupGrid);

    }
    
    private void saveGameDataToCSV(int playerScore, String username) {
        // Define the file path (you can change the file path as needed)
        String filePath = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";
        

        // Create the file if it doesn't exist
        File file = new File(filePath);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Write header if the file doesn't exist
            if (!fileExists) {
                writer.write("Username,Score,Date\n");
            }

            // Get the current date and time
            String dateTime = getCurrentDateTime();

            // Write the game data (username, score, date/time)
            writer.write(username + "," + playerScore + "," + dateTime + "\n");

            System.out.println("Game data saved to CSV: " + username + ", " + playerScore + ", " + dateTime + ", " + filePath);
        } catch (IOException e) {
            // Handle any exceptions that occur during file writing
            showAlert("Error saving game data: " + e.getMessage());
        }
    }

    // Method to get the current date and time in a specific format
    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    
     private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
