package de.ypsilon.quizzy.exception;

public class UserAuthenticationException extends QuizzyWebException{

    public UserAuthenticationException(String msg) {
        super(msg);
    }
}
