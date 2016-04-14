package cachemoney420.financeproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        View view = inflater.inflate(R.layout.fragment_finance_list, container, false);

        mComparisonRecyclerView = (RecyclerView) view.findViewById(R.id.comparison_recycler_view);
        mComparisonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (int i=0; i < 10; i++) {
            Comparison c = new Comparison();
            c.setTag1("abc" + i);
            c.setTag2("def" + i);
            String r = Float.toString(i/2);
            c.setRatio(r);
            c.setRank(Integer.toString(i+1));
        }

        updateUI();

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

    private class ComparisonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Comparison mComparison;
        private TextView mTitle;
        private TextView mRatio;

        public ComparisonHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitle = (TextView) itemView.findViewById(R.id.comparison_title);
            mRatio = (TextView) itemView.findViewById(R.id.comparison_price);
        }

        public void bindComparison(Comparison comparison) {
            mComparison = comparison;
            mTitle.setText(mComparison.getTag1());
            mRatio.setText(mComparison.getRatio());
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