package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetContractType;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class ContractTypeModel implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String amContractTypeGuid;
    private String contractType;
    private String description;
    private String AMContractTypeCustom1;
    private String AMContractTypeCustom2;

    private SQliteDatabase sQliteDatabase;


    private GetContractType getContractType;

    @Inject
    public ContractTypeModel(SQliteDatabase sQliteDatabase,GetContractType getContractType){
        this.sQliteDatabase = sQliteDatabase;
        this.getContractType = getContractType;
        this.getContractType.setApiCallsListener(this);
    }

    public void getContractTypeList(){
        try {
            getContractType.getData(Request.Method.POST, ApiCallsConstant.GET_CONTRACT_TYPE, null, AssetConstant.REQUEST_GET_CONTRACT_TYPE, null);
        } catch (Exception e){

        }
    }

    public void insertContractTypeData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE._ID , classObject.getString("AM_ContractTypeGUID"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.CM_OU_ID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.DESCRIPTION , classObject.getString("Description"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.CONTRACT_TYPE , classObject.getString("ContractType"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.AM_CONTRACT_TYPE_CUSTOM1 , classObject.getString("AM_ContractType_Custom1"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.AM_CONTRACT_TYPE_CUSTOM2 , classObject.getString("AM_ContractType_Custom2"));
            contentValues.put(TableConstants.TABLE_CONTRACT_TYPE.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_CONTRACT_TYPE.TABLE_NAME,classDataList,TableConstants.TABLE_CONTRACT_TYPE._ID);
    }


    public ArrayList<ContentValues> getAllAquisitionMethod() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_CONTRACT_TYPE.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertContractTypeData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
