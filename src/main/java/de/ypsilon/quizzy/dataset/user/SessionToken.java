package de.ypsilon.quizzy.dataset.user;

import com.mongodb.client.MongoCollection;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.database.codecs.SessionTokenCodec;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.UUID;

public class SessionToken {

    private final ObjectId _id;
    private final ObjectId userId;
    private final String sessionToken;

    public SessionToken(ObjectId _id, ObjectId userId, String sessionToken) {
        this._id = _id;
        this.userId = userId;
        this.sessionToken = sessionToken;
    }

    public static SessionToken createAndSaveSessionToken(User user) {
        SessionToken token = new SessionToken(new ObjectId(), user.getId(), UUID.randomUUID().toString());
        token.save();
        return token;
    }

    public static User retrieveUserFromSessionTokenString(String token){
        SessionToken sessionToken = getSessionTokenByString(token);
        if(sessionToken != null) {
            return User.getUserById(sessionToken.getUserId());
        } else {
            return null;
        }
    }

    public static SessionToken getSessionTokenByString(String token) {
        return getCollection().find(new Document(SessionTokenCodec.TOKEN_KEY, token)).first();
    }

    public static void revokeAllSessionTokens(User user) {
        getCollection().deleteMany(new Document(SessionTokenCodec.USER_ID_KEY, user.getId()));
    }

    public static void revokeSessionToken(SessionToken token) {
        getCollection().deleteMany(new Document(SessionTokenCodec.ID_KEY, token.getId()));
    }

    private static void saveSessionToken(SessionToken token) {
        revokeSessionToken(token);
        getCollection().insertOne(token);
    }

    public SessionToken save() {
        SessionToken.saveSessionToken(this);
        return this;
    }

    private static MongoCollection<SessionToken> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("SessionTokens", SessionToken.class);
    }

    public ObjectId getId() {
        return _id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public String getTokenString() {
        return sessionToken;
    }
}
