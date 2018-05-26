package com.am.reachwell.User.Dependencies.component;

import com.am.reachwell.Global.Depedencies.PerActivity;
import com.am.reachwell.Global.Depedencies.component.ActivityComponent;
import com.am.reachwell.Global.Depedencies.component.ApplicationComponent;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.User.Dependencies.module.UserModule;
import com.am.reachwell.User.Views.Activity.DashboardActivity;
import com.am.reachwell.User.Views.Activity.LoginActivity;

import dagger.Component;

/**
 * Created by udaypatil on 20/02/18.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(LoginActivity signUpSignInActivity);
    void inject(DashboardActivity signInFragment);

}
