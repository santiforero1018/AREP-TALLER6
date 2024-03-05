package edu.eci.arep.taller6;

import static spark.Spark.get;
// import static spark.Spark.staticFiles;
import static spark.Spark.port;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // staticFiles.location("/public");
        port(5000);
        get("/loginserverfacade", (req, res) -> {
            res.type("appliaction-json");
            return "{\"login\":\"2024-02-20 - login inicial\"}";
        });
    }
}
