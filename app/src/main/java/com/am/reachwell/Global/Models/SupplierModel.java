package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetClasses;
import com.am.reachwell.Global.Services.ApiCalls.GetSupplier;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Data;

@Data
public class SupplierModel implements ApiCallsInterface {
    String cm_ouguid;
    String parentGuid;
    String supplierCode;
    String supplierType;
    String supplierName;
    String address;
    String cmCityGuid;
    String cmStateGuid;
    String cmCountryGuid;
    String emailId;
    String phoneNumber;

    private SQliteDatabase sQliteDatabase;


    private GetSupplier getSupplier;

    @Inject
    public SupplierModel(SQliteDatabase sQliteDatabase,GetSupplier getSupplier){
        this.sQliteDatabase = sQliteDatabase;
        this.getSupplier = getSupplier;
        this.getSupplier.setApiCallsListener(this);
    }

    public void getSuppliers(){
        try {
            getSupplier.getData(Request.Method.POST, ApiCallsConstant.GET_SUPPLIER, null, AssetConstant.REQUEST_GET_SUPPLIER, null);
        } catch (Exception e){

        }
    }

    public void insertSupplierData(JSONArray supplierList) throws Exception{
        ArrayList<ContentValues> supplierDataList = new ArrayList<>();
        for (int i=0;i<supplierList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = supplierList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_SUPPLIER._ID , classObject.getString("CM_SupplierGUID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.CM_OUGUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.CM_CITY_GUID , classObject.getString("CM_CityGUID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.CM_COUNTRY_GUID , classObject.getString("CM_CountryGUID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.CM_STATE_GUID , classObject.getString("CM_StateGUID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.ADDRESS ,classObject.getString("Address"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.EMAIL_ID ,classObject.getString("EmailID"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.SUPPLIER_NAME ,classObject.getString("SupplierName"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.SUPPLIER_CODE ,classObject.getString("SupplierCode"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.SUPPLIER_TYPE ,classObject.getString("SupplierType"));
            contentValues.put(TableConstants.TABLE_SUPPLIER.PHONE_NUMBER ,classObject.getString("PhoneNumber"));
            supplierDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_SUPPLIER.TABLE_NAME,supplierDataList,TableConstants.TABLE_SUPPLIER._ID);
    }


    public ArrayList<ContentValues> getAllSuppliers() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_SUPPLIER.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertSupplierData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }

}
