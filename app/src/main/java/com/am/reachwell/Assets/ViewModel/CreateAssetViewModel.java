package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Service.AssetApiCallsListener;
import com.am.reachwell.Assets.Service.CreateAsset;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.AssetStatusModel;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetClasses;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetStatus;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.R;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class CreateAssetViewModel implements ApiCallsInterface{

    private ArrayList<ContentValues> assetClassArrayList,assetStatusArrayList;
    private ArrayList<String> assetClassList = new ArrayList<>();
    private ArrayList<String> assetStatusList = new ArrayList<>();
    private CreateAssetInterface createAssetInterface;
    private AssetClassModel assetClassModel;
    private AssetStatusModel assetStatusModel;
    private CreateAssetModel createAssetModel;
    private CreateAsset createAsset;

    NetworkHelper networkHelper;

    @Inject
    public CreateAssetViewModel(AssetClassModel assetClassModel, AssetStatusModel assetStatusModel,
                                CreateAssetModel createAssetModel, CreateAsset createAsset,NetworkHelper networkHelper){
        this.assetClassModel = assetClassModel;
        this.assetStatusModel = assetStatusModel;
        this.createAssetModel = createAssetModel;
        this.createAsset = createAsset;
        this.networkHelper = networkHelper;
    }

    public void setCreateAssetInterface(CreateAssetInterface createAssetInterface){
        this.createAssetInterface = createAssetInterface;
    }

    public void getAssetClasses(){
        try {
            assetClassArrayList = assetClassModel.getAllAssetClass();
            for (int i=0;i<assetClassArrayList.size(); i++){
                ContentValues contentValues = assetClassArrayList.get(i);
                assetClassList.add(contentValues.getAsString(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS));
            }
            createAssetInterface.addSpinnerItem(assetClassList);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getAssetStatus(){
        try {
            assetStatusArrayList = assetStatusModel.getAllAssetStatus();
            for (int i=0;i<assetStatusArrayList.size(); i++){
                ContentValues contentValues = assetStatusArrayList.get(i);
                assetStatusList.add(contentValues.getAsString(TableConstants.TABLE_ASSET_STATUS.ASSET_STATUS));
            }
            createAssetInterface.addStatusSpinnerItem(assetStatusList);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createAsset(int classPosition,int statusPosition,boolean maintainable){
        try {
            createAssetModel.setAsset_class(getAssetClassId(classPosition));
            createAssetModel.setAsset_status(getAssetStatusId(statusPosition));
            createAssetModel.setAsset_maintainable(maintainable);
            createAssetModel.setAsset_desc(createAssetInterface.getEditTextData(R.id.asset_name_desc));
            createAssetModel.setAsset_make(createAssetInterface.getEditTextData(R.id.asset_make_et));
            createAssetModel.setAsset_model(createAssetInterface.getEditTextData(R.id.asset_model_et));
            createAssetModel.setAsset_sl_no(createAssetInterface.getEditTextData(R.id.asset_sl_no_prefix_et));
            createAssetModel.setAsset_tag(createAssetInterface.getEditTextData(R.id.asset_tag_et));
            createAssetModel.setAsset_name(createAssetInterface.getEditTextData(R.id.asset_name_et));
            if (networkHelper.checkNetwork()) {
                createAsset.setData(Request.Method.POST, ApiCallsConstant.CREATE_ASSET, createAssetInterface.getAssestData(), AssetConstant.REQUEST_CREATE_ASSET, null);
            } else {
                createAssetModel.insertAssetData();
            }
        } catch (Exception e){

        }
    }

    public String getAssetClassId(int position){
        return assetClassArrayList.get(position).getAsString(TableConstants.TABLE_ASSET_CLASS._ID);
    }

    public String getAssetStatusId(int position){
        return assetStatusArrayList.get(position).getAsString(TableConstants.TABLE_ASSET_STATUS._ID);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                createAssetModel.updateSyncStatus(createAssetModel.getId());
            } catch (Exception e){

            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
