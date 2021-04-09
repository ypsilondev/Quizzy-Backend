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

    /**
     * Constructor of a {@link VerificationCode} - creates one with all attributes
     *
     * @param _id       the {@link ObjectId} of the code
     * @param userId    the {@link ObjectId} of the {@link User} the code belongs to
     * @param createdAt the {@link Date} the code was created at
     * @param vn        the verification-number
     */
    public VerificationCode(ObjectId _id, ObjectId userId, Date createdAt, int vn) {
        this._id = _id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.verificationNumber = vn;
    }

    /**
     * Creates a new verification-code for a user
     *
     * @param user the user the code should be created for
     * @return the new verification-code
     */
    public static VerificationCode createVerificationCode(User user) {
        VerificationCode vc = new VerificationCode(new ObjectId(), user.getId(), new Date(), getRandomVerificationNumber());
        vc.save();
        return vc;
    }

    /**
     * Verifies a user by a verification-number
     *
     * @param user               the user to be verified
     * @param verificationNumber the verification-number supplied
     * @throws QuizzyWebException thrown, when the verification failed.
     */
    public static void verifyUser(User user, int verificationNumber) throws QuizzyWebException {
        if (user.isVerified()) {
            throw new QuizzyWebException("User ist already verified!");
        }
        VerificationCode vc = getCollection().find(new Document(VerificationCodeCodec.USER_ID_KEY, user.getId())).filter(new Document(VerificationCodeCodec.VERIFICATION_CODE_KEY, verificationNumber)).first();
        if (vc == null) {
            throw new UserAuthenticationException("invalid verification-number for this user");
        }
        if (new Date().getTime() - vc.getCreatedAt().getTime() < verificationExpireTime) {
            user.setVerified(true).save();
            getCollection().deleteMany(new Document(VerificationCodeCodec.USER_ID_KEY, user.getId()));
        } else {
            getCollection().deleteMany(new Document(VerificationCodeCodec.ID_KEY, vc.getId()));
            throw new UserAuthenticationException("verification-key expired.");
        }
    }

    /**
     * Saves a {@link VerificationCode} to the database
     *
     * @param vc the {@link VerificationCode} to be saved
     */
    public static void saveVerificationCode(VerificationCode vc) {
        getCollection().deleteMany(new Document(VerificationCodeCodec.ID_KEY, vc.getId()));
        getCollection().insertOne(vc);
    }

    /**
     * Saves this {@link VerificationCode} to the database
     */
    public void save() {
        VerificationCode.saveVerificationCode(this);
    }

    /**
     * Creates a (pseudo-)random 6-digit number, however it will never start with 0.
     *
     * @return the number
     */
    private static int getRandomVerificationNumber() {
        return random.nextInt(900000) + 100000;
    }

    /**
     * Getter for the {@link MongoCollection} containing all {@link VerificationCode}s
     *
     * @return the collection
     */
    private static MongoCollection<VerificationCode> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("VerificationCodes", VerificationCode.class);
    }

    /**
     * The object-id of this {@link VerificationCode}
     *
     * @return the object-id
     */
    public ObjectId getId() {
        return _id;
    }

    /**
     * Getter for the id of the {@link User} this {@link VerificationCode} belongs to.
     *
     * @return the user-id
     */
    public ObjectId getUserId() {
        return userId;
    }

    /**
     * Getter for the time of creation of this code
     *
     * @return the {@link Date}
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter for the verification-number
     *
     * @return the verification-number
     */
    public int getVerificationNumber() {
        return verificationNumber;
    }
}
