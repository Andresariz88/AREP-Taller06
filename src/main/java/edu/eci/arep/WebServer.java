package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;

public class WebServer {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://172.31.54.93:3500%S";


    public static void main(String... args){

        port(getPort());
        staticFileLocation("/public");

        path("/logs", () -> {
            get("", (req,res) -> getLogs());
            post("", (req,res) -> {
                System.out.println(req.body());
                insertLog(req.body());
                return getLogs();
            });
        });
    }

    public static String getLogs() throws IOException {
        URL obj = new URL(roundRobin());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response + "\n");
            return response.toString();
        } else {
            System.out.println("GET request did not work.");
            return "";
        }
    }

    private static void insertLog(String body) throws IOException {
        URL obj = new URL(roundRobin());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/json");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }

    private static String roundRobin() {
        Random random = new Random();
        Map<Integer, String> logservers = new HashMap<>();
        logservers.put(0, "http://ec2-54-225-8-186.compute-1.amazonaws.com");
        logservers.put(1, "http://ec2-44-198-181-103.compute-1.amazonaws.com");
        logservers.put(2, "http://ec2-44-204-96-113.compute-1.amazonaws.com");
        int a = random.nextInt(3);
        String newUrl = logservers.get(a) + ":3500" + (a+1);
        System.out.println("Making request to: "+newUrl);
        return newUrl;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}