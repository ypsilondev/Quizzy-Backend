package de.ypsilon.quizzy;

import de.ypsilon.quizzy.util.EnvironmentVariableUtil;

public class BackendMain {

    public static void main(String[] args) {
        new EnvironmentVariableUtil().parseVariables(args);

        QuizzyBackend quizzyBackend = new QuizzyBackend();
        quizzyBackend.initialize();
    }

}
