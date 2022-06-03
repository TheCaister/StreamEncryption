import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

public class ResponseBuilder {
    /**
     * Getting the full response of an HTTP request in the form of a String.
     * @param con The HTTP connection.
     * @return Full response of the request.
     * @throws IOException
     */
    public static String getFullResponse(HttpURLConnection con) throws IOException {
        // Building full response
        // Adding the response status info
        StringBuilder fullResponseBuilder = new StringBuilder();
        fullResponseBuilder.append(con.getResponseCode())
                .append(" ")
                .append(con.getResponseMessage())
                .append("\n");

        // Getting the headers and adding each of them to the StringBuilder
        // Done in the format HeaderName: HeaderValues
        con.getHeaderFields().entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {
                    fullResponseBuilder.append(entry.getKey()).append(": ");
                    List headerValues = entry.getValue();
                    Iterator it = headerValues.iterator();
                    if (it.hasNext()) {
                        fullResponseBuilder.append(it.next());
                        while (it.hasNext()) {
                            fullResponseBuilder.append(", ").append(it.next());
                        }
                    }
                    fullResponseBuilder.append("\n");
                });

        int status = con.getResponseCode();
        Reader streamReader;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            fullResponseBuilder.append(inputLine);
            fullResponseBuilder.append('\n');
        }
        in.close();

        return fullResponseBuilder.toString();
    }

    /**
     * Returns only the content part of an HTTP request in String format.
     * @param con The HTTP connection.
     * @return Content part of the response.
     * @throws IOException
     */
    public static String getResponseContentOnly(HttpURLConnection con) throws IOException {
        StringBuilder output = new StringBuilder();

        int status = con.getResponseCode();
        Reader streamReader;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            output.append(inputLine);
            output.append('\n');
        }
        output.deleteCharAt(output.length() - 1);
        in.close();

        return output.toString();
    }
}