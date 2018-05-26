package com.am.reachwell.Assets.Views.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.reachwell.Assets.Dependencies.component.AssetComponent;
import com.am.reachwell.Assets.Dependencies.component.DaggerAssetComponent;
import com.am.reachwell.Assets.Views.Fragment.AssetDetailsFragment;
import com.am.reachwell.Assets.Views.Fragment.AssetDetailsListFragment;
import com.am.reachwell.Assets.Views.Fragment.AssetEditFragment;
import com.am.reachwell.Assets.Views.Fragment.CreateAssetFragment;
import com.am.reachwell.Assets.Views.Fragment.CreateAssetFromGRN;
import com.am.reachwell.Assets.Views.Fragment.GRNFragment;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.Global.Views.Activity.BaseActivity;
import com.am.reachwell.R;
import com.am.reachwell.ReachwellApplication;

public class AssetsActivity extends BaseActivity {
    private Toolbar toolbar;
    public boolean assetRefreshList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_create_assets);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeToolBar();

        switch (getIntent().getIntExtra("type",0)){
            case 0:
                addFragment(R.id.asset_frame_layout, new CreateAssetFragment(), null);
                break;
            case 1:
                addFragment(R.id.asset_frame_layout, new AssetDetailsListFragment(), null);
                break;
            case 2:
                break;
            case 3:
                addFragment(R.id.asset_frame_layout, new GRNFragment(), null);
                break;
            case 4:
                addFragment(R.id.asset_frame_layout, new CreateAssetFromGRN(), null);
                break;
        }
    }

    private void initializeToolBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toolbar_layout, null);
        actionBar.setCustomView(view, new android.support.v7.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        view.findViewById(R.id.settings_icon).setVisibility(View.GONE);
    }

    public void attachFragment(String assetId,int type){
        if (type == 0) {
            AssetDetailsFragment assetDetailsFragment = new AssetDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AssetId", assetId);
            assetDetailsFragment.setArguments(bundle);
            addFragment(R.id.asset_frame_layout, assetDetailsFragment, "Asset");
        } else {
            AssetEditFragment assetEditFragment = new AssetEditFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AssetId", assetId);
            assetEditFragment.setArguments(bundle);
            addFragment(R.id.asset_frame_layout, assetEditFragment, "Asset");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private AssetComponent getComponent(){
        return DaggerAssetComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(ReachwellApplication.get(this).getComponent()).build();
    }
}
