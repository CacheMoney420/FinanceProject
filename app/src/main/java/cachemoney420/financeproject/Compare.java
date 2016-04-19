package cachemoney420.financeproject;

import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Compare {
    private static final String TAG = "tag";

    public HashMap<BigDecimal, String> getCompare(String[] over, String[] under) {

        HashMap<BigDecimal, String> ratioMap = new HashMap<BigDecimal, String>();

        try {
            Map<String, Stock> overMap = YahooFinance.get(over);
            Map<String, Stock> underMap = YahooFinance.get(under);
            for (String lv : overMap.keySet()) {
                for (String ticker : underMap.keySet()) {
                    BigDecimal ratio = overMap.get(lv).getQuote().getPrice().divide(
                            underMap.get(ticker).getQuote().getPrice(), BigDecimal.ROUND_CEILING);
                    ratioMap.put(ratio, lv + " vs " + ticker);
                }
            }

        } catch (IOException ioe) {
            Log.d(TAG, ioe.getMessage());
        }
        return ratioMap;

    }
    public List<BigDecimal> sortCompares(HashMap<BigDecimal, String> ratioMap) {
        List<BigDecimal> ratioList = new ArrayList<>(ratioMap.keySet());
        Collections.sort(ratioList);
        Collections.reverse(ratioList);
        return ratioList;
    }
}
