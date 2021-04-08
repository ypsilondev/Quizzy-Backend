package de.ypsilon.quizzy;

import de.ypsilon.quizzy.util.EnvironmentVariableWrapper;

import java.util.Arrays;

public class BackendMain {

    public static void main(String[] args) {
        new EnvironmentVariableWrapper().parseVariables(args);

        QuizzyBackend quizzyBackend = new QuizzyBackend();
        quizzyBackend.initialize();
    }

}
