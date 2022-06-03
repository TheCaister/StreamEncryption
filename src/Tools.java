import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Tools {
    public static int generateRandomInteger(){
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }

    public static int generateRandomIntegerHTTP() throws IOException {
        Map<String, String> randParameters = new HashMap<>();
        int output;
        int status;
        randParameters.put("num", "1");
        randParameters.put("min", "0");
        randParameters.put("max", "1000000000");
        randParameters.put("col", "1");
        randParameters.put("base", "10");
        randParameters.put("format", "plain");
        randParameters.put("rnd", "new");

        URL randUrl = new URL(Constants.RANDOM_API_INTEGERS + ParameterStringBuilder.getParamsString(randParameters));
        HttpURLConnection randCon = (HttpURLConnection) randUrl.openConnection();
        randCon.setRequestMethod("GET");
        randCon.setDoOutput(true);

        randCon.setConnectTimeout(5000);
        randCon.setReadTimeout(5000);


        status = randCon.getResponseCode();
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = randCon.getHeaderField("Location");
            URL newUrl = new URL(location);
            randCon = (HttpURLConnection) newUrl.openConnection();
        }

        // In the case that the request fails...
        Reader randStreamReader = null;

        // Get different stream depending if there's an error.
        if (status > 299) {
            randStreamReader = new InputStreamReader(randCon.getErrorStream());
        } else {
            randStreamReader = new InputStreamReader(randCon.getInputStream());
        }
        // Then we can carry on with the reading afterwards

        output = Integer.parseInt(ResponseBuilder.getResponseContentOnly(randCon));

        randCon.disconnect();

        System.out.println(output);
        return output;
    }
}
