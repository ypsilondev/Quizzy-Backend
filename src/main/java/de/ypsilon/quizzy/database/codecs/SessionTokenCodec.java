package de.ypsilon.quizzy.database.codecs;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class SessionTokenCodec implements Codec<SessionToken> {

    public static final String ID_KEY = "_id";
    public static final String USER_ID_KEY = "userId";
    public static final String TOKEN_KEY = "sessionToken";

    @Override
    public SessionToken decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        ObjectId _id = reader.readObjectId(ID_KEY);
        ObjectId userId = reader.readObjectId(USER_ID_KEY);
        String sessionToken = reader.readString(TOKEN_KEY);
        reader.readEndDocument();
        return new SessionToken(_id, userId, sessionToken);
    }

    @Override
    public void encode(BsonWriter writer, SessionToken sessionToken, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeObjectId(ID_KEY, sessionToken.getId());
        writer.writeObjectId(USER_ID_KEY, sessionToken.getUserId());
        writer.writeString(TOKEN_KEY, sessionToken.getTokenString());
        writer.writeEndDocument();
    }

    @Override
    public Class<SessionToken> getEncoderClass() {
        return SessionToken.class;
    }
}
