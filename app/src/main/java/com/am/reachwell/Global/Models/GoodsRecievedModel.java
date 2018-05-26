package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.CommanHelper;
import com.am.reachwell.Global.Helpers.SQliteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Data;

@Data
public class GoodsRecievedModel {
    String cm_ouguid;
    String contact_number;
    String contact_date;
    String po_number;
    String po_date;
    String grn_number;
    String grn_date;
    String dc_number;
    String dc_date;
    String invoice_number;
    String invoice_date;
    String recieved_by;
    String recieved_date;
    String supplied_by;
    String supplied_through;

    private SQliteDatabase sQliteDatabase;


    @Inject
    public GoodsRecievedModel(SQliteDatabase sQliteDatabase){
        this.sQliteDatabase = sQliteDatabase;
    }


    public void insertGoodsRecievedDModel(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED._ID , CommanHelper.generateUUID());
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.INVOICE_DATE , invoice_date);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.INVOICE_NUMBER , invoice_number);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.DC_DATE ,dc_date);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.DC_NUMBER ,dc_number);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.CM_OUGUID ,cm_ouguid);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.GRN_DATE ,grn_date);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.GRN_NUMBER ,grn_number);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.PO_DATE ,po_date);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.PO_NUMBER ,po_number);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.RECIEVED_BY ,recieved_by);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.RECIEVED_DATE ,recieved_date);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SUPPLIED_BY ,supplied_by);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SUPPLIED_THROUGH ,supplied_through);
            contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SYNC ,0);
            classDataList.add(contentValues);
        }
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVED.TABLE_NAME,classDataList);
    }

    public String insertGoodsRecieved() throws Exception{
        String guuid = CommanHelper.generateUUID();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED._ID , guuid );
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.INVOICE_DATE , invoice_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.INVOICE_NUMBER , invoice_number);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.CONTACT_NUMBER , invoice_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.CONTACT_DATE , invoice_number);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.DC_DATE ,dc_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.DC_NUMBER ,dc_number);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.CM_OUGUID ,cm_ouguid);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.GRN_DATE ,grn_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.GRN_NUMBER ,grn_number);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.PO_DATE ,po_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.PO_NUMBER ,po_number);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.RECIEVED_BY ,recieved_by);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.RECIEVED_DATE ,recieved_date);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SUPPLIED_BY ,supplied_by);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SUPPLIED_THROUGH ,supplied_through);
        contentValues.put(TableConstants.TABLE_GOODS_RECIEVED.SYNC ,0);
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVED.TABLE_NAME,contentValues);
        return guuid;
    }

    public void insertGoodsRecieved(ArrayList<ContentValues> goodsArrayList) throws Exception{
        sQliteDatabase.insertData(TableConstants.TABLE_GOODS_RECIEVED.TABLE_NAME,goodsArrayList);
    }

    public ArrayList<ContentValues> getAllGoodsRecieved() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_GOODS_RECIEVED.TABLE_NAME);
    }
}
