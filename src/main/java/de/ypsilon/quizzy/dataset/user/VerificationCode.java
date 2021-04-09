package de.ypsilon.quizzy.dataset.user;

import com.mongodb.client.MongoCollection;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.database.codecs.VerificationCodeCodec;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Random;

public class VerificationCode {

    private static final Random random = new Random();
    private static final long verificationExpireTime = 1000 * 60 * 60 * 24 * 7; // 7 days.

    private final ObjectId _id;
    private final ObjectId userId;
    private final Date createdAt;
    private final int verificationNumber;

    public VerificationCode(ObjectId _id, ObjectId userId, Date createdAt, int vn) {
        this._id = _id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.verificationNumber = vn;
    }

    public static VerificationCode createVerificationCode(User user) {
        VerificationCode vc = new VerificationCode(new ObjectId(), user.getId(), new Date(), getRandomVerificationNumber());
        vc.save();
        return vc;
    }

    public static void verifyUser(User user, int verificationNumber) throws QuizzyWebException {
        if(user.isVerified()){
            throw new QuizzyWebException("User ist already verified!");
        }
        VerificationCode vc = getCollection().find(new Document(VerificationCodeCodec.USER_ID_KEY, user.getId())).filter(new Document(VerificationCodeCodec.VERIFICATION_CODE_KEY, verificationNumber)).first();
        if (vc == null) {
            throw new UserAuthenticationException("invalid verification-number for this user");
        }
        if (new Date().getTime() - vc.getCreatedAt().getTime() < verificationExpireTime) {
            user.setVerified(true).save();
            getCollection().deleteMany(new Document(VerificationCodeCodec.USER_ID_KEY, user.getId()));
        }else{
            getCollection().deleteMany(new Document(VerificationCodeCodec.ID_KEY, vc.getId()));
            throw new UserAuthenticationException("verification-key expired.");
        }
    }

    public static void saveVerificationCode(VerificationCode vc) {
        getCollection().deleteMany(new Document(VerificationCodeCodec.ID_KEY, vc.getId()));
        getCollection().insertOne(vc);
    }

    public void save() {
        VerificationCode.saveVerificationCode(this);
    }

    private static int getRandomVerificationNumber() {
        return random.nextInt(900000) + 100000;
    }

    private static MongoCollection<VerificationCode> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("VerificationCodes", VerificationCode.class);
    }

    public ObjectId getId() {
        return _id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getVerificationNumber() {
        return verificationNumber;
    }
}
