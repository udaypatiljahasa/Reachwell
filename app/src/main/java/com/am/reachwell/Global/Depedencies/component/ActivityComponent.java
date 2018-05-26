/**
 * Jahasa Technology all rights reserved
 *
 * @author Uday
 * @since 03.02.2018
 *
 * Activity Component class, contians the activities where dependency need to be injected and functions to get the dependencies.
 *
 *
 */

package com.am.reachwell.Global.Depedencies.component;




import com.am.reachwell.Global.Helpers.DialogHelper;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Services.BackgroundServices.SyncData;
import com.am.reachwell.User.Views.Activity.DashboardActivity;
import com.am.reachwell.Global.Depedencies.PerActivity;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.User.Views.Activity.LoginActivity;


import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(DashboardActivity dashboardActivity);

    DialogHelper getDialogHelper();
//    NetworkHelper getNetworkHelper();
}
