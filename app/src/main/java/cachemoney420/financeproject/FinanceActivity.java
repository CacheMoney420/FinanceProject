package cachemoney420.financeproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import cachemoney420.financeproject.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FinanceActivity extends Fragment {

    private RecyclerView mComparisonRecyclerView;
    private ComparisonAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_finance, container, false);

        mComparisonRecyclerView = (RecyclerView) view.findViewById(R.id.comparison_recycler_view);
        mComparisonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] list1 = {"WMT", "TGT", "SAMS"};
        String[] list2 = {"MCD", "BUD", "BKW"};

//        Compare compare = new Compare();
//        List<Pair<Pair<Double, Integer>, Pair<String, String>>> comp = compare.getCompare(list1, list2);
//
//        for (int i = 0; i < comp.size(); i++) {
//            Comparison c = new Comparison();
//            c.setOverweight(comp.get(i).second.first);
//            c.setUnderweight(comp.get(i).second.second);
//            c.setRatio(comp.get(i).first.first);
//            c.setRank(comp.get(i).first.second);
//            addComparison(c);
//        }

        List<Pair<Pair<Double,Integer>,Pair<String,String>>> test = new ArrayList<Pair<Pair<Double,Integer>,Pair<String,String>>>();

        for (int i = 0; i < 10; i++) {
            Comparison c = new Comparison();
            Pair a = new Pair(5.00/(i+1), 1);
            Pair b = new Pair("abc" + i, "def" + i);
            test.add(new Pair(a, b));
            c.setOverweight(test.get(i).getRight().getLeft());
            c.setUnderweight(test.get(i).getRight().getRight());
            c.setRatio(test.get(i).getLeft().getLeft());
            c.setRank(test.get(i).getLeft().getRight());
            addComparison(c);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void updateUI() {
        ComparisonLab comparisonLab = ComparisonLab.get(getActivity());
        List<Comparison> comparisons = comparisonLab.getComparisons();

        if (mAdapter == null) {
            mAdapter = new ComparisonAdapter(comparisons);
            mComparisonRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setComparisons(comparisons);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void addComparison(Comparison comparison) {
        ComparisonLab.get(getActivity()).addComparison(comparison);
    }

    private class ComparisonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Comparison mComparison;
        private TextView mTitle;
        private TextView mRank;

        public ComparisonHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitle = (TextView) itemView.findViewById(R.id.comparison_title);
            mRank = (TextView) itemView.findViewById(R.id.comparison_rank);
        }

        public void bindComparison(Comparison comparison) {
            mComparison = comparison;
            mTitle.setText(mComparison.getOverweight() + " vs " + mComparison.getUnderweight());
            mRank.setText(mComparison.getRank() + " | " + mComparison.getRatio());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ComparisonPagerActivity.newIntent(getActivity(), mComparison.getId());
            startActivity(intent);
        }
    }

    private class ComparisonAdapter extends RecyclerView.Adapter<ComparisonHolder> {

        private List<Comparison> mComparisons;

        public ComparisonAdapter(List<Comparison> comparisons) {
            mComparisons = comparisons;
        }

        @Override
        public ComparisonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_comparison, parent, false);
            return new ComparisonHolder(view);
        }

        @Override
        public void onBindViewHolder (ComparisonHolder holder, int position) {
            Comparison comparison = mComparisons.get(position);
            holder.bindComparison(comparison);
        }

        @Override
        public int getItemCount() {
            return mComparisons.size();
        }

        public void setComparisons(List<Comparison> comparisons) {
            mComparisons = comparisons;
        }
    }
}