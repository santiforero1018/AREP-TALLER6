package edu.eci.arep.taller6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebConnection {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String[] GET_URLs;
    private int instancia = 1;

    public void setURLInvoked(String[] urls){
        GET_URLs = urls;
    }

    public String connect(String [] args) throws IOException{
        URL obj = new URL(GET_URLs[instancia]);
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
        return response.toString();
    }
}
