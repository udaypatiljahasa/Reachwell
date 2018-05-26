package com.am.reachwell.Global.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Models.AssetListModel;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetClasses;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;


public class AssetClassModel implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String CMAccountHeadID;
    private String parentId;
    private int classType;
    private String assetClass;
    private String assetDescription;
    private String AMAssetClassCustom1;
    private String AMAssetClassCustom2;

    private SQliteDatabase sQliteDatabase;


    private GetAssetClasses getAssetClasses;

    @Inject
    public AssetClassModel(SQliteDatabase sQliteDatabase,GetAssetClasses getAssetClasses){
        this.sQliteDatabase = sQliteDatabase;
        this.getAssetClasses = getAssetClasses;
        this.getAssetClasses.setApiCallsListener(this);
    }

    public void getAssetClassList(){
        try {
            getAssetClasses.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_CLASS, null, AssetConstant.REQUEST_GET_ASSET_CLASS, null);
        } catch (Exception e){

        }
    }

    public void insertClassData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_ASSET_CLASS._ID , classObject.getString("AM_AssetClassGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.CM_OU_ID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.CM_ACCOUNT_HEAD_GUID , classObject.getString("CM_AccountHeadGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.PARENT_ID , classObject.getString("ParentGUID"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS , classObject.getString("AssetClass"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.ASSET_DESCRIPTION ,classObject.getString("Description"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.AM_ASSET_CLASS_CUSTOM1 ,classObject.getString("AM_AssetClass_Custom1"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.AM_ASSET_CLASS_CUSTOM2 ,classObject.getString("AM_AssetClass_Custom2"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.CLASS_TYPE ,classObject.getString("ClassType"));
            contentValues.put(TableConstants.TABLE_ASSET_CLASS.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_ASSET_CLASS.TABLE_NAME,classDataList,TableConstants.TABLE_ASSET_CLASS._ID);
    }


    public ArrayList<ContentValues> getAllAssetClass() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_ASSET_CLASS.TABLE_NAME);
    }

    public String getAssetSpecifiedColumnDataById(String columnName,String Id) throws Exception{
        return sQliteDatabase.getSpecifiedColumnDataById(TableConstants.TABLE_ASSET_CLASS.TABLE_NAME,columnName,Id);
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
