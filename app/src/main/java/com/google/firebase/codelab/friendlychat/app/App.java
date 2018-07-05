package com.google.firebase.codelab.friendlychat.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.log(activity.getClass().getSimpleName() + " onCreate");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.log(activity.getClass().getSimpleName() + " onStart");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.log(activity.getClass().getSimpleName() + " onResume");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.log(activity.getClass().getSimpleName() + " onPause");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.log(activity.getClass().getSimpleName() + " onStop");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.log(activity.getClass().getSimpleName() + " onDestroy");
            }
        });
    }
}
