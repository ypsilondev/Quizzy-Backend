package de.ypsilon.quizzy.dataset;

import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;

/**
 * This class models a question in the game
 */
public class Question {

    static final int ANSWER_COUNT = 4;

    private final ObjectId id;
    private final ObjectId questionCategory;
    private final String question;
    private final String correctAnswer;
    private final List<String> wrongAnswers;

    /**
     * Creates a question with all attributes
     * @param questionCategory the category of the question (e.g. GBI or LA)
     * @param question the question-text itself
     * @param correctAnswer the correct answer to the question
     * @param wrongAnswers the wrong answers to the question
     */
    public Question(ObjectId id, ObjectId questionCategory, String question, String correctAnswer, List<String> wrongAnswers) {
        this.id = id;
        if(wrongAnswers.size() != ANSWER_COUNT - 1) {
            throw new IllegalArgumentException(String.format("A question has exactly %d possible wrong answers.", ANSWER_COUNT));
        }
        this.questionCategory = questionCategory;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        // TODO FIXME  Find the correct id by db-lookup
    }

    /**
     * Get the {@link ObjectId} from the question in the database.
     * @return a {@link ObjectId}.
     */
    public ObjectId getIdentity() {
        return id;
    }

    /**
     * Get the {@link ObjectId} from the {@link QuestionCategory} that the question is located in.
     * @return a {@link ObjectId}
     */
    public ObjectId getQuestionCategoryIdentity() {
        return questionCategory;
    }

    /**
     * Fetch the Question category from the database.
     * @return a {@link QuestionCategory}
     */
    public QuestionCategory getQuestionCategory() {
        //TODO get the question category
        return null;
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

}
