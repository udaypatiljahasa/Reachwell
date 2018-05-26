package com.am.reachwell.Global.Services.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.am.reachwell.Global.Depedencies.component.ActivityComponent;
import com.am.reachwell.Global.Depedencies.component.ApplicationComponent;
import com.am.reachwell.Global.Depedencies.component.DaggerActivityComponent;
import com.am.reachwell.Global.Depedencies.component.DaggerApplicationComponent;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.Global.Depedencies.module.ApplicationModule;
import com.am.reachwell.Global.Models.AccountHeadModel;
import com.am.reachwell.Global.Models.AquisitionMethodModel;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.AssetStatusModel;
import com.am.reachwell.Global.Models.ContractTypeModel;
import com.am.reachwell.Global.Models.EmployeesModel;
import com.am.reachwell.Global.Models.LocationModel;
import com.am.reachwell.Global.Models.LocationTypeModel;
import com.am.reachwell.Global.Models.SupplierModel;
import com.am.reachwell.ReachwellApplication;

import javax.inject.Inject;

public class SyncData extends IntentService {


    public SyncData() {
        super("SyncData");
    }

    @Inject
    AssetClassModel assetClassModel;
    @Inject
    AssetStatusModel assetStatusModel;
    @Inject
    AccountHeadModel accountHeadModel;
    @Inject
    AquisitionMethodModel aquisitionMethodModel;
    @Inject
    ContractTypeModel contractTypeModel;
    @Inject
    LocationModel locationModel;
    @Inject
    LocationTypeModel locationTypeModel;
    @Inject
    EmployeesModel employeesModel;
    @Inject
    SupplierModel supplierModel;

    @Override
    protected void onHandleIntent(Intent intent) {
        getComponent().inject(this);
        assetClassModel.getAssetClassList();
        assetStatusModel.getAssetStatusList();
        accountHeadModel.getAccountHeadList();
        aquisitionMethodModel.getAquisitionMethodList();
        contractTypeModel.getContractTypeList();
        locationTypeModel.getLocationTypeList();
        locationModel.getLocationList();
        employeesModel.getEmployees();
        supplierModel.getSuppliers();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("syncData","Stopped");
    }

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private ApplicationComponent getComponent(){
        return DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(ReachwellApplication.get(getApplicationContext())))
                .build();
    }
}
