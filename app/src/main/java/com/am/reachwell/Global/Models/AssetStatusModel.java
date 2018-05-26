package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetClasses;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetStatus;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AssetStatusModel  implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String operationalStatus;
    private String assetStatus;
    private String assetDescription;
    private String AMAssetClassCustom1;
    private String AMAssetClassCustom2;

    private SQliteDatabase sQliteDatabase;

    private GetAssetStatus getAssetStatus;

    @Inject
    public AssetStatusModel(SQliteDatabase sQliteDatabase,GetAssetStatus getAssetStatus){
        this.sQliteDatabase = sQliteDatabase;
        this.getAssetStatus = getAssetStatus;
        this.getAssetStatus.setApiCallsListener(this);
    }

    public void getAssetStatusList(){
        try {
            getAssetStatus.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_STATUS, null, AssetConstant.REQUEST_GET_ASSET_STATUS, null);
        } catch (Exception e){

        }
    }

    public void insertClassData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_ASSET_STATUS._ID , classObject.getString("AM_AssetStatusGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.CM_OU_ID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.ASSET_STATUS , classObject.getString("AssetStatus"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.ASSET_DESCRIPTION ,classObject.getString("Description"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.AM_ASSET_CLASS_CUSTOM1 ,classObject.getString("AM_AssetClass_Custom1"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.AM_ASSET_CLASS_CUSTOM2 ,classObject.getString("AM_AssetClass_Custom2"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.OPERATIONAL_STATUS ,classObject.getString("OperationalStatus"));
            contentValues.put(TableConstants.TABLE_ASSET_STATUS.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_ASSET_STATUS.TABLE_NAME,classDataList,TableConstants.TABLE_ASSET_STATUS._ID);
    }


    public ArrayList<ContentValues> getAllAssetStatus() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_ASSET_STATUS.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertClassData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
