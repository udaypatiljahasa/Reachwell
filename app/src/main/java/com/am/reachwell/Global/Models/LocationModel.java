package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetLocation;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class LocationModel implements ApiCallsInterface {
    private String id;
    private int active;
    private String CMOUID;
    private String ParentGUID;
    private String locationType;
    private String cmLocationTypeGUID;
    private String locationCode;
    private String  locationName;
    private String locationDescription;
    private String CMLocationCustom1;
    private String Barcode;
    private String AMContractLocGUID;

    private SQliteDatabase sQliteDatabase;


    private GetLocation getLocation;

    @Inject
    public LocationModel(SQliteDatabase sQliteDatabase,GetLocation getLocation){
        this.sQliteDatabase = sQliteDatabase;
        this.getLocation = getLocation;
        this.getLocation.setApiCallsListener(this);
    }

    public void getLocationList(){
        try {
            getLocation.getData(Request.Method.POST, ApiCallsConstant.GET_LOCATION, null, AssetConstant.REQUEST_GET_LOCATION, null);
        } catch (Exception e){

        }
    }

    public void insertLocationData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_LOCATION._ID , classObject.getString("CM_LocationGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION.CM_OUGUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION.CM_LOCATION_TYPE_GUID , classObject.getString("CM_LocationTypeGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION.PARENT_GU_ID , classObject.getString("ParentGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION.LOCATION_CODE , classObject.getString("LocationCode"));
            contentValues.put(TableConstants.TABLE_LOCATION.LOCATION_NAME ,classObject.getString("LocationName"));
            contentValues.put(TableConstants.TABLE_LOCATION.LOCATION_DESCRIPTION ,classObject.getString("LocationDescription"));
            contentValues.put(TableConstants.TABLE_LOCATION.CM_LOCATION_CUSTOM1 ,classObject.getString("CM_Location_Custom1"));
            contentValues.put(TableConstants.TABLE_LOCATION.BARCODE ,classObject.getString("Barcode"));
            contentValues.put(TableConstants.TABLE_LOCATION.AM_CONTRACT_LOC_GUID ,classObject.getString("AM_ContractLocGUID"));
            contentValues.put(TableConstants.TABLE_LOCATION.ACTIVE ,classObject.getString("Active"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_LOCATION.TABLE_NAME,classDataList,TableConstants.TABLE_LOCATION._ID);
    }


    public ArrayList<ContentValues> getAllLocation() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_LOCATION.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertLocationData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
