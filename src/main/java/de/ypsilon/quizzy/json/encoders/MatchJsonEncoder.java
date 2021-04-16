package de.ypsilon.quizzy.json.encoders;

import de.ypsilon.quizzy.dataset.Match;
import de.ypsilon.quizzy.json.JsonEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class MatchJsonEncoder implements JsonEncoder<Match> {

    private static final String MATCH_ID_KEY = "matchId";
    private static final String USERS_KEY = "players";


    @Override
    public JSONObject encode(Match data) {
        JSONObject json = new JSONObject();
        json.put(MATCH_ID_KEY, data.getMatchId().toString());
        JSONArray users = new JSONArray();
        data.getParticipatingUsers().forEach(user -> users.put(user.asJson()));
        json.put(USERS_KEY, users);

        return json;
    }

    @Override
    public Class<Match> getEncoderClass() {
        return Match.class;
    }
}
