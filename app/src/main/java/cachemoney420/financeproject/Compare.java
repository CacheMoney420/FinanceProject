package cachemoney420.financeproject;

import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Compare {
    private static final String TAG = "tag";

    private static Comparator<Pair<Pair<Double, Integer>, Pair<String, String>>> sPairComparator
            = new Comparator<Pair<Pair<Double, Integer>, Pair<String, String>>>() {
        @Override
        public int compare(Pair<Pair<Double, Integer>, Pair<String, String>> lhs,
                           Pair<Pair<Double, Integer>, Pair<String, String>> rhs) {
            if (lhs.first.second.equals(rhs.first.second)) {
                return lhs.first.first.compareTo(rhs.first.first);
            }
            return lhs.first.second.compareTo(rhs.first.second);
        }
    };
    private static class StockTask extends AsyncTask<String[], Void, List<Pair<Pair<Double, Integer>, Pair<String, String>>>> {

        @Override
        protected List<Pair<Pair<Double, Integer>, Pair<String, String>>> doInBackground(String[]... params) {
            List<Pair<Pair<Double, Integer>, Pair<String, String>>> ratioList = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("#.00");
            try {
                Map<String, Stock> overMap = YahooFinance.get(params[0], Interval.DAILY);
                Map<String, Stock> underMap = YahooFinance.get(params[1], Interval.DAILY);
                for (String o : overMap.keySet()) {
                    for (String u : underMap.keySet()) {
                        Double ratio = overMap.get(o).getQuote().getPrice().divide(
                                underMap.get(u).getQuote().getPrice(),
                                BigDecimal.ROUND_CEILING
                        ).doubleValue();
                        List<Double> historyQuotes = getHistoryRatio(overMap.get(o), underMap.get(u), true);
                        boolean found = false;
                        for (Integer rank = 0; rank != historyQuotes.size() - 1; rank++) {
                            if (ratio.compareTo(historyQuotes.get(rank)) == 1) {
                                ratio = Double.parseDouble(df.format(ratio));
                                ratioList.add(Pair.create(Pair.create(ratio, rank), Pair.create(o, u)));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            ratio = Double.parseDouble(df.format(ratio));
                            ratioList.add(
                                    Pair.create(Pair.create(ratio, historyQuotes.size()), Pair.create(o, u))
                            );
                        }
                    }
                }
            Collections.sort(ratioList, sPairComparator);
            } catch (IOException ioe) {
                this.cancel(true);
            }
            return ratioList;
        }
    }

    public static List<Double> getHistoryRatio(Stock over, Stock under, Boolean sorted) {
        List<Double> historyQuotes = new ArrayList<>();
        try {
            List<HistoricalQuote> overQuoteList = over.getHistory();
            List<HistoricalQuote> underQuoteList = under.getHistory();

            for (int quoteIndex = 0; quoteIndex != overQuoteList.size(); quoteIndex++) {
                historyQuotes.add(overQuoteList.get(quoteIndex).getAdjClose().divide(underQuoteList.get(quoteIndex).getAdjClose(), BigDecimal.ROUND_CEILING).doubleValue());
            }
            if (sorted) {
                Collections.sort(historyQuotes);
            }
            Collections.reverse(historyQuotes);
        } catch (IOException ioe) {
            Log.e(TAG, "Connection Failed", ioe);
        }
        return historyQuotes;
    }

    public static List<Pair<Pair<Double, Integer>, Pair<String, String>>> getCompare
        (String[] over, String[] under) {
        /*
        Basic guide to using this thing...

        List<Pair<Pair<Ratio, Rank>, Pair<Over ticker, Under ticker>>>
        Ratio = specificPair.first.first
        Rank = specificPair.first.second
        Over ticker = specificPair.second.first
        Under ticker = specificPair.second.second
         */
/*        try {
            return new StockTask().execute(over, under).get();
        } catch (InterruptedException ie) {
            Log.d(TAG, ie.getMessage());
        } catch (ExecutionException ee) {
            Log.d(TAG, ee.getMessage());
        }*/
        return new ArrayList<>();
    }
}
