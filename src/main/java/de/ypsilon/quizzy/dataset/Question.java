package de.ypsilon.quizzy.dataset;

import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Question {

    static final int ANSWER_COUNT = 4;

    private final String question;
    private final String correctAnswer;
    private final List<String> wrongAnswers;

    private final ObjectId questionId;

    public Question(String question, String correctAnswer, String... wrongAnswers) {
        if(wrongAnswers.length != ANSWER_COUNT - 1) {
            throw new IllegalArgumentException(String.format("A question has exactly %d possible wrong answers.", ANSWER_COUNT));
        }
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = Arrays.asList(wrongAnswers);
        // TODO FIXME  Find the correct id by db-lookup
        this.questionId = new ObjectId();
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public ObjectId getQuestionId() {
        return questionId;
    }
}
