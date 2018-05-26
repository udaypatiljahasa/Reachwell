package com.am.reachwell.Assets.Models;

import android.content.ContentValues;

import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.CommanHelper;
import com.am.reachwell.Global.Helpers.SQliteDatabase;

import javax.inject.Inject;

import lombok.Data;

@Data
public class PictureModel {
    private String id;
    private String asset_id;
    private String file_name;
    private String file_path;
    private String imageBase64;

    private SQliteDatabase sQliteDatabase;

    @Inject
    public PictureModel(SQliteDatabase sQliteDatabase){
        this.sQliteDatabase = sQliteDatabase;
    }

    public void insertAssetData() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_ASSET_PICTURE._ID, CommanHelper.generateUUID());
        contentValues.put(TableConstants.TABLE_ASSET_PICTURE.ASSET_ID, asset_id);
        contentValues.put(TableConstants.TABLE_ASSET_PICTURE.FILE_NAME, file_name);
        contentValues.put(TableConstants.TABLE_ASSET_PICTURE.FILE_PATH, file_path);
        sQliteDatabase.insertData(TableConstants.TABLE_ASSET_PICTURE.TABLE_NAME, contentValues);
    }

    public void updateSyncStatus(String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstants.TABLE_CREATE_ASSET.ASSET_SYNC,1);
        contentValues.put(TableConstants.TABLE_CREATE_ASSET._ID,id);
        sQliteDatabase.updateData(TableConstants.TABLE_ASSET_PICTURE.TABLE_NAME,contentValues);
    }
}
