package de.ypsilon.quizzy.dataset.question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * This class makes it possible to serve {@link Question}s to the players
 */
public class ServableQuestion {
    private static final Random random = new Random();

    private final UUID referenceId;
    private final Question question;
    private final int correctAnswerId;

    /**
     * Creates a servable question
     * @param question the question to be served
     */
    public ServableQuestion(Question question) {
        this.referenceId = UUID.randomUUID();
        this.question = question;
        this.correctAnswerId = random.nextInt(Question.ANSWER_COUNT);
    }

    /**
     * Creates an answer-list in a (pseudo-)random order. However this order is defined since the constructor was called
     * so this function will always return the same {@link Collection} when called on the same object.
     * @return a {@link Collection} containing all answers.
     */
    public Collection<String> getAnswerList() {
        List<String> output = new ArrayList<>();
        List<String> wrongAnswers = new ArrayList<>(this.question.getWrongAnswers());
        Collections.shuffle(wrongAnswers);
        for (int i = 0; i < this.correctAnswerId; i++) {
            output.add(wrongAnswers.get(i));
        }
        output.add(this.question.getCorrectAnswer());
        for (int i = this.correctAnswerId; i < Question.ANSWER_COUNT - 1; i++) {
            output.add(wrongAnswers.get(i));
        }
        return output;
    }

    /**
     * Getter for the {@link UUID} of this {@link ServableQuestion}
     * @return the {@link UUID}
     */
    public UUID getReferenceId() {
        return referenceId;
    }

    /**
     * Getter for the question being served.
     * @return the served question
     */
    public Question getQuestion() {
        return question;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }
}
