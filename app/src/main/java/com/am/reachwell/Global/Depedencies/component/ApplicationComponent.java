/**
 * Jahasa Technology all rights reserved
 *
 * @author Uday
 * @since 03.02.2018
 *
 * Application Component class, contians the functions which to fetch dependencies which are required at application level.
 *
 *
 */

package com.am.reachwell.Global.Depedencies.component;

import android.app.Application;
import android.content.Context;

import com.am.reachwell.Assets.Models.GRNModel;
import com.am.reachwell.Assets.ViewModel.AssetDetailListViewModel;
import com.am.reachwell.Assets.ViewModel.AssetDetailsViewModel;
import com.am.reachwell.Assets.ViewModel.AssetEditViewModel;
import com.am.reachwell.Assets.ViewModel.CreateAssetFromGRNViewModel;
import com.am.reachwell.Assets.ViewModel.CreateAssetViewModel;
import com.am.reachwell.Assets.ViewModel.GRNItemViewModel;
import com.am.reachwell.Assets.ViewModel.GRNViewModel;
import com.am.reachwell.Global.Depedencies.ApplicationContext;
import com.am.reachwell.Global.Depedencies.module.ApplicationModule;
import com.am.reachwell.Global.Helpers.SharedPreferenceHelper;
import com.am.reachwell.Global.Models.AccountHeadModel;
import com.am.reachwell.Global.Models.AquisitionMethodModel;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.AssetStatusModel;
import com.am.reachwell.Global.Models.ContractTypeModel;
import com.am.reachwell.Global.Services.ApiCalls.VolleyApiCalls;
import com.am.reachwell.Global.Services.BackgroundServices.SyncData;
import com.am.reachwell.ReachwellApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {ApplicationModule.class,AndroidSupportInjectionModule.class})
public interface ApplicationComponent {

    void inject(ReachwellApplication reachwellApplication);
    void inject(SyncData syncData);

    @ApplicationContext
    Context getContext();

    SharedPreferenceHelper getSharedPreferenceHelper();
    VolleyApiCalls getVolleyApiCalls();
    CreateAssetViewModel getCreateAssetViewModel();
    AssetDetailListViewModel getAssetDetailListViewModel();
    GRNItemViewModel getGRNItemViewModel();
    GRNViewModel getGRNViewModel();
    CreateAssetFromGRNViewModel getCreateAssetFromGRNViewModel();
    AssetDetailsViewModel getAssetDetailsViewModel();
    AssetEditViewModel getAssetEditViewModel();
    AssetClassModel getAssetClassModel();
    AssetStatusModel getAssetStatusModel();
    AccountHeadModel getAccountHeadModel();
    AquisitionMethodModel getAquisitionMethodModel();
    ContractTypeModel getContractTypeModel();
    GRNModel getGRNModel();
}
