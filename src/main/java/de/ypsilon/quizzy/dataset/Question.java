package de.ypsilon.quizzy.dataset;

import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class models a question in the game
 */
public class Question {

    static final int ANSWER_COUNT = 4;

    private final QuestionCategory questionCategory;
    private final String question;
    private final String correctAnswer;
    private final List<String> wrongAnswers;

    private final ObjectId questionId;

    /**
     * Creates a question with all attributes
     * @param questionCategory the category of the question (e.g. GBI or LA)
     * @param question the question-text itself
     * @param correctAnswer the correct answer to the question
     * @param wrongAnswers the wrong answers to the question
     */
    public Question(QuestionCategory questionCategory, String question, String correctAnswer, String... wrongAnswers) {
        if(wrongAnswers.length != ANSWER_COUNT - 1) {
            throw new IllegalArgumentException(String.format("A question has exactly %d possible wrong answers.", ANSWER_COUNT));
        }
        this.questionCategory = questionCategory;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = Arrays.asList(wrongAnswers);
        // TODO FIXME  Find the correct id by db-lookup
        this.questionId = new ObjectId();
    }

    /**
     * Getter for the question-text
     * @return the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Getter for the text of the correct answer
     * @return the text
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Getter for a {@link Collection} containing all the wrong answers to the question
     * @return the wrong anwers
     */
    public Collection<String> getWrongAnswers() {
        return wrongAnswers;
    }

    /**
     * Getter for the question's {@link java.util.UUID}
     * @return the uuid of the question
     */
    public ObjectId getQuestionId() {
        return questionId;
    }
}
