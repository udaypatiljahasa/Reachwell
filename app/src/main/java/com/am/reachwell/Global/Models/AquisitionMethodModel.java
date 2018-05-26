package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAcquisitionMethod;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AquisitionMethodModel implements ApiCallsInterface{
    private String id;
    private int active;
    private String CMOUID;
    private String amAcquisitionMethodGuid;
    private String description;
    private String acquisitionMethod;

    private SQliteDatabase sQliteDatabase;


    private GetAcquisitionMethod getAcquisitionMethod;

    @Inject
    public AquisitionMethodModel(SQliteDatabase sQliteDatabase,GetAcquisitionMethod getAcquisitionMethod){
        this.sQliteDatabase = sQliteDatabase;
        this.getAcquisitionMethod = getAcquisitionMethod;
        this.getAcquisitionMethod.setApiCallsListener(this);
    }

    public void getAquisitionMethodList(){
        try {
            getAcquisitionMethod.getData(Request.Method.POST, ApiCallsConstant.GET_AQUISITION_METHOD, null, AssetConstant.REQUEST_GET_AQUISITION_METHOD, null);
        } catch (Exception e){

        }
    }

    public void insertAcquisitionMethodData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_ACQUISITION_METHOD._ID , classObject.getString("AM_AcquisitionMethodGUID"));
            contentValues.put(TableConstants.TABLE_ACQUISITION_METHOD.CM_OU_GUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_ACQUISITION_METHOD.DESCRIPTION , classObject.getString("Description"));
            contentValues.put(TableConstants.TABLE_ACQUISITION_METHOD.ACQUISITION_METHOD , classObject.getString("AcquisitionMethod"));
            contentValues.put(TableConstants.TABLE_ACQUISITION_METHOD.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_ACQUISITION_METHOD.TABLE_NAME,classDataList,TableConstants.TABLE_ACQUISITION_METHOD._ID);
    }


    public ArrayList<ContentValues> getAllAquisitionMethod() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_ACQUISITION_METHOD.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertAcquisitionMethodData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
