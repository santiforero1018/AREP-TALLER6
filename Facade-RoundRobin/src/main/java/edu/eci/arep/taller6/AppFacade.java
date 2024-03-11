package edu.eci.arep.taller6;

import static spark.Spark.get;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

/**
 * class Facade to acces to the service
 * 
 * @author Santiago Forero Yate 
 */
public class AppFacade {
    private static WebConnection invoker = new WebConnection();
    private static final String[] URLs = { "http://logservice1:7000/loginserverfacade?query=",
            "http://logservice2:7000/loginserverfacade?query=", "http://logservice3:7000/loginserverfacade?query=" };

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        get("/loginservice", (req, res) -> {
            invoker.setURLInvoked(URLs);
            res.type("appliaction-json");
            return invoker.connect(req.queryParams("send")+"&service=");
        });
    }

    /**
     * method that returns the env port, else, returns 4567
     * 
     * @return and integer that represents the port
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
