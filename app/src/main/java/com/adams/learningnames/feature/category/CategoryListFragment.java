package com.adams.learningnames.feature.category;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adams.learningnames.R;
import com.adams.learningnames.data.ServiceLocator;
import com.adams.learningnames.feature.learning.LearningFragment;
import com.adams.learningnames.helper.BaseFragment;
import com.adams.learningnames.helper.viewholder.EasyRecyclerAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-20
 */
public class CategoryListFragment extends BaseFragment {

    @BindView(R.id.category__list)
    protected RecyclerView recyclerView;

    @BindView(R.id.category__list_empty)
    protected View recyclerEmptyView;

    @Override
    protected int getLayoutRes() {
        return R.layout.category__list;
    }

    @Override
    protected void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.init(view, savedInstanceState);

        CategoryService categoryService = ServiceLocator.getCategoryService(getContext());
        if (categoryService.hasCategories()) {
            initWithCategories(categoryService.getCategories());
        } else {
            recyclerView.setVisibility(View.GONE);
            recyclerEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void initWithCategories(List<CategoryService.CategoryItem> categories) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        EasyRecyclerAdapter<CategoryService.CategoryItem> adapter = new EasyRecyclerAdapter<>(
                R.layout.category__list_item,
                categories,
                CategoryViewHolder::new
        );
        recyclerView.setAdapter(adapter);
    }

    class CategoryViewHolder extends EasyRecyclerAdapter.EasyRecyclerViewHolder<CategoryService.CategoryItem> {

        @BindView(R.id.category__list_item__name)
        TextView textView;
        @BindView(R.id.category__list_item__image)
        ImageView imageView;

        CategoryViewHolder(@NonNull View holderView) {
            super(holderView);
        }

        @Override
        public void apply(CategoryService.CategoryItem categoryItem) {
            textView.setText(categoryItem.name);
            Picasso.get()
                    .load(categoryItem.imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Timber.i("Success loading image");
                        }

                        @Override
                        public void onError(Exception e) {
                            Timber.e(e);
                        }
                    });
        }

        @OnClick(R.id.category__list_item)
        void onListItemClick() {
            Bundle args = new Bundle();
            args.putString("category", getData().name);
            getFragmentSwitcher().replace(LearningFragment.class, true, args,
                    R.anim.right_in,
                    R.anim.left_out,
                    R.anim.right_out,
                    R.anim.left_in);
        }
    }

    @OnClick(R.id.category__list_add)
    void onAddClick() {
        getFragmentSwitcher().replace(CategoryEditFragment.class, true);
    }
}
