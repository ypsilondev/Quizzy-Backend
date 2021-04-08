package de.ypsilon.quizzy.dataset;

import java.util.HashMap;
import java.util.UUID;

public class QuestionController {

    private final HashMap<UUID, ServableQuestion> servedQuestions = new HashMap<>();

    public ServableQuestion serveQuestion(Question question, User user) {
        ServableQuestion servedQuestion = new ServableQuestion(question);
        this.servedQuestions.put(servedQuestion.getReferenceId(), servedQuestion);
        return servedQuestion;
    }

    public Question getQuestionByUuid(UUID questionId) {
        // TODO load question from DB
        return null;
    }

}
