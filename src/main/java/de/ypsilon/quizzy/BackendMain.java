package de.ypsilon.quizzy;

import de.ypsilon.quizzy.util.EnvironmentVariablesUtil;

public class BackendMain {

    public static void main(String[] args) {
        new EnvironmentVariablesUtil().parseVariables(args);

        QuizzyBackend quizzyBackend = new QuizzyBackend();
        quizzyBackend.initialize();
    }

}
