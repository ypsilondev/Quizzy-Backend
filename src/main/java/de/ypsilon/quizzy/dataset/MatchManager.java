package de.ypsilon.quizzy.dataset;

import de.ypsilon.quizzy.exception.QuizzyExcepton;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MatchManager {

    private static MatchManager instance;
    private final Set<Match> matches;


    public MatchManager() {
        if (instance == null) {
            instance = this;
            this.matches = new HashSet<>();
        } else {
            throw new IllegalAccessError("Already instantiated!");
        }
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }

    public Match getMatchById(UUID matchId) throws QuizzyExcepton {
        return this.matches.stream()
                .filter(match -> match.getMatchId().equals(matchId))
                .findFirst()
                .orElseThrow(() -> new QuizzyExcepton("Found no match with the given id."));
    }


    public static MatchManager getInstance() {
        return instance;
    }
}
