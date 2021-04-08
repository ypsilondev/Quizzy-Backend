package de.ypsilon.quizzy.dataset.question;

import com.mongodb.client.MongoCollection;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.database.codecs.QuestionCodec;
import de.ypsilon.quizzy.dataset.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.UUID;

public class QuestionController {

    private static QuestionController instance;

    private final HashMap<UUID, ServableQuestion> servedQuestions = new HashMap<>();

    public QuestionController() {
        if (instance == null) {
            instance = this;
        }
    }

    public ServableQuestion serveQuestion(Question question, User user) {
        ServableQuestion servedQuestion = new ServableQuestion(question);
        this.servedQuestions.put(servedQuestion.getReferenceId(), servedQuestion);
        return servedQuestion;
    }

    public void saveQuestion(Question question) {
        getCollection().deleteMany(new Document(QuestionCodec.ID_KEY, question.getIdentity()));
        getCollection().insertOne(question);
    }

    public Question getQuestionById(ObjectId _id) {
        return getCollection().find(new Document(QuestionCodec.ID_KEY, _id)).first();
    }

    private static MongoCollection<Question> getCollection() {
        return DatabaseManager.getInstance().getDatabase().getCollection("Questions", Question.class);
    }

    public static QuestionController getInstance() {
        return instance;
    }
}
