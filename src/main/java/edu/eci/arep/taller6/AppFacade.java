package edu.eci.arep.taller6;

import static spark.Spark.get;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

public class AppFacade {
    private static WebConnection invoker = new WebConnection();
    private static String[] Urls =  {"http://localhost:5000/loginserverfacade"};
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");
        get("/loginservice", (req, res) -> {
            invoker.setURLInvoked(Urls);
            res.type("appliaction-json");
            return invoker.connect(args);});
    }
}
