package de.ypsilon.quizzy.web.routes.questions;

import de.ypsilon.quizzy.dataset.question.Question;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionRoute implements Route {

    @Override
    public String getPath() {
        return "/questions/add";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        JSONObject json = new JSONObject(context.body());


        String questionCategoryId = json.getString("questionCategory");
        String questionString = json.getString("question");
        String correctAnswer = json.getString("correctAnswer");

        List<String> wrongAnswers = new ArrayList<>();
        json.getJSONArray("wrongAnswers").forEach(wrongAnswer->wrongAnswers.add((String) wrongAnswer));

        List<String> imagesIds = new ArrayList<>();
        json.getJSONArray("images").forEach(image->wrongAnswers.add((String) image));

        String timeToAnswerString = "-1";
        if (!json.isNull("timeToAnswer")) {
            timeToAnswerString = json.getString("timeToAnswer");
        }

        RouteUtil.requireAllNotNull(questionCategoryId, questionString, correctAnswer, wrongAnswers, imagesIds);
        ObjectId questionCategory = new ObjectId(questionCategoryId);
        List<ObjectId> images = new ArrayList<>();
        imagesIds.forEach(imageId -> images.add(new ObjectId(imageId)));
        Question question = new Question(questionCategory, questionString, correctAnswer, wrongAnswers, images, RouteUtil.getInt(timeToAnswerString));

        RouteUtil.sendSuccessMessage(context);
        question.save();
    }
}
