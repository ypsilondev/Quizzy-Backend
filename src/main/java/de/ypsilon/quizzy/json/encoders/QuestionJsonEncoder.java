package de.ypsilon.quizzy.json.encoders;

import de.ypsilon.quizzy.dataset.Question;
import de.ypsilon.quizzy.json.JsonEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionJsonEncoder implements JsonEncoder<Question> {

    private static final String QUESTION = "question";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String WRONG_ANSWERS = "wrongAnswers";

    /**
     * Encode a object to the JSON value.
     *
     * @param data the object to encode.
     */
    @Override
    public JSONObject encode(Question data) {
        JSONObject json = new JSONObject();

        json.put(QUESTION, data.getQuestion());
        json.put(CORRECT_ANSWER, data.getCorrectAnswer());

        JSONArray wrongAnswers = new JSONArray();
        for (String wrongAnswer : data.getWrongAnswers()) {
            wrongAnswers.put(wrongAnswer);
        }
        json.put(WRONG_ANSWERS, wrongAnswers);

        return json;
    }

    /**
     * The class that is going to be encoded.
     *
     * @return a class.
     */
    @Override
    public Class<Question> getEncoderClass() {
        return Question.class;
    }
}
