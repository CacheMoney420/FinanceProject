package cachemoney420.financeproject;

import android.support.v4.util.Pair;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by mspear on 4/19/16.
 */
public class CompareTest {
    private static final String TAG = "tag";

    private static Comparator<Pair<Pair<BigDecimal, Integer>, Pair<String, String>>> sPairComparator
            = new Comparator<Pair<Pair<BigDecimal, Integer>, Pair<String, String>>>() {
        @Override
        public int compare(Pair<Pair<BigDecimal, Integer>, Pair<String, String>> lhs,
                           Pair<Pair<BigDecimal, Integer>, Pair<String, String>> rhs) {
            if (lhs.first.second.equals(rhs.first.second)) {
                return lhs.first.first.compareTo(rhs.first.first);
            }
            return lhs.first.second.compareTo(rhs.first.second);
        }
    };

    @Test
    public void getCompare() {

        List<Pair<Pair<BigDecimal, Integer>, Pair<String, String>>> ratioList = new ArrayList<>();
        String[] over = new String[3];
        over[0] = "PFE";
        over[1] = "BAC";
        over[2] = "VRX";

        try {
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.YEAR, -1);
            Map<String, Stock> overMap = YahooFinance.get(over, from, to);
            Map<String, Stock> underMap = YahooFinance.get(over, from, to);


            for (String o : overMap.keySet()) {
                for (String u : underMap.keySet()) {
                    BigDecimal ratio = overMap.get(o).getQuote().getPrice().divide(
                            underMap.get(u).getQuote().getPrice(),
                            BigDecimal.ROUND_CEILING
                    );
                    List<HistoricalQuote> overQuoteList = overMap.get(o).getHistory(from, to, Interval.DAILY);
                    List<HistoricalQuote> underQuoteList = underMap.get(u).getHistory(from, to, Interval.DAILY);
                    List<BigDecimal> historyQuotes = new ArrayList<>();

                    for (int quoteIndex = 0; quoteIndex != overQuoteList.size(); quoteIndex++) {
                        historyQuotes.add(overQuoteList.get(quoteIndex).getAdjClose().divide(underQuoteList.get(quoteIndex).getAdjClose(), BigDecimal.ROUND_CEILING));
                    }
//                    for (HistoricalQuote hqOver : overMap.get(o)
////                            .getHistory(from, to, Interval.DAILY)) {
//                        quoteList.add(hq.getAdjClose());
//                    }
                    Collections.sort(historyQuotes);
                    Collections.reverse(historyQuotes);
                    boolean found = false;
                    for (Integer rank = 0; rank != historyQuotes.size() - 1; rank++) {
                        if (ratio.compareTo(historyQuotes.get(rank)) == 1) {
                            ratioList.add(Pair.create(Pair.create(ratio, rank), Pair.create(o, u)));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ratioList.add(
                                Pair.create(Pair.create(ratio, historyQuotes.size()), Pair.create(o, u))
                        );
                    }
                }
            }
        } catch (IOException ioe) {
            Log.d(TAG, ioe.getMessage());
        }
        Collections.sort(ratioList, sPairComparator);
        Collections.reverse(ratioList);
        for (Pair<Pair<BigDecimal, Integer>, Pair<String, String>> item : ratioList) {
            System.out.println(item.first.first);
        }
        //return ratioList;

    }
}
