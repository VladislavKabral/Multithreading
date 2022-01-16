package by.epam.multithreading;

import by.epam.multithreading.entity.Client;
import by.epam.multithreading.parser.JsonParser;
import by.epam.multithreading.parser.ParserException;
import by.epam.multithreading.restaurant.CashDesk;
import by.epam.multithreading.restaurant.Restaurant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String JSON_FILE_PATH = "src/main/resources/Clients.json";
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static void programExecution() {
        JsonParser jsonParser = new JsonParser();
        try {
            List<Client> clients = jsonParser.parse(JSON_FILE_PATH);
            Restaurant restaurant = Restaurant.getInstance();

            for (Client client: clients) {
                int numberOfCashDesk = client.calculateNumberOfCashDesk();
                restaurant.addClientToDefiniteCashDesk(numberOfCashDesk, client);
            }

            List<CashDesk> cashDesks = restaurant.getCashDesks();
            int countOfCashDesks = cashDesks.size();
            ExecutorService cashDesksService = Executors.newFixedThreadPool(countOfCashDesks);
            cashDesks.forEach(cashDesksService::submit);

            while (restaurant.getCountOfClients() > 0) {
                TimeUnit.SECONDS.sleep(1);
            }

            cashDesksService.shutdown();

        } catch (ParserException | InterruptedException e) {
            LOGGER.error(e);
        }
    }

    public static void main(String[] args) {
        programExecution();
    }
}
