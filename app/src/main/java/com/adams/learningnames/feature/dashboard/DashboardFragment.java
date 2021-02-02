package com.adams.learningnames.feature.dashboard;

import android.os.Bundle;
import android.view.View;

import com.adams.learningnames.R;
import com.adams.learningnames.feature.category.CategoryEditFragment;
import com.adams.learningnames.feature.learning.LearningFragment;
import com.adams.learningnames.helper.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-19
 */
public class DashboardFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.dashboard;
    }

    @Override
    public Object getToolbarTitle() {
        return R.string.dashboard__title;
    }


    @Override
    protected void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.init(view, savedInstanceState);

    }

    @OnClick(R.id.dashboard__try)
    void onTryClick() {
        getFragmentSwitcher().replace(LearningFragment.class, true);
    }

    @OnClick(R.id.dashboard__new)
    void onNewClick() {
        getFragmentSwitcher().replace(CategoryEditFragment.class, true);
    }

    @OnClick(R.id.dashboard__challenge__button)
    void onChallengeClick() {
        getFragmentSwitcher().replace(LearningFragment.class, true);
    }

    @OnClick(R.id.dashboard__continue__button)
    void onContinueClick() {
        getFragmentSwitcher().replace(LearningFragment.class, true);
    }
}

