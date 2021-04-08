package de.ypsilon.quizzy.dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ServedQuestion {
    private static final Random random = new Random();

    private final UUID referenceId;
    private final Question question;
    private final int correctAnswerId;

    public ServedQuestion(Question question) {
        this.referenceId = UUID.randomUUID();
        this.question = question;
        this.correctAnswerId = random.nextInt(Question.ANSWER_COUNT);
    }

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

    public UUID getReferenceId() {
        return referenceId;
    }
}
