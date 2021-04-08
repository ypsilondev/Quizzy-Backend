package de.ypsilon.quizzy.dataset;

import java.util.HashMap;
import java.util.UUID;

public class QuestionController {

    private HashMap<UUID, ServedQuestion> servedQuestions = new HashMap<>();

    public ServedQuestion serveQuestion(Question question, User user) {
        ServedQuestion servedQuestion = new ServedQuestion(question);
        this.servedQuestions.put(servedQuestion.getReferenceId(), servedQuestion);
        return servedQuestion;
    }

}
