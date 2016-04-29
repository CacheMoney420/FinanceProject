package cachemoney420.financeproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComparisonList {
    private static ComparisonList sComparisonList;
    private List<Comparison> mComparisons;

    public static ComparisonList get(Context context) {
        if (sComparisonList == null) {
            sComparisonList = new ComparisonList(context);
        }
        return sComparisonList;
    }

    private ComparisonList(Context context) {
        mComparisons = new ArrayList<>();
    }

    public void addComparison(Comparison c) {
        mComparisons.add(c);
    }

    public List<Comparison> getComparisons() {
        return mComparisons;
    }

    public Comparison getComparison(UUID id) {
        for (Comparison comparison : mComparisons) {
            if (comparison.getId().equals(id)){
                return comparison;
            }
        }
        return null;
    }
}
