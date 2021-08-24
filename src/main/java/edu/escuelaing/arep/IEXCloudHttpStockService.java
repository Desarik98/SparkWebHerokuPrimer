package edu.escuelaing.arep;

public class IEXCloudHttpStockService extends HttpStockService{

    @Override
    public String getURL(String timeSeries, String stock) {

        return "https://cloud.iexapis.com/stable/stock/market/batch/time_series?symbols="+stock+"&types=quote,chart&range="+timeSeries+"&token=pk_d4e76ebeb6b1480e9c3892e4ac904f24";
    }
}
