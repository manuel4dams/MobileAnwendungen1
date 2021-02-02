package com.adams.learningnames.data;

import android.content.Context;

import com.adams.learningnames.feature.category.CategoryService;
import com.adams.learningnames.feature.learning.LearningService;

/**
 * @author Manuel Adams
 * @since 2019-03-06
 */
public class ServiceLocator {

    private static CategoryService categoryService = null;
    private static LearningService learningService = null;

    public static synchronized CategoryService getCategoryService(Context context) {
        if (categoryService == null)
            categoryService = new CategoryServiceImplementation(context);
        return categoryService;
    }

    public static synchronized LearningService getLearningService(Context context) {
        if (learningService == null)
            learningService = new LearningServiceImplementation(context);
        return learningService;
    }
}
