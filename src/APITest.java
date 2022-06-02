import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class APITest {
    public static void main(String[] args) throws IOException {
        // Storing parameters for requests
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        parameters.put("param2", "val");

        // Set up connection and request method
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        // Set up output stream
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        System.out.println(ParameterStringBuilder.getParamsString(parameters));
    }
}
