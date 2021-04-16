package de.ypsilon.quizzy.dataset;

import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.dataset.question.Question;
import de.ypsilon.quizzy.dataset.question.QuestionCategory;
import de.ypsilon.quizzy.dataset.question.ServableQuestion;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.QuizzyExcepton;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This class models a match between a variable amount of players.
 */
public class Match {

    private static final int QUESTION_COUNT = 10;
    private static final int MAX_PLAYER_COUNT = 100;
    private static final int POINTS_PER_WIN = 1;

    private QuestionCategory questionCategory;
    private UUID matchId;

    private HashMap<User, Integer> questionIndex;
    private HashMap<User, ServableQuestion> servedQuestions;
    private List<Question> questions;

    private Set<User> participatingUsers;

    private HashMap<User, Integer> scores;

    public Match() {
        this.matchId = UUID.randomUUID();
        this.questionIndex = new HashMap<>();
        this.participatingUsers = new HashSet<>();
        this.scores = new HashMap<>();
        this.servedQuestions = new HashMap<>();
        this.loadQuestions();
        QuizzyBackend.getQuizzyBackend().getMatchManager().addMatch(this);
    }

    public void addUser(User user) {
        if (this.participatingUsers.size() < MAX_PLAYER_COUNT) {
            if (this.participatingUsers.add(user)) {
                this.questionIndex.put(user, 0);
                this.scores.put(user, 0);
            }
        }
    }

    public boolean isFinished() {
        return this.questionIndex.values().stream().allMatch(index -> index == QUESTION_COUNT);
    }

    private void loadQuestions() {
        // TODO fixme!!!!
        this.questions = new ArrayList<>();
    }

    private Question getCurrentQuestion(User user) throws QuizzyExcepton {
        if (this.questionIndex.containsKey(user)) {
            return this.questions.get(this.questionIndex.get(user));
        } else {
            throw new QuizzyExcepton(String.format("The user %s is not a participant in match %s", user, this.matchId.toString()));
        }
    }

    public ServableQuestion serveCurrentQuestion(User user) throws QuizzyExcepton {
        Question currentQuestion = this.getCurrentQuestion(user);
        if (this.servedQuestions.get(user).getQuestion().equals(currentQuestion)) {
            return this.servedQuestions.get(user);
        } else {
            ServableQuestion sq = new ServableQuestion(currentQuestion);
            this.servedQuestions.put(user, sq);
            return sq;
        }
    }

    public boolean submitQuestion(User user, int answerId) throws QuizzyExcepton {
        ServableQuestion servedQuestion = this.serveCurrentQuestion(user);
        this.questionIndex.merge(user, 1, Integer::sum);
        if (servedQuestion.getCorrectAnswerId() == answerId) {
            this.addScore(user, POINTS_PER_WIN);
            return true;
        } else {
            return false;
        }
    }

    private void addScore(User user, int amount) {
        this.scores.merge(user, amount, Integer::sum);
    }

    public UUID getMatchId() {
        return matchId;
    }

    public Set<User> getParticipatingUsers() {
        return participatingUsers;
    }

    public JSONObject asJson() {
        return QuizzyBackend.getQuizzyBackend().getJsonCodecManager().getEncoder(Match.class).encode(this);
    }
}
