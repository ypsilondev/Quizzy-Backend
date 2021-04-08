package de.ypsilon.quizzy.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Database manager used to initialize the MongoDB database and register all used codecs.
 *
 * @version 1.0
 */
public class DatabaseManager {

    private final CodecRegistry codecRegistry;
    private final MongoClient client;
    private final MongoDatabase database;

    /**
     * Generate a new database instance.
     */
    public DatabaseManager() {
        // get all required env variables
        String hostName = System.getenv("mongo.host");
        int port = Integer.parseInt(System.getenv("mongo.port"));
        String username = System.getenv("mongo.username");
        String authDatabase = System.getenv("mongo.authDatabase");
        String password = System.getenv("mongo.password");
        String defaultDatabase = System.getenv("mongo.database");

        // generate the credentials.
        MongoCredential credential = MongoCredential.createCredential(username, authDatabase, password.toCharArray());

        // get all new codecs and the default codec
        CodecRegistry extraCodecs = CodecRegistries.fromCodecs(registerAdditionalCodecs());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();

        // merge both codecs together
        this.codecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, extraCodecs);

        // initialize the settings for the client
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(hostName, port))))
                .codecRegistry(this.codecRegistry)
                .build();

        // register the client and set the default database
        this.client = MongoClients.create(settings);
        this.database = this.client.getDatabase(defaultDatabase);
    }

    /**
     * Register all new codecs in the project and return them for initialization in the the client.
     *
     * @return a list with all codecs used in the project.
     */
    private List<Codec<?>> registerAdditionalCodecs() {
        List<Codec<?>> codecs = new LinkedList<>();

        return codecs;
    }

    /**
     * Get the codec registry used from the client.
     *
     * @return the running codec registry.
     */
    public CodecRegistry getCodecRegistry() {
        return this.codecRegistry;
    }

    /**
     * Get the running mongo client.
     *
     * @return a mongo client.
     */
    public MongoClient getClient() {
        return client;
    }

    /**
     * Get the default database.
     *
     * @return the database.
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}
