package com.am.reachwell.Global.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.am.reachwell.Global.Constants.CreateTableConstants;
import com.am.reachwell.Global.Depedencies.ApplicationContext;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQliteDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reachwell_db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public SQliteDatabase(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private void createTable(SQLiteDatabase db){
        db.execSQL(CreateTableConstants.CREATE_TABLE_ASSET_CLASS);
        db.execSQL(CreateTableConstants.CREATE_TABLE_ASSET_STATUS);
        db.execSQL(CreateTableConstants.CREATE_TABLE_ACCOUNT_HEAD);
        db.execSQL(CreateTableConstants.CREATE_TABLE_CONTRACT_TYPE);
        db.execSQL(CreateTableConstants.CREATE_TABLE_ACQUISITION_METHOD);
        db.execSQL(CreateTableConstants.CREATE_TABLE_LCOATION_TYPE);
        db.execSQL(CreateTableConstants.CREATE_TABLE_LOCATION);
        db.execSQL(CreateTableConstants.CREATE_TABLE_CREATE_ASSET);
        db.execSQL(CreateTableConstants.CREATE_TABLE_GOODS_RECIEVED);
        db.execSQL(CreateTableConstants.CREATE_TABLE_GOODS_RECIEVEDD);
        db.execSQL(CreateTableConstants.CREATE_TABLE_GRN);
        db.execSQL(CreateTableConstants.CREATE_TABLE_SUPPLIER);
        db.execSQL(CreateTableConstants.CREATE_TABLE_EMPLOYEE);
        db.execSQL(CreateTableConstants.CREATE_TABLE_ASSET_PICTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void checkAndInsertData(String tableName, ArrayList<ContentValues> dataList,String primaryColumn){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
            String query = "SELECT * FROM " + tableName;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if (cursor.moveToFirst()){
                String[] columns = cursor.getColumnNames();
                do {
                    ContentValues contentValues = new ContentValues();
                    for (int i=0;i<columns.length;i++){
                        String value = cursor.getString(cursor.getColumnIndex(columns[i]));
                        contentValues.put(columns[i],value);
                    }
                    contentValuesArrayList.add(contentValues);
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (contentValuesArrayList.size() > 0){
                for (int i = 0; i < dataList.size(); i++) {
                    ContentValues contentValues = dataList.get(i);
                    String checkquery = "SELECT * FROM " + tableName + " WHERE  " + primaryColumn + " = '" + contentValues.getAsString(primaryColumn) +"'";
                    Cursor checkcursor = sqLiteDatabase.rawQuery(checkquery,null);
                    String dataExists = "";
                    if (checkcursor.moveToFirst()){
                        String[] columns = checkcursor.getColumnNames();
                        do {
                            dataExists = checkcursor.getString(cursor.getColumnIndex(columns[0]));
                        } while (checkcursor.moveToNext());
                    }
                    checkcursor.close();
                    if (dataExists.length() > 0 && dataExists != null){
                        String[] whereArgs = new String[]{contentValues.get("_id").toString()};
                        sqLiteDatabase.update(tableName,contentValues,"_id=?",whereArgs);
                    } else {
                        sqLiteDatabase.insert(tableName,null,contentValues);
                    }
                }
            } else {
                for (int i = 0; i < dataList.size(); i++) {
                    ContentValues contentValues = dataList.get(i);
                    long data = sqLiteDatabase.insert(tableName, null, contentValues);
                    Log.d("database", String.valueOf(data));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

    public void insertData(String tableName, ArrayList<ContentValues> dataList){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            try {
                for (int i = 0; i < dataList.size(); i++) {
                    ContentValues contentValues = dataList.get(i);
                    long data = sqLiteDatabase.insert(tableName, null, contentValues);
                    Log.d("database", String.valueOf(data));
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                sqLiteDatabase.setTransactionSuccessful();
                sqLiteDatabase.endTransaction();
                sqLiteDatabase.close();
            }
    }

    public void insertData(String tableName, ContentValues data) throws Exception{
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(tableName,null,data);
        sqLiteDatabase.close();
    }

    public void updateData(String tableName, ContentValues data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] whereArgs = new String[]{data.get("_id").toString()};
        sqLiteDatabase.update(tableName,data,"_id=?",whereArgs);
        sqLiteDatabase.close();
    }

    public String getDataByColumnName(String tableName, String columnName, String value){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE  " + columnName + " is " + value;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        String retValue = "";
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                retValue = cursor.getString(cursor.getColumnIndex(columns[0]));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return retValue;
    }

    public String getSpecifiedColumnDataById(String tableName, String columnName, String Id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE   _id = '" + Id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        String retValue = "";
        if (cursor.moveToFirst()){
            do {
                retValue = cursor.getString(cursor.getColumnIndex(columnName));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return retValue;
    }

    public String getDataByColumn(String tableName, String columnName, String value){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE  " + columnName + " = '" + value + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        String retValue = "";
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                retValue = cursor.getString(cursor.getColumnIndex(columns[0]));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return retValue;
    }

    public ArrayList<ContentValues> getDataByColumnValue(String tableName, String columnName, String value){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE  " + columnName + " = '" + value + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                ContentValues contentValues = new ContentValues();
                for (int i=0;i<columns.length;i++){
                    String columnValue = cursor.getString(cursor.getColumnIndex(columns[i]));
                    contentValues.put(columns[i],columnValue);
                }
                contentValuesArrayList.add(contentValues);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contentValuesArrayList;
    }

    public ArrayList<ContentValues> getDataContainingColumnValue(String tableName, String columnName, String value){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE  " + columnName + " LIKE '%" + value + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                ContentValues contentValues = new ContentValues();
                for (int i=0;i<columns.length;i++){
                    String columnValue = cursor.getString(cursor.getColumnIndex(columns[i]));
                    contentValues.put(columns[i],columnValue);
                }
                contentValuesArrayList.add(contentValues);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contentValuesArrayList;
    }

    public ArrayList<ContentValues> getDataById(String tableName, String id) throws Exception {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE  _id = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                ContentValues contentValues = new ContentValues();
                for (int i=0;i<columns.length;i++){
                    String value = cursor.getString(cursor.getColumnIndex(columns[i]));
                    contentValues.put(columns[i],value);
                }
                contentValuesArrayList.add(contentValues);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contentValuesArrayList;
    }

    public int getCheckIfDataExists(String tableName, String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT Count(_id) as count FROM " + tableName + " WHERE  _id = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        int retValue = 0;
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do{
                retValue = cursor.getInt(cursor.getColumnIndex(columns[0]));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return retValue;
    }

    public ArrayList<ContentValues> getAllData(String tableName) throws Exception {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String[] columns = cursor.getColumnNames();
            do {
                ContentValues contentValues = new ContentValues();
                for (int i=0;i<columns.length;i++){
                    String value = cursor.getString(cursor.getColumnIndex(columns[i]));
                    contentValues.put(columns[i],value);
                }
                contentValuesArrayList.add(contentValues);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contentValuesArrayList;
    }
}
