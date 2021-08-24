package edu.escuelaing.arep;

public class AlphaAdvantageHttpStockService extends HttpStockService{
    @Override
    public String getURL(String timeSeries, String stock) {

        return "https://www.alphavantage.co/query?function=TIME_SERIES_"+timeSeries+"&symbol="+stock+"&interval=5min&apikey=4NC2BOCH03RV7XZ0";
    }
}
