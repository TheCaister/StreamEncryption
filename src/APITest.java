import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
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

        // Adding headers
        con.setRequestProperty("Content-Type", "application/json");

        // Reading headers
        String contentType = con.getHeaderField("Content-Type");

        // Setting timeouts
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // Working with cookies
        String cookiesHeader = con.getHeaderField("Set-Cookie");
        // Getting list of cookies
        //List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);

        CookieManager cookieManager = null;

        // Perform this function to each cookie in the list
        //cookies.forEach(cookie -> cookieManager.getCookieStore().add(null, cookie));

        // Get the username cookie. Optional makes it so that it can be empty.
//        Optional<HttpCookie> usernameCookie = cookies.stream().findAny().filter(
//                cookie -> cookie.getName().equals("username")
//        );

        // If no username cookie exists, add one.
//        if (usernameCookie == null) {
//            cookieManager.getCookieStore().add(null, new HttpCookie("username", "ed"));
//        }

        // To add cookies to the request, the Cookie header must be set.
        // Connection must be closed and reopened.
        con.disconnect();
        con = (HttpURLConnection) url.openConnection();

//        con.setRequestProperty("Cookie",
//                StringUtils.join(cookieManager.getCookieStore().getCookies(), ";"));

        // Redirects - Can set to either true or false
        //con.setInstanceFollowRedirects(false);
        //HttpURLConnection.setFollowRedirects(false);

        // If a request returns a status code 301 or 302 (indicating a redirect),
        // retrieve the Location header and create a new request to the new URL.
        int status = 0;
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = con.getHeaderField("Location");
            URL newUrl = new URL(location);
            con = (HttpURLConnection) newUrl.openConnection();
        }

        // Reading responses
        status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // In the case that the request fails...
        Reader streamReader = null;

        // Get different stream depending if there's an error.
        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }
        // Then we can carry on with the reading afterwards


        System.out.println(ParameterStringBuilder.getParamsString(parameters));

        Map<String, String> randParameters = new HashMap<>();
        randParameters.put("num", "20");
        randParameters.put("min", "-321");
        randParameters.put("max", "3000");
        randParameters.put("col", "1");
        randParameters.put("base", "10");
        randParameters.put("format", "plain");
        randParameters.put("rnd", "new");

        //URL randUrl = new URL(Constants.RANDOM_API_INTEGERS);
        //URL randUrl = new URL("https://www.random.org/integers/?num=10&min=1&max=6&col=1&base=10&format=plain&rnd=new");
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

        System.out.println(ResponseBuilder.getFullResponse(randCon));

    }

}
