package de.ypsilon.quizzy.web.routes.dev;

import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class DevRoute implements Route {

    private static final String ADD_ACCOUNT_FORM = "<h1>Register User:</h1><form target=\"_blank\" method=\"post\" onsubmit=\"submitForm(this); return false;\" action=\"/users/register\"> <input type=\"text\" name=\"displayName\" placeholder=\"User-Name\"> <input type=\"text\" name=\"email\" placeholder=\"E-Mail\"> <input type=\"text\" name=\"password\" placeholder=\"Password\"> <input type=\"text\" name=\"profileImage\" placeholder=\"Profile-Image-id\" value=\"606f801770402029ae887153\"> <input type=\"submit\"></form> <br />";
    private static final String VERIFY_ACCOUNT_FORM = "<h1>Verify User:</h1> Note: you need to have a session cookie!<form target=\"_blank\" method=\"get\" action=\"/users/security/verify\"> <input type=\"text\" name=\"verificationNumber\" placeholder=\"Verification-Number\"> <input type=\"submit\" value=\"Verifiy!\"></form> <br />";
    private static final String LOGIN_FORM = "<h1>Login User:</h1><form target=\"_blank\" method=\"post\" onsubmit=\"submitForm(this); return false;\" action=\"/users/authenticate\"> <input type=\"text\" name=\"loginName\" placeholder=\"Login-Name\"><input type=\"text\" name=\"password\" placeholder=\"Password\"> <input type=\"submit\"></form>";
    private static final String LOGIN_CHECK_FORM = "<h1>Check login-state</h1><form target=\"_blank\" method=\"get\" onsubmit=\"submitForm(this); return false;\" action=\"/users/logincheck\"> <input type=\"submit\" value=\"Check login state\"></form>";
    private static final String REVOKE_TOKENS_FORM = "<h1>Revoke local session-token</h1><form target=\"_blank\" method=\"post\" onsubmit=\"submitForm(this); return false;\" action=\"/users/security/revokeToken\"> <input type=\"submit\" value=\"Revoke token\"></form><h1>Revoke all session-tokens</h1><form target=\"_blank\" method=\"post\" onsubmit=\"submitForm(this); return false;\" action=\"/users/security/revokeAllTokens\"> <input type=\"submit\" value=\"Revoke all tokens\"></form>";

    private static final String DISPLAY_AREA = "Request:<br /><textarea id=\"requestDisplay\" style=\"width:80vw;\"></textarea><br />Response:<br /><textarea id=\"responseDisplay\" style=\"width:80vw;\"></textarea>";

    private static final String SERIALISATION_FORM = "<script>function submitForm(e){document.getElementById(\"requestDisplay\").value=\"\",document.getElementById(\"responseDisplay\").value=\"\";let t=serializeForm(e),n=e.action,l=e.method;var i=new XMLHttpRequest;i.open(l,n),i.setRequestHeader(\"Content-Type\",\"application/json;charset=UTF-8\"),i.addEventListener(\"load\",function(e){document.getElementById(\"responseDisplay\").value=i.responseText}),i.send(t),document.getElementById(\"requestDisplay\").value=t}function serializeForm(e){let t={};for(child of e.children)\"INPUT\"==child.tagName&&\"submit\"!=child.type&&(t[child.name]=child.value);let n=JSON.stringify(t);return console.log(n),n}</script>";

    @Override
    public String getPath() {
        return "/dev";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.html(ADD_ACCOUNT_FORM + VERIFY_ACCOUNT_FORM + LOGIN_FORM + LOGIN_CHECK_FORM + REVOKE_TOKENS_FORM + DISPLAY_AREA + SERIALISATION_FORM);
    }
}
