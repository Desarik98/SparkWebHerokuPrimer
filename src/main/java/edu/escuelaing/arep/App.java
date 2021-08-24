package edu.escuelaing.arep;

import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.*;

/**
 * Minimal web app example for Heroku using SparkWeb
 *
 * @author Daniel Mejia
 */
public class App {

    private static final MemoryCache<String, String> memoryCache = new MemoryCache<String,String>(5000,5000,100);

    /**
     * This main method uses SparkWeb static methods and lambda functions to
     * create a simple Hello World web app. It maps the lambda function to the
     * /hello relative URL.
     */
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        get("/inputDataStockService", App::inputDataPageStockService);
        get("/inputDataIEXCloud", App::inputDataPageIEXCloud);
        get("/stockService", App::stockService);
        get("/iexService", App::iexService);
    }
    private static String inputDataPageStockService(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>HTML Forms</h2>"
                + "<form action=\"/stockService\">"
                + "  Stock:<br>"
                + "  <input type=\"text\" name=\"stock\" value=\"MSFT\">"
                + "  <br>"
                + "  Time Series:<br>"
                + "  <input type=\"text\" name=\"time_series\" value=\"INTRADAY\">"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "<p>If you click the \"Submit\" button, the form-data will be sent to a page called \"/results\".</p>"
                + "</body>"
                + "</html>";
        return pageContent;
    }
    private static String inputDataPageIEXCloud(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>HTML Forms</h2>"
                + "<form action=\"/iexService\">"
                + "  Stock:<br>"
                + "  <input type=\"text\" name=\"stock\" value=\"aapl\">"
                + "  <br>"
                + "  Time Series:<br>"
                + "  <input type=\"text\" name=\"time_series\" value=\"today\">"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "<p>If you click the \"Submit\" button, the form-data will be sent to a page called \"/results\".</p>"
                + "</body>"
                + "</html>";
        return pageContent;
    }


    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    private static String stockService(Request req, Response res){
        String response = "None";
        try {
            response = HttpStockService.getAlphaService().getStockInfoAsJSON(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (memoryCache.get(req.url()) == null){
            memoryCache.put(req.url(),response);
            return response;
        }else {
            return memoryCache.get(req.url());
        }
    }

    private static String iexService(Request req, Response res){
        String response = "None";
        try{
            response = IEXCloudHttpStockService.getIEXService().getStockInfoAsJSON(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (memoryCache.get(req.url()) == null){
            memoryCache.put(req.url(),response);
            return response;
        }else {
            return memoryCache.get(req.url());
        }
    }

}
