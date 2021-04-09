package de.ypsilon.quizzy.database.codecs;

import de.ypsilon.quizzy.dataset.user.User;
import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class UserCodec implements Codec<User> {

    public static final String ID_KEY = "_id";
    public static final String DISPLAY_NAME_KEY = "displayName";
    public static final String EMAIL_KEY = "email";
    private static final String PROFILE_IMAGE_KEY = "profileImage";
    private static final String ACCOUNT_SETTINGS_KEY = "accountSettings";
    private static final String SCORE_KEY = "totalScore";
    private static final String PERMISSION_KEY = "permissions";
    private static final String VERIFIED_KEY = "verified";
    private static final String HASHED_PW_KEY = "hashedPassword";
    private static final String SALT_KEY = "salt";

    @Override
    public User decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        ObjectId _id = reader.readObjectId(ID_KEY);
        String displayName = reader.readString(DISPLAY_NAME_KEY);
        String email = reader.readString(EMAIL_KEY);
        ObjectId profileImage = reader.readObjectId(PROFILE_IMAGE_KEY);
        int accountSettings = reader.readInt32(ACCOUNT_SETTINGS_KEY);
        int totalScore = reader.readInt32(SCORE_KEY);
        int permissions = reader.readInt32(PERMISSION_KEY);
        boolean verified = reader.readBoolean(VERIFIED_KEY);
        byte[] hashedPassword = reader.readBinaryData(HASHED_PW_KEY).getData();
        byte[] salt = reader.readBinaryData(SALT_KEY).getData();

        reader.readEndDocument();
        return new User(_id, displayName, email, profileImage, accountSettings, totalScore, permissions, verified, hashedPassword, salt);
    }

    @Override
    public void encode(BsonWriter writer, User user, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeObjectId(ID_KEY, user.getId());
        writer.writeString(DISPLAY_NAME_KEY, user.getDisplayName());
        writer.writeString(EMAIL_KEY, user.getEmail());
        writer.writeObjectId(PROFILE_IMAGE_KEY, user.getProfileImage());
        writer.writeInt32(ACCOUNT_SETTINGS_KEY, user.getAccountSettings());
        writer.writeInt32(SCORE_KEY, user.getTotalScore());
        writer.writeInt32(PERMISSION_KEY, user.getPermissions());
        writer.writeBoolean(VERIFIED_KEY, user.isVerified());
        writer.writeBinaryData(HASHED_PW_KEY, new BsonBinary(user.getHashedPassword()));
        writer.writeBinaryData(SALT_KEY, new BsonBinary(user.getSalt()));

        writer.writeEndDocument();
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}
