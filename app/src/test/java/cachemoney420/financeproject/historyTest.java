package cachemoney420.financeproject;

import android.util.Log;
import android.support.v4.util.Pair;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by mspear on 4/8/16.
 */
public class historyTest {

    public static Comparator<Pair<BigDecimal, Integer>> sPairComparator = new Comparator<Pair<BigDecimal, Integer>>() {
        @Override
        public int compare(Pair<BigDecimal, Integer> lhs, Pair<BigDecimal, Integer> rhs) {
            int result = lhs.second.compareTo(rhs.second);
            if (result != 0) {
                return result;
            } else {
                return lhs.first.compareTo(rhs.first);
            }
        }
    };

    private static final String TAG = "tag";

    @Test
    public void historyCompare() {
        String[] tickerArray = new String[3];
        tickerArray[0] ="PFE";
        tickerArray[1] = "BAC";
        tickerArray[2] = "VRX";

        HashMap<Pair<BigDecimal, Integer>, String> ratioMap = new HashMap<Pair<BigDecimal, Integer>, String>();
        List<BigDecimal> quoteList = new ArrayList<>();

        try {
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();

            from.add(Calendar.YEAR, -1);
            Map<String, Stock> stockMap = YahooFinance.get(tickerArray, from, to);

            for (String s: stockMap.keySet()) {
                for (String s2: stockMap.keySet()) {
                    BigDecimal ratio = stockMap.get(s).getQuote().getPrice().divide(
                            stockMap.get(s2).getQuote().getPrice(),
                            BigDecimal.ROUND_CEILING
                    );
                    for (HistoricalQuote hq: stockMap.get(s2).getHistory(from, to, Interval.DAILY)) {
                        quoteList.add(hq.getAdjClose());
                    }
                    Collections.sort(quoteList);
                    Collections.reverse(quoteList);
                    boolean found = false;
                    for (Integer rank = 0; rank == quoteList.size()-1; rank++) {
                        if (ratio.compareTo(quoteList.get(rank)) == 1) {
                            ratioMap.put(new Pair<>(ratio, rank), s + " vs " + s2);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ratioMap.put(new Pair<>(ratio, quoteList.size()), s + " vs " + s2);
                    }
                }
            }
        } catch (IOException ioe) {
            Log.d(TAG, "historyCompare: " + ioe.getMessage());
        }
        //assert(ratioMap.keySet().size() == tickerArray.length * tickerArray.length);
        List<Pair<BigDecimal, Integer>> ratioList = new ArrayList<>(ratioMap.keySet());
        Collections.sort(ratioList, sPairComparator);
        Collections.reverse(ratioList);
        assert(sPairComparator.compare(ratioList.get(0), ratioList.get(1)) == 1);
    }
}
