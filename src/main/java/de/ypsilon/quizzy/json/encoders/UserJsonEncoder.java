package de.ypsilon.quizzy.json.encoders;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.json.JsonEncoder;
import org.bson.BsonBinary;
import org.json.JSONObject;

public class UserJsonEncoder implements JsonEncoder<User> {

    public static final String ID_KEY = "_id";
    public static final String DISPLAY_NAME_KEY = "displayName";
    public static final String EMAIL_KEY = "email";
    private static final String PROFILE_IMAGE_KEY = "profileImage";
    private static final String ACCOUNT_SETTINGS_KEY = "accountSettings";
    private static final String SCORE_KEY = "totalScore";
    private static final String PERMISSION_KEY = "permissions";
    private static final String VERIFIED_KEY = "verified";
    private static final String HASHED_PW_KEY = "hashedPassword";
    private static final String SALT_KEY = "salt";
    

    @Override
    public JSONObject encode(User user) {
        JSONObject json = new JSONObject();

        json.put(ID_KEY, user.getId().toString());
        json.put(DISPLAY_NAME_KEY, user.getDisplayName());
        json.put(PROFILE_IMAGE_KEY, user.getProfileImage().toString());
        json.put(SCORE_KEY, user.getTotalScore());

        return json;
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}
