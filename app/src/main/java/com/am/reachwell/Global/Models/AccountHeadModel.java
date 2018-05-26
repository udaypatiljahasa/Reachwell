package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAccountHead;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AccountHeadModel implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String amAccountHeadGuid;
    private String parentGuid;
    private String accountHeadCode;
    private String accountHeadName;
    private String accountHeadDescription;
    private String AMAccountHeadCustom1;
    private String AMAccountHeadCustom2;


    private SQliteDatabase sQliteDatabase;


    private GetAccountHead getAccountHead;

    @Inject
    public AccountHeadModel(SQliteDatabase sQliteDatabase,GetAccountHead getAcquisitionMethod){
        this.sQliteDatabase = sQliteDatabase;
        this.getAccountHead = getAcquisitionMethod;
        this.getAccountHead.setApiCallsListener(this);
    }

    public void getAccountHeadList(){
        try {
            getAccountHead.getData(Request.Method.POST, ApiCallsConstant.GET_ACCOUNT_HEAD, null, AssetConstant.REQUEST_GET_ACCOUNT_HEAD, null);
        } catch (Exception e){

        }
    }

    public void insertAccountHeadData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD._ID , classObject.getString("AM_AccountHeadGUID"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.CM_OUGUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.PARENT_GU_ID , classObject.getString("ParentGUID"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.ACCOUNT_HEAD_CODE , classObject.getString("AccountHeadCode"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.ACCOUNT_HEAD_NAME , classObject.getString("AccountHeadName"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.ACCOUNT_HEAD_DESCRIPTION , classObject.getString("AccountHeadDescription"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.AM_ACCOUNT_HEAD_CUSTOM1 , classObject.getString("AM_AccountHead_Custom1"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.AM_ACCOUNT_HEAD_CUSTOM2 , classObject.getString("AM_AccountHead_Custom2"));
            contentValues.put(TableConstants.TABLE_ACCOUNT_HEAD.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_ACCOUNT_HEAD.TABLE_NAME,classDataList,TableConstants.TABLE_ACCOUNT_HEAD._ID);
    }


    public ArrayList<ContentValues> getAllAccountHead() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_ACCOUNT_HEAD.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertAccountHeadData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
