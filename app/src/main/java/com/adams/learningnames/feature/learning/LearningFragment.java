package com.adams.learningnames.feature.learning;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.adams.learningnames.R;
import com.adams.learningnames.data.ServiceLocator;
import com.adams.learningnames.feature.category.CategoryEditFragment;
import com.adams.learningnames.helper.BaseFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindViews;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.MaskTransformation;
import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-20
 */
public class LearningFragment extends BaseFragment {

    @BindViews({R.id.learning__image1, R.id.learning__image2, R.id.learning__image3, R.id.learning__image4})
    List<ImageView> imageViews;

    @Override
    protected int getLayoutRes() {
        return R.layout.learning;
    }

    @Override
    public Object getToolbarTitle() {
        Bundle bundle = getFragmentSwitcher().getCurrentFragment().getArgumentsNotNull();
        return bundle.getString("category");
    }

    @NonNull
    @Override
    protected MenuEntry[] getMenuEntries() {
        return new MenuEntry[]{
                new MenuEntry(R.string.learning__menu_categoryedit,
                        item -> {
                            Timber.i("category edit clicked");
                            getFragmentSwitcher().replace(CategoryEditFragment.class, true);
                            return true;
                        })
        };
    }

    @Override
    protected void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Use the global layout listener to have the image views width and height required
        // for centerCrop (because it needs a resize call) by Picasso
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                initializeImages();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initializeImages() {
        Transformation transformation = new MaskTransformation(getContext(),
                R.drawable.image_mask_rounded_corner_10dp);

        String[] paths = ServiceLocator.getLearningService(getContext()).getImagePaths("Tiere");
        for (int i = 0; i < imageViews.size(); i++) {
            Picasso.get()
                    .load(paths[i])
                    .resize(imageViews.get(i).getWidth(), imageViews.get(i).getHeight())
                    .centerCrop()
                    .transform(transformation)
                    .into(imageViews.get(i), new Callback() {
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
    }

    @OnClick(R.id.learning__image1)
    void onImage1Click() {
        Timber.i("image1 clicked");
    }

    @OnClick(R.id.learning__image2)
    void onImage2Click() {
        Timber.i("image2 clicked");
    }

    @OnClick(R.id.learning__image3)
    void onImage3Click() {
        Timber.i("image3 clicked");
    }

    @OnClick(R.id.learning__image4)
    void onImage4Click() {
        Timber.i("image4 clicked");
    }

    @OnClick(R.id.learning__edit)
    void onEditClick() {
        getFragmentSwitcher().replace(CategoryEditFragment.class, true);
    }
}
