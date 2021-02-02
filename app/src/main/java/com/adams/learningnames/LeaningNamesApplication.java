package com.adams.learningnames;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * @author Manuel Adams
 * @since 2019-02-19
 */
public class LeaningNamesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Install LeakCanary
        boolean leakCanaryInProgress = InstallLeakCanaryAndCheckIfInProgress();
        if (leakCanaryInProgress)
            return;

        InstallTimber();
        InitializePicasso();
    }

    private boolean InstallLeakCanaryAndCheckIfInProgress() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return true;
        }

        LeakCanary.install(this);
        return false;
    }

    private void InstallTimber() {
        Timber.plant(new Timber.DebugTree());
        //if (BuildConfig.FABRIC_ENABLED) {
        //    Timber.plant(new CrashlyticsTree(this));
        //}
    }

    private void InitializePicasso() {
        if (BuildConfig.DEBUG)
            Picasso.get().setIndicatorsEnabled(true);
    }
}
