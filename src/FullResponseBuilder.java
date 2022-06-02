import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

public class FullResponseBuilder {
    public static String getFullResponse(HttpURLConnection con) throws IOException {
        String output = "";

        // Building full response
        // Adding the response status info
        StringBuilder fullResponseBuilder = new StringBuilder();
        fullResponseBuilder.append(con.getResponseCode())
                .append(" ")
                .append(con.getResponseMessage())
                .append("\n");

        output = fullResponseBuilder.toString();

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

        output = fullResponseBuilder.toString();

        int status = con.getResponseCode();
        Reader streamReader = null;

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
}