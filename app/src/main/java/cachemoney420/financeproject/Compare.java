package cachemoney420.financeproject;

import android.support.v4.util.Pair;
import android.util.Log;

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
 * Created by mspear on 4/5/16.
 */
public class Compare {
    private static final String TAG = "tag";

    private static Comparator<Pair<BigDecimal, Integer>> sPairComparator = new Comparator<Pair<BigDecimal, Integer>>() {
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

    public HashMap<Pair<BigDecimal, Integer>, String> getCompare(String[] over, String[] under) {

        HashMap<Pair<BigDecimal, Integer>, String> ratioMap =
                new HashMap<>();


        try {
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.YEAR, -1);
            Map<String, Stock> overMap = YahooFinance.get(over, from, to);
            Map<String, Stock> underMap = YahooFinance.get(under, from, to);


            for (String o : overMap.keySet()) {
                for (String u : underMap.keySet()) {
                    BigDecimal ratio = overMap.get(o).getQuote().getPrice().divide(
                            underMap.get(u).getQuote().getPrice(),
                            BigDecimal.ROUND_CEILING
                    );
                    List<BigDecimal> quoteList = new ArrayList<>();
                    for (HistoricalQuote hq : underMap.get(u).getHistory(from, to, Interval.DAILY)) {
                        quoteList.add(hq.getAdjClose());
                    }
                    Collections.sort(quoteList);
                    Collections.reverse(quoteList);
                    boolean found = false;
                    for (Integer rank = 0; rank == quoteList.size() - 1; rank++) {
                        if (ratio.compareTo(quoteList.get(rank)) == 1) {
                            ratioMap.put(new Pair<>(ratio, rank), o + " vs " + u);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ratioMap.put(new Pair<>(ratio, quoteList.size()), o + " vs " + u);
                    }
                }
            }
        } catch (IOException ioe) {
            Log.d(TAG, ioe.getMessage());
        }
        return ratioMap;

    }
    public List<Pair<BigDecimal, Integer>> sortCompares(HashMap<Pair<BigDecimal, Integer>, String> ratioMap) {
        List<Pair<BigDecimal, Integer>> ratioList = new ArrayList<>(ratioMap.keySet());
        Collections.sort(ratioList, sPairComparator);
        Collections.reverse(ratioList);
        return ratioList;
    }
}
