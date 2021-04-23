package de.ypsilon.quizzy.dataset.question;

import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.dataset.Match;
import de.ypsilon.quizzy.exception.QuizzyExcepton;
import org.json.JSONObject;

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
    private boolean answered;

    /**
     * Creates a servable question
     *
     * @param question the question to be served
     */
    public ServableQuestion(Question question) {
        this.referenceId = UUID.randomUUID();
        this.question = question;
        this.correctAnswerId = random.nextInt(Question.ANSWER_COUNT);
        this.answered = false;
    }

    /**
     * Creates an answer-list in a (pseudo-)random order. However this order is defined since the constructor was called
     * so this function will always return the same {@link Collection} when called on the same object.
     *
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
     *
     * @return the {@link UUID}
     */
    public UUID getReferenceId() {
        return referenceId;
    }

    /**
     * Getter for the question being served.
     *
     * @return the served question
     */
    public Question getQuestion() {
        return question;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public JSONObject asJson() {
        return QuizzyBackend.getQuizzyBackend().getJsonCodecManager().getEncoder(ServableQuestion.class).encode(this);
    }

    public boolean answer(int answerId) throws QuizzyExcepton {
        if (this.answered) {
            throw new QuizzyExcepton("This question was already answered!");
        }
        this.answered = true;
        return this.correctAnswerId == answerId;
    }

}
