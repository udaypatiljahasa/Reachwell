package com.am.reachwell.Assets.Models;

import android.content.ContentValues;

import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.CommanHelper;
import com.am.reachwell.Global.Helpers.SQliteDatabase;

import javax.inject.Inject;

import lombok.Data;

@Data
public class GRNModel {
    private String id;
    private String grn_description;
    private float order_qty;
    private int unit;
    private float recieved_qty;
    private String grn_remarks;
    private int status;
    private int active;
    private int item_status;

    private SQliteDatabase sQliteDatabase;

    @Inject
    public GRNModel(SQliteDatabase sQliteDatabase) {
        this.sQliteDatabase = sQliteDatabase;
    }

    public void insertAssetData() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_GRN._ID, CommanHelper.generateUUID());
        contentValues.put(TableConstants.TABLE_CREATE_GRN.GRN_DEXRIPTION, grn_description);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.ORDER_QTY, order_qty);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.UNIT, unit);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.RECIEVED_QTY, recieved_qty);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.GRN_REMARKS, grn_remarks);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.STATUS, status);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.ACTIVE, active);
        contentValues.put(TableConstants.TABLE_CREATE_GRN.ITEM_STATUS, item_status);
        sQliteDatabase.insertData(TableConstants.TABLE_CREATE_GRN.TABLE_NAME, contentValues);
    }

    public void updateSyncStatus(String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SYNC,1);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID,id);
        sQliteDatabase.updateData(TableConstants.TABLE_CREATE_ASSET.TABLE_NAME,contentValues);
    }
}
