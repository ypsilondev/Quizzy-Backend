package de.ypsilon.quizzy.database.codecs;

import de.ypsilon.quizzy.dataset.user.VerificationCode;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.Date;

public class VerificationCodeCodec implements Codec<VerificationCode> {

    public static final String ID_KEY = "_id";
    public static final String USER_ID_KEY = "userId";
    public static final String CREATED_AT_KEY = "createdAt";
    public static final String VERIFICATION_CODE_KEY = "verificationCode";

    @Override
    public VerificationCode decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        ObjectId _id = reader.readObjectId(ID_KEY);
        ObjectId userId = reader.readObjectId(USER_ID_KEY);
        Date createdAt = new Date(reader.readDateTime(CREATED_AT_KEY));
        int verificationCodeNumber = reader.readInt32(VERIFICATION_CODE_KEY);

        reader.readEndDocument();
        return new VerificationCode(_id, userId, createdAt, verificationCodeNumber);
    }

    @Override
    public void encode(BsonWriter writer, VerificationCode verificationCode, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeObjectId(ID_KEY, verificationCode.getId());
        writer.writeObjectId(USER_ID_KEY, verificationCode.getUserId());
        writer.writeInt64(CREATED_AT_KEY, verificationCode.getCreatedAt().getTime());
        writer.writeInt32(VERIFICATION_CODE_KEY, verificationCode.getVerificationNumber());

        writer.writeEndDocument();
    }

    @Override
    public Class<VerificationCode> getEncoderClass() {
        return VerificationCode.class;
    }
}
