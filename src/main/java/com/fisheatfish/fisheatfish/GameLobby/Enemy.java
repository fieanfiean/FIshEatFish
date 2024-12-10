package com.fisheatfish.fisheatfish.GameLobby;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Enemy {
    private int level;
    private int size;
    private int speed;
    private String direction;
    private String leftImagePath;
    private String rightImagePath;
    private ImageView imageView;

    // Map to hold the probability for each enemy level
    private static Map<Integer, double[]> enemyLevelProbabilities = new HashMap<>();

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
        updateImage();
    }

    public Enemy(int level, String leftImagePath, String rightImagePath) {
        this.level = level;

        // Set size and speed based on level
        switch (level) {
            case 1:
                this.size = 5;  // Small
                this.speed = 1; // Slow
                break;
            case 2:
                this.size = 8;  // Medium
                this.speed = 2; // Moderate
                break;
            case 3:
                this.size = 12; // Large
                this.speed = 3; // Fast
                break;
            case 4:
                this.size = 15; // Extra large
                this.speed = 4; // Very fast
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
        
        this.leftImagePath = leftImagePath;
        this.rightImagePath = rightImagePath;

        this.direction = "RIGHT";
        
        try {
            Image image = new Image(rightImagePath);
            this.imageView = new ImageView(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found at: " + rightImagePath);
            this.imageView = new ImageView(); // Fallback
        }

        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size * 10); // Adjust size
        this.imageView.setFitHeight(size * 5);
    }

    // Initialize the probabilities for each level of the enemy
    public static void initializeProbabilities() {
        enemyLevelProbabilities.put(1, new double[] {0.6, 0.4, 0.0, 0.0}); // Mostly level 1 enemies
        enemyLevelProbabilities.put(2, new double[] {0.5, 0.3, 0.2, 0.0}); // Higher chance of level 2
        enemyLevelProbabilities.put(3, new double[] {0.4, 0.3, 0.2, 0.1}); // Mix of levels 1, 2, and 3
        enemyLevelProbabilities.put(4, new double[] {0.0, 0.3, 0.4, 0.3}); // Mix of all levels
    }

    // Get the enemy level based on player level
    private static int getEnemyLevel(int playerLevel) {
        System.out.println("Player level " + playerLevel);
        initializeProbabilities();
        // Get the probabilities for the current player level
        double[] probabilities = enemyLevelProbabilities.getOrDefault(playerLevel, new double[] {0.25, 0.25, 0.25, 0.25}); // Default: equal chance
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

    // Create a new enemy with a random position and direction
    public static Enemy createNewEnemy(Scene scene, Pane pane, int playerLevel) {
        int enemyLevel = getEnemyLevel(playerLevel);

        // Image paths for left and right directions
        String leftImage = String.format("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy%dLeft.png", enemyLevel);
        String rightImage = String.format("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/enemy%dRight.png", enemyLevel);

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
    
    public static void moveEnemy(Enemy enemy, Scene scene,Pane pane) {
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
//        if (currentX < -enemy.getImageView().getFitWidth()) {
//            // Respawn on the right side
//            currentX = scene.getWidth();
//            currentY = Math.random() * (scene.getHeight() - enemy.getImageView().getFitHeight()); // Random Y within bounds
//        } else if (currentX > scene.getWidth()) {
//            // Respawn on the left side
//            currentX = -enemy.getImageView().getFitWidth();
//            currentY = Math.random() * (scene.getHeight() - enemy.getImageView().getFitHeight()); // Random Y within bounds
//        }
//
//        // Set the new position of the enemy
//        enemy.setX(currentX);
//        enemy.setY(currentY);

        if (currentX < -enemy.getImageView().getFitWidth() || currentX > scene.getWidth()) {
            // Remove the enemy from the pane
            pane.getChildren().remove(enemy.getImageView());
        } else {
            // Update the position of the enemy
            enemy.setX(currentX);
            enemy.setY(currentY);
        }
        
        // Update the position of the enemy's image view
        enemy.getImageView().setX(currentX);
        enemy.getImageView().setY(currentY);
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move(double deltaX, double deltaY) {
        imageView.setX(imageView.getX() + deltaX);
        imageView.setY(imageView.getY() + deltaY);
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }

    public void moveHorizontally() {
        if ("RIGHT".equals(direction)) {
            setX(getX() + speed); // Move right
        } else if ("LEFT".equals(direction)) {
            setX(getX() - speed); // Move left
        }
    }

    // Method to update the image based on direction
    private void updateImage() {
        if ("RIGHT".equals(direction)) {
            imageView.setImage(new Image(rightImagePath)); // Image for moving right
        } else if ("LEFT".equals(direction)) {
            imageView.setImage(new Image(leftImagePath)); // Image for moving left
        }
    }
}
