package edu.eci.arep.mongo.taller6;

import static com.mongodb.client.model.Sorts.descending;
import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

/**
 * Log service to the system, usin MongoDb
 *
 * @author Santiago Forero Yate
 */
public class AppLog {

    private static MongoClient client = new MongoClient("db", 27017);

    public static void main(String[] args) {
        port(getPort());
        get("/loginserverfacade", (req, res) -> {
            return loadData(req.queryParams("query"), req.queryParams("service"));
        });
    }

    private static String loadData(String newMsg, String serviceIns) {
        try {
            MongoDatabase database = client.getDatabase("taller6");
            MongoCollection<Document> messages = database.getCollection("logs");

            // Save the new document in the database
            Document newDoc = new Document("message", newMsg).append("date", new Date()).append("final Server", serviceIns);
            messages.insertOne(newDoc);

            // Get the 10 most recent documents
            List<Document> query = new ArrayList<>();
            Bson projection = Projections.fields(Projections.include("date", "message"), Projections.excludeId());
            messages.find().sort(descending("date")).projection(projection).limit(10).into(query);

            Map<String, Map<String, Object>> result = new HashMap<>();
            int cont = 1;
            for (Document doc : query) {
                Map<String, Object> record = new HashMap<>();
                record.put("message", doc.getString("message"));
                record.put("date", ((Date) doc.get("date")).toInstant().toString());
                record.put("final Server", doc.getString("final Server"));
                result.put(String.valueOf(cont), record);
                cont++;
            }

            Gson json = new Gson();
            return json.toJson(result);
        } catch (Exception e) {
            return "An error happened trying to load or safe data MongoDB: " + e.getMessage();
        }
    }

    /**
     * method that returns the port to run the service
     * 
     * @return and integer that reperesents the port
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 7000;
    }
}
