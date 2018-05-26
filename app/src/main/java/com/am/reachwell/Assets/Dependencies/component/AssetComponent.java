package com.am.reachwell.Assets.Dependencies.component;

import com.am.reachwell.Assets.Dependencies.module.AssetModule;
import com.am.reachwell.Assets.ViewModel.CreateAssetViewModel;
import com.am.reachwell.Assets.Views.Activity.AssetsActivity;
import com.am.reachwell.Assets.Views.Fragment.AssetDetailsFragment;
import com.am.reachwell.Assets.Views.Fragment.AssetDetailsListFragment;
import com.am.reachwell.Assets.Views.Fragment.AssetEditFragment;
import com.am.reachwell.Assets.Views.Fragment.CreateAssetFragment;
import com.am.reachwell.Assets.Views.Fragment.CreateAssetFromGRN;
import com.am.reachwell.Assets.Views.Fragment.GRNFragment;
import com.am.reachwell.Assets.Views.Fragment.GRNItemDialogFragment;
import com.am.reachwell.Global.Depedencies.PerActivity;
import com.am.reachwell.Global.Depedencies.component.ActivityComponent;
import com.am.reachwell.Global.Depedencies.component.ApplicationComponent;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;


import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AssetModule.class})
public interface AssetComponent extends ActivityComponent {
    void inject(CreateAssetFragment createAssetFragment);
    void inject(AssetDetailsListFragment assetDetailsListFragment);
    void inject(GRNFragment grnFragment);
    void inject(AssetsActivity assetsActivity);
    void inject(GRNItemDialogFragment grnItemDialogFragment);
    void inject(CreateAssetFromGRN createAssetFromGRN);
    void inject(AssetDetailsFragment assetDetailsFragment);
    void inject(AssetEditFragment assetEditFragment);
}
