package com.adams.learningnames.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.adams.learningnames.R;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Michael Ruf
 * @since 2015-05-23
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private boolean initialized;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuEntry[] entries = getMenuEntries();
        for (MenuEntry entry : entries) {
            MenuItem item = null;
            if (entry.title instanceof String) {
                item = menu.add((String) entry.title);
            } else if (entry.title instanceof Integer) {
                item = menu.add((int) entry.title);
            }
            if (item != null) {
                if (entry.iconRes != 0) {
                    item.setIcon(entry.iconRes);
                    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }
                item.setOnMenuItemClickListener(entry.listener);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public synchronized void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!initialized) {
            initialized = true;
            init(view, savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handleToolbar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getLayoutRes();

    @NonNull
    protected MenuEntry[] getMenuEntries() {
        return new MenuEntry[0];
    }

    public Object getToolbarTitle() {
        return R.string.app_name;
    }

    private void handleToolbar() {
        // We do not want to change the toolbar in nested fragments
        if (isNestedFragment())
            return;

        Object toolbarTitle = getToolbarTitle();
        if (toolbarTitle != null) {
            getToolbar().setVisibility(View.VISIBLE);
            if (toolbarTitle instanceof Integer)
                getToolbar().setTitle((int) toolbarTitle);
            else
                getToolbar().setTitle((CharSequence) toolbarTitle);
        } else {
            getToolbar().setVisibility(View.GONE);
        }
    }

    protected void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Method stub for overloading by initialization code
    }

    /**
     * Takes the toolbar from the base activity
     *
     * @return Main activity fragment switcher
     */
    public Toolbar getToolbar() {
        return ((BaseActivity) Objects.requireNonNull(getActivity())).getToolbar();
    }

    public AppBarLayout getToolbarDecoration() {
        return ((BaseActivity) Objects.requireNonNull(getActivity())).getToolbarDecoration();
    }

    /**
     * Takes the support fragment switcher from the main activity
     *
     * @return Main activity fragment switcher
     */
    public FragmentSwitcher<BaseFragment> getFragmentSwitcher() {
        return ((BaseActivity) Objects.requireNonNull(getActivity())).getFragmentSwitcher();
    }

    /**
     * Returns the arguments bundle for this fragment instance.
     * If they are null, an empty bundle gets returned.
     *
     * @return The arguments or a empty bundle if no arguments given
     */
    public Bundle getArgumentsNotNull() {
        return getArguments() == null ? new Bundle() : super.getArguments();
    }

    /**
     * Returns whether the fragment is nested in another one.
     *
     * @return Whether the fragment is nested
     */
    public boolean isNestedFragment() {
        try {
            return getParentFragment() != null && getParentFragment() != this;
        } catch (Throwable e) {
            return false;
        }
    }

    protected static final class MenuEntry {

        private final Object title;
        private final int iconRes;
        private final MenuItem.OnMenuItemClickListener listener;

        public MenuEntry(Object title, MenuItem.OnMenuItemClickListener listener) {
            this(title, 0, listener);
        }

        public MenuEntry(Object title, int iconRes, MenuItem.OnMenuItemClickListener listener) {
            this.title = title;
            this.iconRes = iconRes;
            this.listener = listener;
        }
    }
}
