package de.ypsilon.quizzy;

import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.web.WebManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuizzyBackend {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuizzyBackend.class.getName());

    public QuizzyBackend() {
        new DatabaseManager();
        new JsonCodecManager();
        new WebManager();
    }
}
