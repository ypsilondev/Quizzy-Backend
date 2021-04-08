package de.ypsilon.quizzy.dataset.user;

import com.mongodb.client.MongoCollection;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.database.codecs.UserCodec;
import de.ypsilon.quizzy.exception.UserCreationException;
import de.ypsilon.quizzy.util.CryptoUtil;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

/**
 * This class models a User of the app. (Also player)
 */
public class User {

    private final ObjectId _id;
    private String displayName;
    private String email;
    private int totalScore;
    private int permissions;
    private boolean verified;
    private byte[] hashedPassword;
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

    public static User createAndStoreUser(String displayName, String email, String cleartextPassword) throws UserCreationException {
        if(getUserByDisplayName(displayName) != null || getUserByEmail(email) != null){
            throw new UserCreationException("A user with this email or displayName does already exist!");
        }
        byte[] salt;
        try{
            salt = CryptoUtil.generateSalt();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e){
            throw new UserCreationException("No salt could be generated!");
        }

        User user = new User(new ObjectId(), displayName, email, 0, 0, false, null, salt);
        user.changePassword(cleartextPassword)
                .save();
        return user;
    }

    public boolean isValidPassword(String cleartextPassword) {
        return Arrays.equals(CryptoUtil.hash(cleartextPassword.getBytes(), this.salt), this.hashedPassword);
    }

    /**
     * Loads a user by its user-id
     * @param _id the it to be searched
     * @return the user with the id or null
     */
    public static User getUserById(ObjectId _id) {
        return getCollection().find(new Document(UserCodec.ID_KEY, _id), User.class).first();
    }

    /**
     * Loads a user by its display-name
     * @param displayName the display-name to be searched
     * @return the user with the display-name or null
     */
    public static User getUserByDisplayName(String displayName) {
        return getCollection().find(new Document(UserCodec.DISPLAY_NAME_KEY, displayName), User.class).first();
    }

    /**
     * Loads a user by its email-address
     * @param email the email to be searched
     * @return the user with the email or null
     */
    public static User getUserByEmail(String email) {
        return getCollection().find(new Document(UserCodec.EMAIL_KEY, email), User.class).first();
    }

    /**
     * Saves a user to the database
     * @param user the user to be saved
     */
    private static void saveUser(User user) {
        getCollection().deleteMany(new Document(UserCodec.ID_KEY, user.getId()));
        getCollection().insertOne(user);
    }

    /**
     * Saves this user to the database
     */
    public void save() {
        User.saveUser(this);
    }

    /**
     * Getter for the {@link MongoCollection} containing all users
     * @return the collection
     */
    private static MongoCollection<User> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("Users", User.class);
    }


    /**
     * Getter for the {@link ObjectId} of the {@link User}
     * @return the {@link ObjectId}
     */
    public ObjectId getId() {
        return _id;
    }

    /**
     * Getter for the display name
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Getter for the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter of the total score
     * @return the total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Getter for the permissions of the user
     * @return the permissions of the user
     */
    public int getPermissions() {
        return permissions;
    }

    /**
     * Getter for the account-verification of the user
     * @return true, if the account is verified, false if not
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Getter for the hashed password
     * @return the hashed password
     */
    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Getter for the salt
     * @return the salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Setter for the display name
     * @param displayName the new display name
     * @return this user
     */
    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Setter for the email
     * @param email the new email
     * @return this user
     */
    public User changeEmail(String email) {
        this.email = email;
        this.verified = false;
        return this;
    }

    /**
     * Setter for the total score of the user
     * @param totalScore the new total score
     * @return this user
     */
    public User setTotalScore(int totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    /**
     * Setter for the permissions of the user
     * @param permissions the new permissions
     * @return this user
     */
    public User setPermissions(int permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * Setter for the user's account verification
     * @param verified the new verification-level
     * @return this user
     */
    public User setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }

    /**
     * Setter for the password of the user
     * @param cleartextPassword the new password (will be hashed)
     * @return this user
     */
    public User changePassword(String cleartextPassword) {
        this.hashedPassword = CryptoUtil.hash(cleartextPassword.getBytes(), salt);
        return this;
    }
}
