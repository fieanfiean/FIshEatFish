/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;


import java.util.ArrayList;
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
import java.util.Set;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



/**
 *
 * @author A S U S
 */
public class GamePage {
    
    private String username;
//    private GameMusic musicManager;
    private final Set<KeyCode> activeKeys = new HashSet();
    
    public GamePage(String username) {
        this.username = username;
    }
    
    public Scene createGameScene(Stage stage){
        
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 640, 480);
        Label score = new Label("Score: 0");
        Label level = new Label("Level: 1");
        
        //background music
        GameMusic musicManager = new GameMusic();
        musicManager.playMusic("gamePlay");

    
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
        
        //Player
        Player playerFish = new Player(1,"file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playerRight.jpeg");
        
        List<Enemy> enemies = new ArrayList<>();
        
        Timeline spawnEnemiesTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            // Create a new enemy and add it to the scene
            Enemy newEnemy = Enemy.createNewEnemy(scene, pane, playerFish.getLevel());
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
                enemy.moveEnemy(enemy, scene, pane); // Move each enemy
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
                        
                        int points = enemy.getLevel() * 10; // Example: Score = enemy level * 2
                        playerFish.addScore(points);
                        playerFish.countFishEaten(enemy.getLevel());
                        score.setText("Score: " + Integer.toString(playerFish.getScore()));
                        level.setText("Level: " + Integer.toString(playerFish.getLevel()));
                        System.out.println("Score: " + playerFish.getScore());
                    } else {
                        System.out.println("Game Over!");
//                        stopGame(gameLoopWrapper[0], enemyMovement,spawnEnemiesTimeline, pane, stage); // Reference is now safe
                        GameOverPage gameOverPage = new GameOverPage(username,musicManager);
                        gameOverPage.showGameOverPopup(gameLoopWrapper[0], enemyMovement, spawnEnemiesTimeline, pane, stage, playerFish, musicManager);
                    }
                    break;
                }
            }
            
            if(activeKeys.contains(KeyCode.ESCAPE)){
                System.out.print("Game Paused");
                GamePauseAndRestartPage gamePauseAndRestartPage = new GamePauseAndRestartPage(username,musicManager);
                gamePauseAndRestartPage.pauseGame(gameLoopWrapper[0], enemyMovement,spawnEnemiesTimeline,pane,stage,playerFish);
            }
            
        }));
        gameLoopWrapper[0].setCycleCount(Timeline.INDEFINITE);
        gameLoopWrapper[0].play();        
        
        
        pane.setFocusTraversable(true);
        
        stage.setTitle("Fish Eat Fish");
        
        return scene;
    }
    
    private boolean isCollision(Player player, Enemy enemy) {
        return player.getImageView().getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent());
    }
    
}
