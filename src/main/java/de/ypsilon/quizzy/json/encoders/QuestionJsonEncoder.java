package de.ypsilon.quizzy.json.encoders;

import de.ypsilon.quizzy.dataset.question.Question;
import de.ypsilon.quizzy.json.JsonEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionJsonEncoder implements JsonEncoder<Question> {

    private static final String QUESTION = "question";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String WRONG_ANSWERS = "wrongAnswers";
    private static final String TIME_TO_ANSWER = "timeToAnswer";

    /**
     * Encode a object to the JSON value.
     *
     * @param data the object to encode.
     */
    @Override
    public JSONObject encode(Question data) {
        JSONObject json = new JSONObject();

        json.put(QUESTION, data.getQuestionString());
        json.put(CORRECT_ANSWER, data.getCorrectAnswer());

        JSONArray wrongAnswers = new JSONArray();
        data.getWrongAnswers().forEach(wrongAnswers::put);
        json.put(WRONG_ANSWERS, wrongAnswers);

        json.put(TIME_TO_ANSWER, data.getTimeToAnswer());

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
