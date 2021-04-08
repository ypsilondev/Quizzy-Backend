package de.ypsilon.quizzy.database.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class QuestionCodec implements Codec<QuestionCodec> {

    public static final QuestionCodec EMPTY_CODEC = new QuestionCodec(null, null, null, null, null);

    private final ObjectId _id;
    private final String question;
    private final String correctAnswer;
    private final List<String> wrongAnswers;
    private final String questionCategoryUuid;

    public QuestionCodec(ObjectId _id, String question, String correctAnswer, List<String> wrongAnswers, String questionCategoryUuid) {
        this._id = _id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.questionCategoryUuid = questionCategoryUuid;
    }

    @Override
    public QuestionCodec decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        ObjectId _id = reader.readObjectId("_id");
        String question = reader.readString("question");
        String correctAnswer = reader.readString("correctAnswer");
        reader.readName("wrongAnswers");
        reader.readStartArray();
        List<String> wrongAnswers = new ArrayList<>();
        while(reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            wrongAnswers.add(reader.readString());
        }
        reader.readEndArray();
        String questionCategoryUuid = reader.readString("questionCategoryUuid");
        reader.readEndDocument();
        return new QuestionCodec(_id, question, correctAnswer, wrongAnswers, questionCategoryUuid);
    }

    @Override
    public void encode(BsonWriter writer, QuestionCodec questionCodec, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeObjectId("_id", this._id);
        writer.writeString("question", this.question);
        writer.writeString("correctAnswer", this.correctAnswer);

        writer.writeStartArray("wrongAnswers");
        wrongAnswers.forEach(writer::writeString);
        writer.writeEndArray();

        writer.writeString("questionCategoryUuid", this.questionCategoryUuid);
        writer.writeEndDocument();
    }

    @Override
    public Class<QuestionCodec> getEncoderClass() {
        return QuestionCodec.class;
    }
}
