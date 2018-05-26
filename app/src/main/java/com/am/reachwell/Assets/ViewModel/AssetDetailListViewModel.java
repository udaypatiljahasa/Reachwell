package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.AssetDetailsListinterface;
import com.am.reachwell.Assets.Models.AssetListModel;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Service.AssetApiCallsListener;
import com.am.reachwell.Assets.Service.GetAssetDetailsList;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AssetDetailListViewModel implements AssetApiCallsListener {
    private GetAssetDetailsList getAssetDetailsList;
    private JSONArray assetDetailsList;
    private AssetDetailsListinterface assetDetailsListinterface;
    private int limit=10,offset=0,totalCount=0,localCount =0;
    private boolean loading = false;
    ArrayList<AssetListModel> itemList = new ArrayList<>();
    private ArrayList<String> assetIDsArraysList = new ArrayList<>();
    private CreateAssetModel createAssetModel;

    @Inject
    public AssetDetailListViewModel(GetAssetDetailsList getAssetDetailsList,CreateAssetModel createAssetModel){
        this.getAssetDetailsList = getAssetDetailsList;
        this.createAssetModel = createAssetModel;
        this.getAssetDetailsList.setAssetApiCallsListener(this);
    }

    public void setAssetDetailsListinterface(AssetDetailsListinterface assetDetailsListinterface){
        this.assetDetailsListinterface = assetDetailsListinterface;
    }

    public void clearArray(){
        assetDetailsList = new JSONArray();
    }

    public void onScrolledAPICall(int dy, String searchText) {
        if (assetDetailsList != null) {
            if (dy > 0 && itemList.size() < totalCount && !loading) {
                this.loading = true;
                getAssetList(searchText);
            }
        }
    }

    public void getLocalAssetList(String searchText){
        assetIDsArraysList.clear();
        ArrayList<ContentValues> contentValuesArrayList;
        assetDetailsListinterface.showDialog();
        try {
            if (searchText.trim().length() > 0) {
                contentValuesArrayList = createAssetModel.getAssetCotainingValueByColumn(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME, searchText);
            } else {
                contentValuesArrayList = createAssetModel.getAllAssets();
            }
            localCount = contentValuesArrayList.size();
            for (int i=0;i<contentValuesArrayList.size();i++){
                ContentValues contentValues  = contentValuesArrayList.get(i);
                AssetListModel assetListModel = new AssetListModel();
                assetListModel.setAssetDesc(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_DESC));
                assetListModel.setAssetName(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME));
                assetListModel.setAssetId(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET._ID));
                assetListModel.setAssetTag(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_TAG));
                assetIDsArraysList.add(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET._ID));
                itemList.add(assetListModel);
            }
            getAssetList(searchText);
        } catch (Exception e){

        }
    }

    public void getAssetList(String searchText){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("limit", limit);
            jsonObject.put("offset",offset);
            jsonObject.put("searchQuery",searchText);
            getAssetDetailsList.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_DETAILS_LIST, jsonObject, AssetConstant.REQUEST_GET_ASSET_DETAILS_LIST, null);
        } catch (Exception e){

        }
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        loading = false;
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                assetDetailsList = jsonObject.getJSONArray("rows");
                for (int i = 0; i < assetDetailsList.length(); i++) {
                    JSONObject dataRow = assetDetailsList.getJSONObject(i);
                    AssetListModel assetListModel = new AssetListModel();
                    assetListModel.setAssetDesc(dataRow.getString("AssetDescription"));
                    assetListModel.setAssetName(dataRow.getString("AssetName"));
                    assetListModel.setAssetId(dataRow.getString("AM_AssetGUID"));
                    assetListModel.setAssetTag(dataRow.getString("AssetTag"));
                    if (!assetIDsArraysList.contains(dataRow.getString("AM_AssetGUID"))){
                        itemList.add(assetListModel);
                    }
                }
                offset = jsonObject.getInt("offset");
                totalCount = localCount + jsonObject.getInt("totalCount");
                assetDetailsListinterface.displayList(itemList);
                assetDetailsListinterface.hideDialog();
            } catch (Exception e){
                assetDetailsListinterface.hideDialog();
            }
        } else {
            assetDetailsListinterface.hideDialog();
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {
        loading = false;
        assetDetailsListinterface.hideDialog();
    }

    public String getAssetId(int posistion){
        return itemList.get(posistion).getAssetId();
    }
}
