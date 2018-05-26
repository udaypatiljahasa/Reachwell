package com.am.reachwell;

import android.app.Application;
import android.content.Context;

import com.am.reachwell.Global.Depedencies.component.ApplicationComponent;
import com.am.reachwell.Global.Depedencies.component.DaggerApplicationComponent;
import com.am.reachwell.Global.Depedencies.module.ApplicationModule;

public class ReachwellApplication extends Application {
    protected ApplicationComponent applicationComponent;

    public static ReachwellApplication  get(Context context) {
        return (ReachwellApplication) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
