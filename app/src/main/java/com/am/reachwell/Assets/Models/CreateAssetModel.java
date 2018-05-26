package com.am.reachwell.Assets.Models;

import android.content.ContentValues;

import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.CommanHelper;
import com.am.reachwell.Global.Helpers.SQliteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Data;

@Data
public class CreateAssetModel implements Serializable {
    private String id;
    private String asset_name;
    private String asset_tag;
    private String asset_desc;
    private String asset_make;
    private String asset_model;
    private String asset_sl_no;
    private String asset_class;
    private String asset_status;
    private String asset_user;
    private String asset_owner;
    private int unit;
    private int quantity;
    private boolean asset_maintainable;

    private SQliteDatabase sQliteDatabase;

    @Inject
    public CreateAssetModel(SQliteDatabase sQliteDatabase) {
        this.sQliteDatabase = sQliteDatabase;
    }

    public void insertAssetData() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID, CommanHelper.generateUUID());
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME, asset_name);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_TAG, asset_tag);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_DESC, asset_desc);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_CLASS, asset_class);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAKE, asset_make);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MODEL, asset_model);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SL_NO, asset_sl_no);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_STATUS, asset_status);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_USER, asset_user);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER, asset_owner);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAINTAINABLE, asset_maintainable);
        sQliteDatabase.insertData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME, contentValues);
    }

    public void insertAssetData(String assetId) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID, assetId);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME, asset_name);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_TAG, asset_tag);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_DESC, asset_desc);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_CLASS, asset_class);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAKE, asset_make);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MODEL, asset_model);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SL_NO, asset_sl_no);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_STATUS, asset_status);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_USER, asset_user);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER, asset_owner);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAINTAINABLE, asset_maintainable);
        sQliteDatabase.insertData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME, contentValues);
    }

    public void updateAssetData() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID, CommanHelper.generateUUID());
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME, asset_name);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_TAG, asset_tag);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_DESC, asset_desc);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_CLASS, asset_class);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAKE, asset_make);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MODEL, asset_model);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SL_NO, asset_sl_no);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_STATUS, asset_status);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_USER, asset_user);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER, asset_owner);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_MAINTAINABLE, asset_maintainable);
        sQliteDatabase.updateData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME, contentValues);
    }

    public void updateSyncStatus(String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SYNC,1);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID,id);
        sQliteDatabase.updateData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME,contentValues);
    }

    public String getAssetSpecifiedColumnDataById(String columnName,String Id) throws Exception{
        return sQliteDatabase.getSpecifiedColumnDataById(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME,columnName,Id);
    }

    public ArrayList<ContentValues> getAssetByColumn(String column,String value) throws Exception{
        return sQliteDatabase.getDataByColumnValue(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME,column,value);
    }

    public ArrayList<ContentValues> getAssetCotainingValueByColumn(String column,String value) throws Exception{
        return sQliteDatabase.getDataContainingColumnValue(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME,column,value);
    }

    public ArrayList<ContentValues> getAllAssets() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME);
    }
}
