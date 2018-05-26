/**
 * Jahasa Technology all rights reserved
 *
 * @author Uday
 * @since 01.02.2018
 *
 * Module Class that provides the application level dependency.
 *
 */

package com.am.reachwell.Global.Depedencies.module;

import android.app.Application;
import android.content.Context;

import com.am.reachwell.Global.Depedencies.ApplicationContext;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application application;


    public ApplicationModule(Application app) {
        application = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    RequestQueue provideRequestQueue(){
       return Volley.newRequestQueue(application);
    }
}
