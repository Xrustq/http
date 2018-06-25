import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Actions {

    private static Logger logger = LoggerFactory.getLogger(Actions.class);

    public StringBuilder doConnect(String URL) throws IOException {

        String inputLine;
        StringBuilder response = new StringBuilder();

        java.net.URL obj = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

//        logger.info(response.toString());

        connection.disconnect();

        return response;
    }

    public StringBuilder doConnectWithParam(String ADDRESS, String yandexuid, String csrfToken) throws IOException {

        String inputLine;
        StringBuilder response = new StringBuilder();

        URL obj = new URL("https://yandex.ru/maps/api/search?text=" + URLEncoder.encode(ADDRESS, "UTF-8") + "&lang=ru_RU&csrfToken=" + csrfToken);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", yandexuid);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

//        logger.info(response.toString());

        connection.disconnect();

        return response;
    }

    public String getCsrfToken(StringBuilder str) {

        String csrfToken = "";

        Pattern pattern = Pattern.compile("csrfToken\":\"[a-zA-Z0-9]*:[0-9]*");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            csrfToken = matcher.group().replaceAll("csrfToken\":\"", "");
        }

        logger.info("csrfToken=" + csrfToken);

        return csrfToken;
    }

    public String getYandexUid(StringBuilder str) {
        String yandexUid = "";

        Pattern pattern = Pattern.compile("yandexuid=[0-9]*");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            yandexUid = matcher.group();
        }

        logger.info(yandexUid);

        return yandexUid;
    }

    public String findCoordinates(StringBuilder response, String ADDRESS) {
        String coordinates = "";
        String coordinates2;
        String coordinates3;

        Pattern pattern = Pattern.compile("coordinates\":\\[[0-9].*?]");
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            coordinates = matcher.group().
                    replaceAll("coordinates\":\\[", "").
                    replaceAll("]", "");
        }

        coordinates2 = coordinates.substring(0, coordinates.indexOf(','));
        coordinates3 = coordinates.substring(coordinates.lastIndexOf(','),
                coordinates.length()).replaceFirst(",", "");
        coordinates = coordinates3.concat((", ").concat(coordinates2));

        logger.info(ADDRESS.concat(". coordinates = ".concat(coordinates)));

        return coordinates;
    }
}
