import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.*;
import java.util.*;

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
        List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);

        CookieManager cookieManager = null;

        // Perform this function to each cookie in the list
        cookies.forEach(cookie -> cookieManager.getCookieStore().add(null, cookie));

        // Get the username cookie. Optional makes it so that it can be empty.
        Optional<HttpCookie> usernameCookie = cookies.stream().findAny().filter(
                cookie -> cookie.getName().equals("username")
        );

        // If no username cookie exists, add one.
        if (usernameCookie == null) {
            cookieManager.getCookieStore().add(null, new HttpCookie("username", "ed"));
        }

        // To add cookies to the request, the Cookie header must be set.
        // Connection must be closed and reopened.
        con.disconnect();
        con = (HttpURLConnection) url.openConnection();

        con.setRequestProperty("Cookie",
                StringUtils.join(cookieManager.getCookieStore().getCookies(), ";"));

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



        // Set up output stream
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();


        System.out.println(ParameterStringBuilder.getParamsString(parameters));
    }
}
