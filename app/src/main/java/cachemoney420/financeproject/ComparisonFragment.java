package cachemoney420.financeproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

public class ComparisonFragment extends Fragment {

    private static final String ARG_FINANCE_ID = "finance_id";
    private GraphView mGraphView;

    private Comparison mComparison;

    private List<Double> mRatioList;
    private TextView mRankDisplay;
    private TextView mRatioDisplay;

    public static ComparisonFragment newInstance(UUID financeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FINANCE_ID, financeId);

        ComparisonFragment fragment = new ComparisonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID comparisonId = (UUID) getArguments().getSerializable(ARG_FINANCE_ID);
        mComparison = ComparisonList.get(getActivity()).getComparison(comparisonId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comparison, container, false);
        mGraphView = (GraphView) v.findViewById(R.id.graph);
        new RatioTask().execute();

//        mGraphView = (GraphView) v.findViewById(R.id.graph);
//
//        try {
//            mRatioList = new RatioTask().execute().get();
//        } catch (ExecutionException | InterruptedException e) {
//            mRatioList = new ArrayList<>();
//        }
//        System.out.println(mRatioList);
//        DataPoint[] data = new DataPoint[mRatioList.size()];
//        double x = 0;
//        for (int index = 0; index < mRatioList.size(); index++) {
//            DataPoint d = new DataPoint(x, mRatioList.get(index));
//            data[index] = d;
//            x++;
//        }
//        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(data);
//        lineGraphSeries.setDrawBackground(true);
//        mGraphView.addSeries(lineGraphSeries);
//        mGraphView.setTitle(mComparison.getOverweight() + " vs " + mComparison.getUnderweight());
//        mGraphView.getViewport().setXAxisBoundsManual(true);
//        mGraphView.getViewport().setMaxX(255);
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(mGraphView);
//        Calendar cal = Calendar.getInstance();
//        Date today = cal.getTime();
//        cal.add(Calendar.YEAR, -1);
//        DateFormat newDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
//        staticLabelsFormatter.setHorizontalLabels(
//                new String[] {newDate.format(cal.getTime()), newDate.format(today)}
//        );
//        mGraphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        mRankDisplay = (TextView) v.findViewById(R.id.displayRank);
        mRankDisplay.setText(mComparison.getRank().toString());
        mRatioDisplay = (TextView) v.findViewById(R.id.displayRatio);
        mRatioDisplay.setText(mComparison.getRatio().toString());

        return v;
    }

    private class RatioTask extends AsyncTask<Void, Void, List<Double>> {
        @Override
        protected List<Double> doInBackground(Void... params) {
            try {
                Stock over = YahooFinance.get(mComparison.getOverweight(), Interval.DAILY);
                Stock under = YahooFinance.get(mComparison.getUnderweight(), Interval.DAILY);
                return Compare.getHistoryRatio(over, under, false);
            } catch(IOException ioe) {
                this.cancel(true);
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Double> result) {
            mRatioList = result;
            System.out.println(mRatioList);
            DataPoint[] data = new DataPoint[mRatioList.size()];
            double x = 0;
            for (int index = 0; index < mRatioList.size(); index++) {
                DataPoint d = new DataPoint(x, mRatioList.get(index));
                data[index] = d;
                x++;
            }
            LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(data);
            lineGraphSeries.setDrawBackground(true);
            mGraphView.addSeries(lineGraphSeries);
            mGraphView.setTitle(mComparison.getOverweight() + " vs " + mComparison.getUnderweight());
            mGraphView.getViewport().setXAxisBoundsManual(true);
            mGraphView.getViewport().setMaxX(255);
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(mGraphView);
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.YEAR, -1);
            DateFormat newDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            staticLabelsFormatter.setHorizontalLabels(
                    new String[] {newDate.format(cal.getTime()), newDate.format(today)}
            );
            mGraphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        }
    }
}
