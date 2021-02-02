package com.adams.learningnames.feature.category;

import java.util.List;

/**
 * @author Manuel Adams
 * @since 2019-03-03
 */
public interface CategoryService {

    List<CategoryItem> getCategories();

    boolean hasCategories();

    CategoryItem getCategory(String name);

    void addCategory(CategoryItem category);

    //void editCategory(String name, CategoryItem category);

    //void deleteCategory(String name);

    class CategoryItem {

        public String name;
        public String imageUrl;
        public float progress;
        // TODO List of images (like in LearningService)


        @SuppressWarnings("unused") // Due to Jackson
        private CategoryItem() {
            // Field should be final
            // Data handling should not change items defined in the feature
        }

        public CategoryItem(String name, String imageUrl, float progress) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.progress = progress;
        }
    }
}
