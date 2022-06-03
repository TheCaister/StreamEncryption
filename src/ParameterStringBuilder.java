import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ParameterStringBuilder {
    /**
     * Generates the parameters part of the HTTP request in the form of a String.
     * @param params A String-String map of parameters to be passed into the request.
     * @return The String form of all parameters in the map. Parameters will be attached to values using '=' and
     * separated with '&'.
     */
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        // Encode parameters into UTF-8.
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();

        // Returns empty if there are no parameters. Otherwise, return the results, just with the '&' at the end removed.
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}