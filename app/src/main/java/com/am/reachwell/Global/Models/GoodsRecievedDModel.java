package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.CommanHelper;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetAssetStatus;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Data;

@Data
public class GoodsRecievedDModel implements ApiCallsInterface,Serializable {
    String asset_class;
    String asset_class_name;
    String item_description;
    String pm_grn_guid;
    float ordered_qty;
    float unit;
    float now_receiving_quantity;

    private SQliteDatabase sQliteDatabase;


    @Inject
    public GoodsRecievedDModel(SQliteDatabase sQliteDatabase){
        this.sQliteDatabase = sQliteDatabase;
    }

//    public void getAssetStatusList(){
//        try {
//            getAssetStatus.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_STATUS, null, AssetConstant.REQUEST_GET_ASSET_STATUS, null);
//        } catch (Exception e){
//
//        }
//    }

    public void insertGoodsRecievedDModel(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD._ID , CommanHelper.generateUUID());
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS , asset_class);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ITEM_DESCRIPTION , item_description);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.NOW_RECEIVING_QUANTITY ,now_receiving_quantity);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ORDERED_QTY ,ordered_qty);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.UNIT ,unit);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.SYNC ,0);
            classDataList.add(contentValues);
        }
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVEDD.TABLE_NAME,classDataList);
    }

    public void insertGoodsDRecieved() throws Exception{
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD._ID , CommanHelper.generateUUID());
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS , asset_class);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.PM_GRNGUID , pm_grn_guid);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ITEM_DESCRIPTION , item_description);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.NOW_RECEIVING_QUANTITY ,now_receiving_quantity);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.ORDERED_QTY ,ordered_qty);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.UNIT ,unit);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVEDD.SYNC ,0);
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVEDD.TABLE_NAME,contentValues);
    }

    public void insertGoodaRecievedD(ArrayList<ContentValues> goodsArrayList) throws Exception{
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVEDD.TABLE_NAME,goodsArrayList);
    }

    public ArrayList<ContentValues> getAllAssetStatus() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_GOODS_RECIEVEDD.TABLE_NAME);
    }

    public ArrayList<ContentValues> getGoodsDRecievedByColumn(String column,String value) throws Exception{
        return sQliteDatabase.getDataByColumnValue(TableConstants.TABLE_GOODS_RECIEVEDD.TABLE_NAME,column,value);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
//                insertClassData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }

}
