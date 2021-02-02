package com.adams.learningnames.feature.category;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adams.learningnames.R;
import com.adams.learningnames.data.ServiceLocator;
import com.adams.learningnames.helper.BaseFragment;
import com.adams.learningnames.helper.viewholder.EasyRecyclerAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.MaskTransformation;
import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-20
 */
public class CategoryEditFragment extends BaseFragment {

    @BindView(R.id.category__edit)
    protected RecyclerView recyclerView;

    @Override
    protected int getLayoutRes() {
        return R.layout.category__edit;
    }


    @Override
    public Object getToolbarTitle() {
        return R.string.category__edit_title;
    }

    @NonNull
    @Override
    protected MenuEntry[] getMenuEntries() {
        return new MenuEntry[]{
                new MenuEntry(R.string.category__edit_deletecategory,
                        item -> {
                            Timber.i("delete category clicked");
                            return true;
                        })
        };
    }

    @Override
    protected void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.init(view, savedInstanceState);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);


        EasyRecyclerAdapter<CategoryService.CategoryItem> adapter = new EasyRecyclerAdapter<>(
                R.layout.category__edit_item,
                ServiceLocator.getCategoryService(getContext()).getCategories(),
                CategoryViewHolder::new
        );
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.category__edit_add)
    void onAddClick() {
        Timber.i("add clicked");
    }

    class CategoryViewHolder extends EasyRecyclerAdapter.EasyRecyclerViewHolder<CategoryService.CategoryItem> {

        @BindView(R.id.category__edit_item_name)
        TextView textView;
        @BindView(R.id.category__edit_item_image)
        ImageView imageView;

        CategoryViewHolder(@NonNull View holderView) {
            super(holderView);
        }

        @Override
        public void apply(CategoryService.CategoryItem categoryItem) {
            textView.setText(categoryItem.name);

            Transformation transformation = new MaskTransformation(getContext(),
                    R.drawable.image_mask_rounded_corner_10dp);

            Picasso.get()
                    .load(categoryItem.imageUrl)
                    .transform(transformation)
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

        @OnClick(R.id.category__edit_item_delete)
        void onDeleteItemClick() {
            Timber.i("delete clicked");
        }

        @OnClick(R.id.category__edit_item_image)
        void onImageClick() {
            Timber.i("Image clicked");
        }

        @OnClick(R.id.category__edit_item_name)
        void onNameClick() {
            Timber.i("Name clicked");
        }
    }
}
