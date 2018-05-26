package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetLocationType;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class LocationTypeModel implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String locationType;
    private String description;
    private String CMLocationTypeCustom1;
    private String CMLocationTypeCustom2;

    private SQliteDatabase sQliteDatabase;


    private GetLocationType getLocationType;

    @Inject
    public LocationTypeModel(SQliteDatabase sQliteDatabase,GetLocationType getLocation){
        this.sQliteDatabase = sQliteDatabase;
        this.getLocationType = getLocation;
        this.getLocationType.setApiCallsListener(this);
    }

    public void getLocationTypeList(){
        try {
            getLocationType.getData(Request.Method.POST, ApiCallsConstant.GET_LOCATION_TYPE, null, AssetConstant.REQUEST_GET_LOCATION_TYPE, null);
        } catch (Exception e){

        }
    }

    public void insertLocationType(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE._ID , classObject.getString("CM_LocationTypeGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.CM_OUGUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.ACCOUNT_LOCATION_TYPE , classObject.getString("LocationType"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.DESCRIPTION , classObject.getString("Description"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.CM_LOCATIONTYPE_CUSTOM1 , classObject.getString("CM_LocationType_Custom1"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.CM_LOCATIONTYPE_CUSTOM2 ,classObject.getString("CM_LocationType_Custom2"));
            contentValues.put(TableConstants.TABLE_LOCATION_TYPE.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_LOCATION_TYPE.TABLE_NAME,classDataList,TableConstants.TABLE_LOCATION_TYPE._ID);
    }


    public ArrayList<ContentValues> getAllLocationType() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_LOCATION.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertLocationType(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
