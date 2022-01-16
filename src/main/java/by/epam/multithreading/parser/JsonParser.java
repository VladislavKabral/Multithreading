package by.epam.multithreading.parser;

import by.epam.multithreading.entity.Client;
import by.epam.multithreading.entity.State;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements Parser{

    private List<Client> clients = new ArrayList<>();
    private static final String TAG_CLIENTS = "clients";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_IS_PREORDER = "isPreOrder";
    private static final String TAG_STATE = "state";

    @Override
    public List<Client> parse(String fileName) throws ParserException {
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader(fileName)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray clientsJsonArray = (JSONArray) object.get(TAG_CLIENTS);

            for (Object jsonClient: clientsJsonArray) {
                JSONObject clientJsonObject = (JSONObject) jsonClient;

                long id = (Long) clientJsonObject.get(TAG_ID);
                String name = (String) clientJsonObject.get(TAG_NAME);
                boolean isPreOrder = Boolean.parseBoolean((String) clientJsonObject.get(TAG_IS_PREORDER));
                State state = State.valueOf(clientJsonObject.get(TAG_STATE).toString().toUpperCase());

                Client client = new Client(id, name, isPreOrder, state);
                clients.add(client);
            }

        } catch (IOException | ParseException e) {
            throw new ParserException(e.getMessage(), e);
        }

        return clients;
    }
}
