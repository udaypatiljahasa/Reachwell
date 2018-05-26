package com.am.reachwell.User.Views.Activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.reachwell.Assets.Views.Activity.AssetsActivity;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.AssetStatusModel;
import com.am.reachwell.Global.Services.BackgroundServices.SyncData;
import com.am.reachwell.R;
import com.am.reachwell.ReachwellApplication;
import com.am.reachwell.Scan.Views.Activity.ScanActivity;
import com.am.reachwell.User.Dependencies.component.DaggerUserComponent;
import com.am.reachwell.User.Dependencies.component.UserComponent;
import com.am.reachwell.User.Interfaces.Views.DahsboardViewInterface;
import com.am.reachwell.User.ViewModel.DashBoardViewModel;

import javax.inject.Inject;

public class DashboardActivity extends AppCompatActivity implements DahsboardViewInterface {
    private Toolbar toolbar;

    @Inject
    DashBoardViewModel dashBoardViewModel;
    @Inject
    NetworkHelper networkHelper;
    private ConnectivityManager connMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getComponent().inject(this);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkHelper.checkNetwork()) {
            Intent intent = new Intent(this, SyncData.class);
            startService(intent);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        dashBoardViewModel.setDahsboardViewInterface(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dashBoardViewModel.getNotifications();
        initializeToolBar();
        initEventListeners();
    }

    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                NetworkInfo ni = connMgr.getActiveNetworkInfo();
                if (ni != null) {
                    if (ni.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        Intent syncDataIntent = new Intent(DashboardActivity.this, SyncData.class);
                        startService(syncDataIntent);
                    } else {

                    }
                } else {

                }
            }

        }
    };

    private void initEventListeners() {
        findViewById(R.id.create_assest_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AssetsActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        findViewById(R.id.asset_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AssetsActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });


        findViewById(R.id.scan_assets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ScanActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });

        findViewById(R.id.create_grn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AssetsActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });

        findViewById(R.id.create_asset_from_grn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AssetsActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
            }
        });
    }

    private void initializeToolBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toolbar_layout, null);
        actionBar.setCustomView(view, new android.support.v7.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    public void showNotifications(String notification, String mails) {
        ((TextView) findViewById(R.id.notificationBadge)).setText(notification);
        ((TextView) findViewById(R.id.mailBadget)).setText(mails);
    }

    private UserComponent getComponent() {
        return DaggerUserComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(ReachwellApplication.get(this).getComponent()).build();
    }
}
