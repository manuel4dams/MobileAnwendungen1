package com.adams.learningnames.helper;

import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Manuel Adams
 * @since 2019-02-19
 */
public abstract class BaseActivity extends AppCompatActivity {

    public abstract FragmentSwitcher<BaseFragment> getFragmentSwitcher();

    public abstract Toolbar getToolbar();

    public abstract AppBarLayout getToolbarDecoration();
}
