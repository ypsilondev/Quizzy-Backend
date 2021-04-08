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

        return json;
    }

    @Override
    public Class<ServableQuestion> getEncoderClass() {
        return ServableQuestion.class;
    }
}
