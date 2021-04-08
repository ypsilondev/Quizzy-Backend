package de.ypsilon.quizzy.web.routes.questions;

import de.ypsilon.quizzy.dataset.question.Question;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

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
        String questionCategoryId = context.formParam("questionCategory");
        String questionString = context.formParam("question");
        String correctAnswer = context.formParam("correctAnswer");
        List<String> wrongAnswers = context.formParams("wrongAnswers");
        List<String> imagesIds = context.formParams("images");

        if (allNotNull(questionCategoryId, questionString, correctAnswer, wrongAnswers, imagesIds)) {
            ObjectId questionCategory = new ObjectId(questionCategoryId);
            List<ObjectId> images = new ArrayList<>();
            imagesIds.forEach(imageId -> images.add(new ObjectId(imageId)));
            Question question = new Question(questionCategory, questionString, correctAnswer, wrongAnswers, images);

            context.html(SUCCESS_JSON);
            question.save();
        } else {
            failRequest(context);
            return;
        }
    }
}
