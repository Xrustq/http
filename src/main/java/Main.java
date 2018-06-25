import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String URL = "https://yandex.ru/maps/44/izhevsk";
    private static final String ADDRESS = "Ижевск, Карла Маркса, 246";

    public static void main(String[] args) throws IOException {

        StringBuilder response;
        StringBuilder response2;
        String yandexuid;
        String csrfToken;

        Actions actions = new Actions();

        response = actions.doConnect(URL);
        yandexuid = actions.getYandexUid(response);
        csrfToken = actions.getCsrfToken(response);

//        logger.info(response.toString());
        logger.info("csrfToken=".concat(actions.getCsrfToken(response)));
        logger.info(actions.getYandexUid(response));

        response2 = actions.doConnectWithParam(ADDRESS, yandexuid, csrfToken);

//        logger.info(response2.toString());
        logger.info(ADDRESS.concat(" coordinates = ".concat(actions.findCoordinates(response2))));
    }


}
