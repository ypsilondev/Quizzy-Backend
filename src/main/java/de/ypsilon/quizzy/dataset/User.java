package de.ypsilon.quizzy.dataset;

import com.mongodb.client.MongoCollection;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.database.codecs.UserCodec;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * This class models a User of the app. (Also player)
 */
public class User {

    private final ObjectId _id;
    private final String displayName;
    private final String email;
    private final int totalScore;
    private final int permissions;
    private final boolean verified;
    private final byte[] hashedPassword;
    private final byte[] salt;

    public User(ObjectId _id, String displayName, String email, int totalScore, int permissions, boolean verified, byte[] hashedPassword, byte[] salt) {
        this._id = _id;
        this.displayName = displayName;
        this.email = email;
        this.totalScore = totalScore;
        this.permissions = permissions;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.verified = verified;
    }

    public static User getUserById(ObjectId _id) {
        return getCollection().find(new Document(UserCodec.ID_KEY, _id), User.class).first();
    }

    public static User getUserByDisplayName(String displayName) {
        return getCollection().find(new Document(UserCodec.DISPLAY_NAME_KEY, displayName), User.class).first();
    }

    public static void saveUser(User user) {
        getCollection().deleteMany(new Document(UserCodec.ID_KEY, user.getId()));
        getCollection().insertOne(user);
    }

    public void save() {
        User.saveUser(this);
    }

    private static MongoCollection<User> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("Users", User.class);
    }

    /* Boilerplate */

    public ObjectId getId() {
        return _id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getPermissions() {
        return permissions;
    }

    public boolean isVerified() {
        return verified;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }
}
