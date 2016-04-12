package cachemoney420.financeproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;
import cachemoney420.financeproject.database.ComparisonDbSchema.ComparisonTable;
import cachemoney420.financeproject.Comparison;

public class ComparisonCursorWrapper extends CursorWrapper {
    public ComparisonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Comparison getComparison() {
        String uuidString = getString(getColumnIndex(ComparisonTable.Cols.UUID));
        String tag1 = getString(getColumnIndex(ComparisonTable.Cols.TAG1));
        String tag2 = getString(getColumnIndex(ComparisonTable.Cols.TAG2));
        String ratio = getString(getColumnIndex(ComparisonTable.Cols.RATIO));
        String rank = getString(getColumnIndex(ComparisonTable.Cols.RANK));

        Comparison comparison = new Comparison(UUID.fromString(uuidString));
        comparison.setTag1(tag1);
        comparison.setTag2(tag2);
        comparison.setRatio(ratio);
        comparison.setRank(rank);

        return comparison;
    }
}
