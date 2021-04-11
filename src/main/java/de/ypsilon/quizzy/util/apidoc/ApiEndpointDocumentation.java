package de.ypsilon.quizzy.util.apidoc;

import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ApiEndpointDocumentation {

    private final String documentation;
    private final Class<? extends Route> clazz;

    public ApiEndpointDocumentation(Class<? extends Route> clazz) {
        this.clazz = clazz;
        this.documentation = buildDocumentationString();
    }

    private String buildDocumentationString() {
        if (clazz.isAnnotationPresent(DocumentedApiEndpoint.class)) {
            String out = "";

            out += buildHeadline();
            out += buildRequestParameters();
            out += buildExampleRequest(true);
            out += buildResponses();

            return out;
        }
        return "";
    }

    private Method getHandler() throws NoSuchMethodException {
        return clazz.getMethod("handle", Context.class);
    }

    private String buildHeadline() {
        String paragraphTemplate = "<p>%s</p>";
        try {
            if (clazz.isAnnotationPresent(DocumentedApiEndpoint.class)) {
                DocumentedApiEndpoint dae = clazz.getAnnotation(DocumentedApiEndpoint.class);
                Route route = clazz.getDeclaredConstructor().newInstance();
                String out = "";
                out += String.format("<h2>%s</h2>", dae.name());
                out += String.format(paragraphTemplate, String.format("Path: %s", route.getPath()));
                out += String.format(paragraphTemplate, String.format("Description: %s", dae.description()));
                out += String.format(paragraphTemplate, String.format("Request-Type: %s", route.getType().name()));
                return out;
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return "";
        }
        return "";
    }

    private String buildResponses() {
        try {
            if (getHandler().isAnnotationPresent(ApiEndpointResponses.class)) {
                ApiEndpointResponses aerp = getHandler().getAnnotation(ApiEndpointResponses.class);
                String out = "<table>%s</table>";
                String bodyTemplate = "<textarea readonly>%s</textarea>";
                String rowTemplate = "<tr><td>%s</td><td>%s</td><td>%s</td></tr>";
                List<String> rows = new ArrayList<>();
                rows.add(String.format(rowTemplate, "Status-Code", "Description", "Body").replaceAll("td>", "th>"));
                Arrays.stream(aerp.value()).forEach(arp -> {
                    rows.add(String.format(rowTemplate, arp.statusCode(), arp.description(), String.format(bodyTemplate, arp.body().getJson().toString(2))));
                });
                return String.format(out, rows.stream().collect(Collectors.joining()));
            }
        } catch (NoSuchMethodException e) {
            return "";
        }
        return "";
    }

    private String buildRequestParameters() {
        try {
            if (getHandler().isAnnotationPresent(ApiEndpointRequestParameters.class)) {
                ApiEndpointRequestParameters aerp = getHandler().getAnnotation(ApiEndpointRequestParameters.class);
                String out = "<table>%s</table>";
                String rowTemplate = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
                List<String> rows = new ArrayList<>();
                rows.add(String.format(rowTemplate, "Name", "Description", "Type", "Exmple-value").replaceAll("td>", "th>"));
                Arrays.stream(aerp.value()).forEach(arp -> {
                    rows.add(String.format(rowTemplate, arp.parameterName(), arp.description(), arp.parameterType().getSimpleName().toLowerCase(Locale.ROOT), arp.exampleValue()));
                });

                out = String.format(out, rows.stream().collect(Collectors.joining()));
                return out;
            }
        } catch (NoSuchMethodException e) {
            return "";
        }
        return "";
    }

    private String buildExampleRequest(boolean htmlEncode) {
        try {
            if (getHandler().isAnnotationPresent(ApiEndpointRequestParameters.class)) {
                ApiEndpointRequestParameters aerp = getHandler().getAnnotation(ApiEndpointRequestParameters.class);
                JSONObject json = new JSONObject();
                Arrays.stream(aerp.value()).forEach(arp -> json.put(arp.parameterName(), arp.exampleValue()));
                String ret = json.toString(2);
                if (htmlEncode) {
                    ret = String.format("Example-Request:<br /><textarea style=\"width: 90vw; height: 30vh;\" readonly>%s</textarea>", ret);
                }
                return ret;
            }
        } catch (NoSuchMethodException e) {
            return "";
        }
        return "";

    }

    public String getDocumentation() {
        return documentation;
    }
}
