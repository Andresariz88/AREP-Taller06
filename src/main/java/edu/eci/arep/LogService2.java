package edu.eci.arep;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;

public class LogService2 {
    private static final List<String> logs = new ArrayList<>();

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            logs.add("LOG SERVICE 3: Log número: #"+i);
        }

        port(getPort());
        get("/", (req,res) -> {

            return logs;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35003;
    }
}
