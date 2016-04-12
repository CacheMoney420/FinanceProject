package cachemoney420.financeproject;

import android.support.v4.app.Fragment;

public class ComparisonListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new FinanceActivity();
    }
}
