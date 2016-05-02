package cachemoney420.financeproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.UUID;

public class ComparisonFragment extends Fragment {

    private static final String ARG_FINANCE_ID = "finance_id";
    private SQLiteDatabase mDatabase;
    private Comparison mComparison;

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
        return v;
    }
}
