package com.adams.learningnames.feature.home;

import android.os.Bundle;
import android.view.View;

import com.adams.learningnames.R;
import com.adams.learningnames.feature.category.CategoryEditFragment;
import com.adams.learningnames.feature.category.CategoryListFragment;
import com.adams.learningnames.feature.dashboard.DashboardFragment;
import com.adams.learningnames.feature.learning.LearningFragment;
import com.adams.learningnames.helper.BaseFragment;
import com.adams.learningnames.helper.android.TabLayout_OnTabSelectedListenerAdapter;
import com.adams.learningnames.helper.android.ViewPager_OnPageChangeListenerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-20
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.home__tabs)
    protected TabLayout tabLayout;

    @BindView(R.id.home__viewpager)
    protected ViewPager viewPager;

    private float toolbarElevation;

    @Override
    protected int getLayoutRes() {
        return R.layout.home;
    }

    @NonNull
    @Override
    protected MenuEntry[] getMenuEntries() {
        return new MenuEntry[]{
                new MenuEntry(R.string.home__menu__imprint,
                        item -> {
                            Timber.i("Impressum clicked");
                            return true;
                        }
                ),
                new MenuEntry(R.string.home__menu__datasecurity,
                        item -> {
                            Timber.i("DataSecurity clicked");
                            return true;
                        }
                ),
                new MenuEntry(R.string.home__menu__newcategory,
                        item -> {
                            Timber.i("New category clicked");
                            getFragmentSwitcher().replace(CategoryEditFragment.class, true);
                            return true;
                        }
                )
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarElevation = getToolbarDecoration().getTargetElevation() != 0
                ? getToolbarDecoration().getTargetElevation()
                : toolbarElevation;
        getToolbarDecoration().setTargetElevation(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        getToolbarDecoration().setTargetElevation(toolbarElevation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(),
                new BaseFragment[]{new DashboardFragment(), new CategoryListFragment()});
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.fragments.length);

        setupTabLayoutNavigation();
    }

    private void setupTabLayoutNavigation() {
        tabLayout.addOnTabSelectedListener(new TabLayout_OnTabSelectedListenerAdapter() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager_OnPageChangeListenerAdapter() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position, positionOffset, false);
            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        });
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        private final BaseFragment[] fragments;

        private PagerAdapter(FragmentManager fragmentManager, BaseFragment[] fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Object title = fragments[position].getToolbarTitle();
            if (title instanceof Integer)
                return getResources().getString((int) title);
            else
                return (CharSequence) title;
        }
    }
}
