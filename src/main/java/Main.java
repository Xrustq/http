import java.io.IOException;

public class Main {

    private static final String URL = "https://yandex.ru/maps/44/izhevsk";
    private static final String ADDRESS = "Ижевск, Карла Маркса, 246";

    public static void main(String[] args) throws IOException {

        StringBuilder response;
        StringBuilder response2;
        String yandexuid;
        String csrfToken;
        String coordinates;

        Actions actions = new Actions();

        response = actions.doConnect(URL);
        yandexuid = actions.getYandexUid(response);
        csrfToken = actions.getCsrfToken(response);
        response2 = actions.doConnectWithParam(ADDRESS, yandexuid, csrfToken);
        coordinates = actions.findCoordinates(response2, ADDRESS);
    }


}
