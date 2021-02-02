package com.adams.learningnames.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

/**
 * @author Manuel Adams
 * @since 2019-03-06
 */
public class BasicService {

    final Context context;

    BasicService(Context context) {
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("LearningNames", Context.MODE_PRIVATE);
    }

    protected AssetManager getAssetManager() {
        return context.getAssets();
    }
}
