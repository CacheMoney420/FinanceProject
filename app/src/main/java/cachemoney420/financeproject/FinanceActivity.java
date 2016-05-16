package cachemoney420.financeproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cachemoney420.financeproject.database.ComparisonBaseHelper;
import cachemoney420.financeproject.database.ComparisonDbSchema;


public class FinanceActivity extends Fragment {

    private static final String DIALOG_ADD = "DialogAdd";
    private static final String DIALOG_DELETE = "DialogDelete";

    private static final int REQUEST_TICKER = 0;
    private static final int REQUEST_TICKER2 = 1;

    private RecyclerView mComparisonRecyclerView;
    private ComparisonAdapter mAdapter;
    private List<Comparison> mComparisons;
    private SQLiteDatabase mDatabase;
    private ArrayList<String> mOver;
    private ArrayList<String> mUnder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabase = new ComparisonBaseHelper(this.getActivity()).getWritableDatabase();
        mOver = new ArrayList<>();
        mUnder = new ArrayList<>();
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_finance, container, false);

        mComparisonRecyclerView = (RecyclerView) view.findViewById(R.id.comparison_recycler_view);
        mComparisonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

//        mOver = new ArrayList<String>() {{
//            add("WMT");
//            add("TGT");
//            add("AAL");
//        }
//        };
//        mUnder = new ArrayList<String>() {{
//            add("MCD");
//            add("BUD");
//            add("AXP");
//        }
//        };

//        Compare compare = new Compare();

        runComparison();

        return view;
    }

    private void runComparison() {
        if (mOver.size() != 0 && mUnder.size() != 0) {
            String[] over = new String[mOver.size()];
            over = mOver.toArray(over);
            String[] under = new String[mUnder.size()];
            under = mUnder.toArray(under);
            List<Pair<Pair<Double, Integer>, Pair<String, String>>> comp = Compare.getCompare(over, under);

            for (int i = 0; i < comp.size(); i++) {
                Comparison c = new Comparison();
                c.setOverweight(comp.get(i).second.first);
                c.setUnderweight(comp.get(i).second.second);
                c.setRatio(comp.get(i).first.first);
                c.setRank(comp.get(i).first.second);
                addComparison(c);
            }
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_finance, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_ticker:
                FragmentManager manager = getFragmentManager();
                AddDialog dialog = AddDialog.newInstance(mOver, mUnder);
                dialog.setTargetFragment(FinanceActivity.this, REQUEST_TICKER);
                dialog.show(manager, DIALOG_ADD);
                return true;
            case R.id.menu_item_delete_ticker:
                FragmentManager manager1 = getFragmentManager();
                DeleteDialog dialog1 = DeleteDialog.newInstance(((ArrayList<String>) mOver.clone()), ((ArrayList<String>) mUnder.clone()));
                dialog1.setTargetFragment(this, REQUEST_TICKER2);
                dialog1.show(manager1, DIALOG_DELETE);
                return true;
            case R.id.menu_item_refresh:
//                mDatabase.delete(ComparisonDbSchema.ComparisonTable.NAME, null, null);
                mComparisons.clear();
                updateUI();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {

        runComparison();

        if (mAdapter == null) {
            mAdapter = new ComparisonAdapter(mComparisons);
            mComparisonRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setComparisons(mComparisons);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void addComparison(Comparison comparison) {
        mComparisons.add(comparison);
        ComparisonList.get(getActivity()).addComparison(comparison);
        ContentValues values = getContentValues(comparison);
        mDatabase.insert(ComparisonDbSchema.ComparisonTable.NAME, null, values);
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
            mRank.setText("Rank: " + mComparison.getRank() + " | Exchange Rate: " + mComparison.getRatio());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ComparisonPagerActivity.newIntent(getActivity(), mComparison.getId());
            startActivity(intent);
        }
    }

    private class ComparisonAdapter extends RecyclerView.Adapter<ComparisonHolder> {

        public ComparisonAdapter(List<Comparison> comparisons) {
            if (comparisons == null) {
                comparisons = new ArrayList<>();
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_TICKER) {
            mComparisons.clear();
            updateUI();
        }

        if (requestCode == REQUEST_TICKER2) {
            String ticker = (String) data.getSerializableExtra(DeleteDialog.EXTRA_TICKER);
            if (ticker != null) {
//                mDatabase.delete(ComparisonDbSchema.ComparisonTable.NAME, null, null);
                if (!mOver.remove(ticker)) {
                    mUnder.remove(ticker);
                }
                mComparisons.clear();
                runComparison();
            } else {
//                mDatabase.delete(ComparisonDbSchema.ComparisonTable.NAME, null, null);
                mComparisons.clear();
            }
            updateUI();
        }
    }

    private static ContentValues getContentValues(Comparison comparison) {
        ContentValues values = new ContentValues();
        values.put(ComparisonDbSchema.ComparisonTable.Cols.UUID, comparison.getId().toString());
        values.put(ComparisonDbSchema.ComparisonTable.Cols.OVER, comparison.getOverweight().toString());
        values.put(ComparisonDbSchema.ComparisonTable.Cols.UNDER, comparison.getUnderweight().toString());

        return values;
    }
}