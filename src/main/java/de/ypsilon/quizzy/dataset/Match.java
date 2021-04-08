package de.ypsilon.quizzy.dataset;

import de.ypsilon.quizzy.dataset.question.QuestionCategory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * This class models a match between a variable amount of players.
 */
public class Match {

    private static final int QUESTION_COUNT = 10;
    private static UUID matchId;

    private List<User> participatingUsers;
    private QuestionCategory questionCategory;

    private HashMap<User, Integer> scores;





}
