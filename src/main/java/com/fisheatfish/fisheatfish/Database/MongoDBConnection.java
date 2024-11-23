/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Database;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
/**
 *
 * @author A S U S
 */
public class MongoDBConnection {
   private static final String URI = "mongodb://localhost:27017"; // MongoDB URI (update if necessary)
    private static final String DB_NAME = "FishEatFish"; // Database name

    public static MongoDatabase connectToDatabase() {
        MongoClient mongoClient = MongoClients.create(URI);
        return mongoClient.getDatabase(DB_NAME);
    }
}
