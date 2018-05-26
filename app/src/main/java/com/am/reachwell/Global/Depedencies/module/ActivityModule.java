/**
 * Jahasa Technology all rights reserved
 *
 * @author Uday
 * @since 01.02.2018
 *
 * Module Class that provides the activity level dependency.
 *
 */

package com.am.reachwell.Global.Depedencies.module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.am.reachwell.Global.Depedencies.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity _activity) {
        activity = _activity;
    }

    /*
     * Provides activity context
     */
    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    /*
     * Provides activity
     */
    @Provides
    Activity provideActivity() {
        return activity;
    }


}