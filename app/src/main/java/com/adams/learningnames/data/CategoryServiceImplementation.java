package com.adams.learningnames.data;

import android.annotation.SuppressLint;
import android.content.Context;

import com.adams.learningnames.data.helper.JsonHelper;
import com.adams.learningnames.feature.category.CategoryService;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//    return Arrays.asList(
//            new CategoryItem("Planeten", "file:///android_asset/Kategorien/Planeten/Europa.jpg", 0.1f),
//            new CategoryItem("Planeten", "file:///android_asset/Kategorien/Planeten/Venus.jpg", 0.7f),
//            new CategoryItem("Planeten", "file:///android_asset/Kategorien/Planeten/Uranus.jpg", 0f),
//            new CategoryItem("Planeten", "file:///android_asset/Kategorien/Planeten/Proxima Centauri b.jpg", 1f)
//            );

/**
 * @author Manuel Adams
 * @since 2019-03-03
 */
@SuppressLint("ApplySharedPref")
public class CategoryServiceImplementation extends BasicService implements CategoryService {

    private static final String SHARED_PREFERENCES_DEFAULT_CATEGORY_ADDED = "DefaultCategoryAdded";
    private static final String SHARED_PREFERENCES_CATEGORIES = "Categories";

    CategoryServiceImplementation(Context context) {
        super(context);
        DEV_deleteSharedPref();
        mayAddDefaultCategory();
    }

    @Deprecated // TODO Remove
    private void DEV_deleteSharedPref() {
        getSharedPreferences().edit().clear().commit();
    }

    private void mayAddDefaultCategory() {
        boolean added = getSharedPreferences().getBoolean(
                SHARED_PREFERENCES_DEFAULT_CATEGORY_ADDED, false);
        if (!added) {
            addCategory(getDefaultCategory());
            getSharedPreferences().edit()
                    .putBoolean(SHARED_PREFERENCES_DEFAULT_CATEGORY_ADDED, true)
                    .commit();
        }
    }

    private CategoryItem getDefaultCategory() {
        // NOTE Eliminate hardcoded (use assets)
        // TODO Put correct data in here
        return new CategoryItem("Planeten", "file:///android_asset/Kategorien/Planeten/Europa.jpg", 0.1f);
    }

    @Override
    public List<CategoryItem> getCategories() {
        String categoriesString = getSharedPreferences().getString(SHARED_PREFERENCES_CATEGORIES, null);
        if (categoriesString == null)
            return null;

        CategoryListWrapper categoriesWrapper = JsonHelper.fromJson(categoriesString, CategoryListWrapper.class);
        if (categoriesWrapper == null)
            return null;

        return categoriesWrapper.categories;
    }

    @Override
    public boolean hasCategories() {
        List<CategoryItem> categories = getCategories();
        return categories != null && categories.size() > 0;
    }

    @Override
    public CategoryItem getCategory(String name) {
        List<CategoryItem> categories = getCategories();
        if (categories == null)
            return null;

        return categories.stream()
                .filter(categoryItem -> categoryItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addCategory(CategoryItem category) {
        // Receive current categories
        List<CategoryItem> categories = getCategories();
        if (categories == null)
            categories = new ArrayList<>();

        // Add the category
        categories.add(category);

        // Save the categories
        String categoriesString = JsonHelper.toJson(new CategoryListWrapper(categories));
        getSharedPreferences().edit()
                .putString(SHARED_PREFERENCES_CATEGORIES, categoriesString)
                .commit();
    }

    private static class CategoryListWrapper {

        @JsonProperty
        private List<CategoryItem> categories;

        @SuppressWarnings("unused") // Due to Jackson
        private CategoryListWrapper() {
        }

        private CategoryListWrapper(List<CategoryItem> categories) {
            this.categories = categories;
        }
    }
}
