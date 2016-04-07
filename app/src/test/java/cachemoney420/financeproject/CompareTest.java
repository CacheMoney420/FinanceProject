package cachemoney420.financeproject;

import android.support.annotation.NonNull;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by mspear on 4/5/16.
 */
public class CompareTest {

    private static final String TAG = "log";



    @Test
    public void stockCompare() {
        String[] tickerArray = new String[3];
        tickerArray[0] ="PFE";
        tickerArray[1] = "BAC";
        tickerArray[2] = "VRX";

        HashMap<BigDecimal, String> ratioMap = new HashMap<BigDecimal, String>();

        try {
            Map<String, Stock> mFunds = YahooFinance.get(tickerArray);
            for (String lv: mFunds.keySet()) {
                for (String ticker: tickerArray) {
                    BigDecimal ratio = mFunds.get(lv).getQuote().getPrice().divide(
                            mFunds.get(ticker).getQuote().getPrice(), BigDecimal.ROUND_CEILING);
                    ratioMap.put(ratio, lv + " vs " + ticker);
                }
            }
            List<BigDecimal> ratioList = new ArrayList<>(ratioMap.keySet());
            Collections.sort(ratioList);
            Collections.reverse(ratioList);
            assert(ratioList.get(0).compareTo(ratioList.get(1)) == 1);
        } catch (IOException ioe) {
            Log.d(TAG, ioe.getMessage());
        }
    }
}
