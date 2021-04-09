package de.ypsilon.quizzy.json.encoders;

import de.ypsilon.quizzy.dataset.question.ServableQuestion;
import de.ypsilon.quizzy.json.JsonEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServableQuestionJsonEncoder implements JsonEncoder<ServableQuestion> {

    private static final String QUESTION = "question";
    private static final String ANSWERS = "answers";
    private static final String IMAGES = "images";
    private static final String REFERENCE = "referenceId";
    private static final String TIME_TO_ANSWER = "timeToAnswer";

    /**
     * Encode a object to the JSON value.
     *
     * @param data the object to encode.
     */
    @Override
    public JSONObject encode(ServableQuestion data) {
        JSONObject json = new JSONObject();

        json.put(QUESTION, data.getQuestion().getQuestionString());

        JSONArray answers = new JSONArray();
        data.getAnswerList().forEach(answers::put);
        json.put(ANSWERS, answers);

        JSONArray images = new JSONArray();
        data.getQuestion().getImages().forEach(images::put);
        json.put(IMAGES, images);

        json.put(REFERENCE, data.getReferenceId().toString());

        json.put(TIME_TO_ANSWER, data.getQuestion().getTimeToAnswer());

        return json;
    }

    /**
     * The class that is going to be encoded.
     *
     * @return a class.
     */
    @Override
    public Class<ServableQuestion> getEncoderClass() {
        return ServableQuestion.class;
    }
}
