package de.ypsilon.quizzy.util.apidoc;

import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
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

    static final String HALFMOON_PAGE = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\" /><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" /><meta content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" name=\"viewport\" /><meta name=\"viewport\" content=\"width=device-width\" /><link rel=\"icon\" href=\"path/to/fav.png\"><title>Quizzy - API-Doc</title><link href=\"https://php.shirkanesi.com/data/halfmoon/css/halfmoon-variables.min.css\" rel=\"stylesheet\" /></head><body class=\"with-custom-webkit-scrollbars with-custom-css-scrollbars\" data-dm-shortcut-enabled=\"true\" data-sidebar-shortcut-enabled=\"true\" data-set-preferred-theme-onload=\"true\"><div class=\"page-wrapper with-navbar with-sidebar with-navbar-fixed-bottom\" data-sidebar-type=\"overlayed-sm-and-down\"><div class=\"sticky-alerts\"></div> <nav class=\"navbar\"> <a href=\"#\" class=\"navbar-brand\"> <img src=\"\" alt=\"\"> Quizzy - API-Doc </a> </nav><div class=\"sidebar-overlay\" onclick=\"halfmoon.toggleSidebar()\"></div><div class=\"sidebar\"></div><div class=\"content-wrapper\"><div class=\"container-fluid\"><div class=\"content\"><h1 class=\"content-title font-size-22\"> Quizzy API-Doc</h1></div> %s</div></div> <nav class=\"navbar navbar-fixed-bottom\"> </nav></div> <script src=\"https://php.shirkanesi.com/data/halfmoon/js/halfmoon.min.js\"></script> <script>halfmoon.toggleSidebar();</script> </body></html>";

    private static final String TABLE_TEMPLATE = "<table class=\"table\">%s</table>";
    private static final String TEXTAREA_TEMPLATE = "<textarea  class=\"form-control\" readonly>%s</textarea>";

    public ApiEndpointDocumentation(Class<? extends Route> clazz) {
        this.clazz = clazz;
        this.documentation = buildDocumentationString();
    }

    private String buildDocumentationString() {
        if (clazz.isAnnotationPresent(DocumentedApiEndpoint.class)) {
            String cardTemplate = "<div class=\"card\">%s</div>";
            String out = "";

            out += buildHeadline();
            out += buildRequestParameters();
            out += buildExampleGetRequest();
            out += buildExamplePostRequest(true);
            out += buildResponses();


            return String.format(cardTemplate, out);
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
                StringBuilder out = new StringBuilder();
                out.append(String.format("<h2>%s</h2>", dae.name()));
                out.append(String.format(paragraphTemplate, String.format("Path: %s", route.getPath())));
                out.append(String.format(paragraphTemplate, String.format("Description: %s", dae.description())));
                out.append(String.format(paragraphTemplate, String.format("Request-Type: %s", route.getType().name())));
                if (getHandler().isAnnotationPresent(ApiRequestRequirements.class)) {
                    Arrays.stream(getHandler().getAnnotation(ApiRequestRequirements.class).value()).forEach(req -> {
                        out.append(String.format(paragraphTemplate, "Requirement: " + req.requirement()));
                    });
                } else if (getHandler().isAnnotationPresent(ApiRequestRequirement.class)) {
                    out.append(String.format(paragraphTemplate, "Requirement: " + getHandler().getAnnotation(ApiRequestRequirement.class).requirement()));
                }
                return out.toString();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return "";
        }
        return "";
    }

    private String buildResponses() {
        try {
            String out = "<h5>Possible Responses:</h5>" + TABLE_TEMPLATE;
            String bodyTemplate = TEXTAREA_TEMPLATE;
            String rowTemplate = "<tr><td>%s</td><td>%s</td><td>%s</td></tr>";
            List<String> rows = new ArrayList<>();
            rows.add(String.format(rowTemplate, "Status-Code", "Description", "Body").replaceAll("td>", "th>"));
            if (getHandler().isAnnotationPresent(ApiEndpointResponses.class)) {
                ApiEndpointResponses aerp = getHandler().getAnnotation(ApiEndpointResponses.class);
                Arrays.stream(aerp.value()).forEach(arp -> {
                    rows.add(String.format(rowTemplate, arp.statusCode(), arp.description(), String.format(bodyTemplate, String.format(bodyTemplate, formatResponse(arp)))));
                });
            } else if (getHandler().isAnnotationPresent(ApiEndpointResponse.class)) {
                ApiEndpointResponse arp = getHandler().getAnnotation(ApiEndpointResponse.class);
                rows.add(String.format(rowTemplate, arp.statusCode(), arp.description(), String.format(bodyTemplate, formatResponse(arp))));
            } else {
                return "";
            }
            return String.format(out, rows.stream().collect(Collectors.joining()));
        } catch (NoSuchMethodException e) {
            return "";
        }
    }

    private static String formatResponse(ApiEndpointResponse arp) {
        if (arp.body().equals(ApiResponses.HTML)) return "HTML-Content";
        return arp.body().getJson().toString(2);
    }

    private String buildRequestParameters() {
        try {
            String out = "<h5>Request Parameters:</h5>" + TABLE_TEMPLATE;
            String rowTemplate = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
            List<String> rows = new ArrayList<>();
            rows.add(String.format(rowTemplate, "Name", "Description", "Type", "Exmple-value").replaceAll("td>", "th>"));
            // FIXME one parameter requests
            if (getHandler().isAnnotationPresent(ApiEndpointRequestParameters.class)) {
                ApiEndpointRequestParameters aerp = getHandler().getAnnotation(ApiEndpointRequestParameters.class);
                Arrays.stream(aerp.value()).forEach(arp -> {
                    rows.add(formatParameter(arp, rowTemplate));
                });
            } else if (getHandler().isAnnotationPresent(ApiEndpointRequestParameter.class)) {
                rows.add(formatParameter(getHandler().getAnnotation(ApiEndpointRequestParameter.class), rowTemplate));
            } else {
                return "";
            }
            out = String.format(out, rows.stream().collect(Collectors.joining()));
            return out;
        } catch (NoSuchMethodException e) {
            return "";
        }
    }

    private String formatParameter(ApiEndpointRequestParameter arp, String rowTemplate) {
        return String.format(rowTemplate, arp.parameterName(), arp.description(), arp.parameterType().getSimpleName().toLowerCase(Locale.ROOT), arp.exampleValue());
    }

    private String buildExamplePostRequest(boolean htmlEncode) {
        try {
            JSONObject json = new JSONObject();
            if (getHandler().isAnnotationPresent(ApiEndpointRequestParameters.class)) {
                ApiEndpointRequestParameters aerp = getHandler().getAnnotation(ApiEndpointRequestParameters.class);
                Arrays.stream(aerp.value()).forEach(arp -> json.put(arp.parameterName(), arp.exampleValue()));
            } else if (getHandler().isAnnotationPresent(ApiEndpointRequestParameter.class)) {
                ApiEndpointRequestParameter arp = getHandler().getAnnotation(ApiEndpointRequestParameter.class);
                json.put(arp.parameterName(), arp.exampleValue());
            } else {
                return "";
            }
            String ret = json.toString(2);
            if (htmlEncode) {
                ret = String.format("<h5>Example request-body:</h5>" + TEXTAREA_TEMPLATE, ret);
            }
            return ret;
        } catch (NoSuchMethodException e) {
            return "";
        }
    }

    private String buildExampleGetRequest() {
        try {
            Route route = clazz.getDeclaredConstructor().newInstance();
            StringBuilder out = new StringBuilder();
            if (!route.getType().equals(HandlerType.GET)) return "";
            out.append(route.getPath());
            List<String> params = new ArrayList<>();
            if (getHandler().isAnnotationPresent(ApiEndpointRequestParameters.class)) {
                ApiEndpointRequestParameters aerp = getHandler().getAnnotation(ApiEndpointRequestParameters.class);
                Arrays.stream(aerp.value()).forEach(arp -> params.add(arp.parameterName() + "=" + arp.exampleValue()));
                ;
            } else if (getHandler().isAnnotationPresent(ApiEndpointRequestParameter.class)) {
                ApiEndpointRequestParameter arp = getHandler().getAnnotation(ApiEndpointRequestParameter.class);
                params.add(arp.parameterName() + "=" + arp.exampleValue());
            } else {
                return "";
            }
            String ret = out.append("?").append(params.stream().collect(Collectors.joining("&"))).toString();
            ret = String.format("<h5>Example request-url:</h5>" + TEXTAREA_TEMPLATE, ret);
            return ret;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return "";
        }
    }

    public String getDocumentation() {
        return documentation;
    }
}
