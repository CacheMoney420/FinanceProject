package cachemoney420.financeproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.math.BigDecimal;
import java.util.UUID;
import cachemoney420.financeproject.database.ComparisonDbSchema.ComparisonTable;
import cachemoney420.financeproject.Comparison;

public class ComparisonCursorWrapper extends CursorWrapper {
    public ComparisonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Comparison getComparison() {
        String uuidString = getString(getColumnIndex(ComparisonTable.Cols.UUID));
        String over = getString(getColumnIndex(ComparisonTable.Cols.OVER));
        String under = getString(getColumnIndex(ComparisonTable.Cols.UNDER));
        Double ratio = getDouble(getColumnIndex(ComparisonTable.Cols.RATIO));

        Comparison comparison = new Comparison(UUID.fromString(uuidString));
        comparison.setOverweight(over);
        comparison.setUnderweight(under);
        comparison.setRatio(ratio);

        return comparison;
    }
}
