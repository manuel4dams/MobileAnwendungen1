package com.adams.learningnames.data;

import android.content.Context;

import com.adams.learningnames.feature.category.CategoryService;
import com.adams.learningnames.feature.learning.LearningService;

/**
 * @author Manuel Adams
 * @since 2019-02-26
 */
public class LearningServiceImplementation extends BasicService implements LearningService {

    private final CategoryService categoryService;

    private String path1 = "file:///android_asset/Kategorien/Planeten/Erde.jpg";
    private String path2 = "file:///android_asset/Kategorien/Planeten/Saturn.jpg";
    private String path3 = "file:///android_asset/Kategorien/Planeten/Neptun.jpg";
    private String path4 = "file:///android_asset/Kategorien/Planeten/Gliese.jpg";

    LearningServiceImplementation(Context context) {
        super(context);
        categoryService = ServiceLocator.getCategoryService(context);
    }

    @Override
    public String[] getImagePaths(String category) {
//        categoryService.getCategory(category).imagePaths;
        return new String[]{path1, path2, path3, path4};
    }

}
