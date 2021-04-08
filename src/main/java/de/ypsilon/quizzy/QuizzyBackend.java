package de.ypsilon.quizzy;

import de.ypsilon.quizzy.data.MinioManager;
import de.ypsilon.quizzy.database.DatabaseManager;
import de.ypsilon.quizzy.dataset.question.QuestionController;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.web.WebManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuizzyBackend {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuizzyBackend.class.getName());

    private static QuizzyBackend quizzyBackend;
    private boolean initialized = false;

    private WebManager webmanager;
    private DatabaseManager databaseManager;
    private MinioManager minioManager;
    private JsonCodecManager jsonCodecManager;

    public QuizzyBackend() {
        quizzyBackend = this;
    }

    public static QuizzyBackend getQuizzyBackend() {
        return quizzyBackend;
    }

    public void initialize() {
        if (initialized) {
            throw new UnsupportedOperationException("Double initialization is prohibited.");
        }
        initialized = true;
        this.jsonCodecManager = new JsonCodecManager();
        this.databaseManager = new DatabaseManager();
        // this.minioManager = new MinioManager();
        this.webmanager = new WebManager();
        new QuestionController();
    }

    public WebManager getWebManager() {
        return webmanager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public MinioManager getMinioManager() {
        return minioManager;
    }

    public JsonCodecManager getJsonCodecManager() {
        return jsonCodecManager;
    }

}
