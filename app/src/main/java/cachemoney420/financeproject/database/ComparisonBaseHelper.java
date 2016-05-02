package cachemoney420.financeproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ComparisonBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "comparisonBase.db";

    public ComparisonBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ComparisonDbSchema.ComparisonTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
                ComparisonDbSchema.ComparisonTable.Cols.UUID + ", " +
                ComparisonDbSchema.ComparisonTable.Cols.OVER + ", " +
                ComparisonDbSchema.ComparisonTable.Cols.UNDER+ ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
