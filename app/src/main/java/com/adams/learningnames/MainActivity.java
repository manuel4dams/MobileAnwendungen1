package com.adams.learningnames;

import android.os.Bundle;

import com.adams.learningnames.feature.home.HomeFragment;
import com.adams.learningnames.helper.BaseActivity;
import com.adams.learningnames.helper.BaseFragment;
import com.adams.learningnames.helper.FragmentSwitcher;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

// TODO Rename LearningNamesActivity?
public class MainActivity extends BaseActivity {

    private FragmentSwitcher<BaseFragment> fragmentSwitcher;

    @BindView(R.id.main__toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.main__toolbar_decoration)
    protected AppBarLayout toolbarDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set initial view and bind
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        // Initialize the toolbar as our menu holder
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        // Initialize the fragment switcher
        fragmentSwitcher = new FragmentSwitcher<>(this, getSupportFragmentManager(), R.id.main__content);

        // Show back button if back stack not empty
        getSupportFragmentManager().addOnBackStackChangedListener(() ->
                Objects.requireNonNull(getSupportActionBar())
                        .setDisplayHomeAsUpEnabled(fragmentSwitcher.getBackStackEntryCount() > 0));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        fragmentSwitcher.replace(HomeFragment.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Delegate the navigate up to our fragment switcher
        fragmentSwitcher.popBackStack();
        return super.onSupportNavigateUp();
    }

    public FragmentSwitcher<BaseFragment> getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public AppBarLayout getToolbarDecoration() {
        return toolbarDecoration;
    }
}
