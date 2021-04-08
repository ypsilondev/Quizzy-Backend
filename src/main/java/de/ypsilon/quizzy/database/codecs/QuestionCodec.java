package de.ypsilon.quizzy.database.codecs;

import de.ypsilon.quizzy.util.DatabaseUtil;
import de.ypsilon.quizzy.dataset.question.Question;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.List;

public class QuestionCodec implements Codec<Question> {

    public static final String ID_KEY = "_id";
    private static final String QUESTION_CATEGORY_KEY = "questionCategory";
    private static final String QUESTION_KEY = "question";
    private static final String CORRECT_ANSWER_KEY = "correctAnswer";
    private static final String WRONG_ANSWERS_KEY = "wrongAnswers";
    private static final String IMAGES_KEY = "images";

    @Override
    public Question decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        ObjectId id = reader.readObjectId(ID_KEY);
        ObjectId questionCategory = reader.readObjectId(QUESTION_CATEGORY_KEY);
        String question = reader.readString(QUESTION_KEY);
        String correctAnswer = reader.readString(CORRECT_ANSWER_KEY);
        List<String> wrongAnswers = DatabaseUtil.readArray(reader, decoderContext, String.class);
        List<ObjectId> images = DatabaseUtil.readArray(reader, decoderContext, ObjectId.class);
        reader.readEndDocument();

        return new Question(id, questionCategory, question, correctAnswer, wrongAnswers, images);
    }

    @Override
    public void encode(BsonWriter writer, Question question, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeObjectId(ID_KEY, question.getIdentity());
        writer.writeObjectId(QUESTION_CATEGORY_KEY, question.getQuestionCategoryIdentity());
        writer.writeString(QUESTION_KEY, question.getQuestionString());
        writer.writeString(CORRECT_ANSWER_KEY, question.getCorrectAnswer());
        DatabaseUtil.writeArray(writer, encoderContext, WRONG_ANSWERS_KEY, question.getWrongAnswers(), String.class);
        DatabaseUtil.writeArray(writer, encoderContext, IMAGES_KEY, question.getImages(), ObjectId.class);

        writer.writeEndDocument();
    }

    @Override
    public Class<Question> getEncoderClass() {
        return Question.class;
    }
}
