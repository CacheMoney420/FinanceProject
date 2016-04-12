package cachemoney420.financeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cachemoney420.financeproject.database.ComparisonBaseHelper;
import cachemoney420.financeproject.database.ComparisonCursorWrapper;
import cachemoney420.financeproject.database.ComparisonDbSchema.ComparisonTable;

public class ComparisonLab {
    private static ComparisonLab sComparisonLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ComparisonLab get(Context context) {
        if (sComparisonLab == null) {
            sComparisonLab = new ComparisonLab(context);
        }
        return sComparisonLab;
    }

    private ComparisonLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ComparisonBaseHelper(mContext).getWritableDatabase();
    }

    public void addComparison(Comparison c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(ComparisonTable.NAME, null, values);
    }

    public List<Comparison> getComparisons() {
        List<Comparison> comparisons = new ArrayList<>();

        ComparisonCursorWrapper cursor = queryComparisons(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                comparisons.add(cursor.getComparison());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return comparisons;
    }

    public Comparison getComparison(UUID id) {
        ComparisonCursorWrapper cursor = queryComparisons(
                ComparisonTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getComparison();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Comparison comparison) {
        ContentValues values = new ContentValues();
        values.put(ComparisonTable.Cols.UUID, comparison.getId().toString());
        values.put(ComparisonTable.Cols.TAG1, comparison.getTag1().toString());
        values.put(ComparisonTable.Cols.TAG2, comparison.getTag2().toString());
        values.put(ComparisonTable.Cols.RATIO, comparison.getRatio().toString());
        values.put(ComparisonTable.Cols.RANK, comparison.getRank().toString());

        return values;
    }

    private ComparisonCursorWrapper queryComparisons(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ComparisonTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ComparisonCursorWrapper(cursor);
    }
}
