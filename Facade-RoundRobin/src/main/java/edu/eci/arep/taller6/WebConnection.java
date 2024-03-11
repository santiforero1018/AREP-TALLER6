package edu.eci.arep.taller6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * class to connect with remote service
 * 
 * @author Santiago Forero Yate
 */
public class WebConnection {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String[] GET_URLs;
    private int instancia = 0;


    /**
     * method that sets an array of strings
     * @param urls urls to connect remotely
     */
    public void setURLInvoked(String[] urls){
        GET_URLs = urls;
    }

    /**
     * method to connect with remote service and returns de response
     * @param queryValue Url query
     * @return A string with response
     * @throws IOException
     */
    public String connect(String queryValue) throws IOException{
        URL obj = new URL(GET_URLs[instancia]+queryValue+getInstance());
        System.out.println("URL destino: "+ GET_URLs[instancia]);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        changeInstance();
        return response.toString();
    }

    /**
     * method that implements round robin to redirect urls
     */
    private void changeInstance(){
        this.instancia = (this.instancia + 1) % GET_URLs.length;
    }


    /**
     * return the instace of the server
     * @return an int that represents the instance
     */
    private int getInstance(){
        return instancia;
    }
}
