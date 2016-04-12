package cachemoney420.financeproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class ComparisonPagerActivity extends AppCompatActivity {
    private static final String EXTRA_COMPARE_ID = "cachemoney420.financeproject.compare_id";

    private ViewPager mViewPager;
    private List<Comparison> mComparisons;

    public static Intent newIntent(Context packageContext, UUID comparisonId) {
        Intent intent = new Intent(packageContext, ComparisonPagerActivity.class);
        intent.putExtra(EXTRA_COMPARE_ID, comparisonId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_pager);

        UUID comparisonId = (UUID) getIntent().getSerializableExtra(EXTRA_COMPARE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_comparison_pager_view_pager);

        ///mComparisons write this
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Comparison comparison = mComparisons.get(position);
                return ComparisonFragment.newInstance(comparison.getId());
            }

            @Override
            public int getCount() {
                return mComparisons.size();
            }
        });

        for (int i = 0; i < mComparisons.size(); i++) {
            if (mComparisons.get(i).getId().equals(comparisonId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
