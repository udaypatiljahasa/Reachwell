package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.AssetDetailsInterface;
import com.am.reachwell.Assets.Models.AssetListModel;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Service.AssetApiCallsListener;
import com.am.reachwell.Assets.Service.GetAssetDetails;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.R;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AssetDetailsViewModel implements AssetApiCallsListener {

    private GetAssetDetails getAssetDetails;
    private AssetDetailsInterface assetDetailsInterface;
    private CreateAssetModel createAssetModel;

    @Inject
    public AssetDetailsViewModel(GetAssetDetails getAssetDetails,CreateAssetModel createAssetModel){
        this.getAssetDetails = getAssetDetails;
        this.createAssetModel = createAssetModel;
        this.getAssetDetails.setAssetApiCallsListener(this);
    }

    public void setAssetDetailsInterface(AssetDetailsInterface assetDetailsInterface){
        this.assetDetailsInterface = assetDetailsInterface;
    }

    public void getAssetDetails(String assetId){
        try {
            String astId = createAssetModel.getAssetSpecifiedColumnDataById(TableConstants.TABLE_ASSET_CLASS._ID,assetId);
            if (astId.length() > 0){
                ArrayList<ContentValues> contentValuesArrayList = createAssetModel.getAssetByColumn(TableConstants.TABLE_ASSET_CLASS._ID,assetId);
                ContentValues contentValues = contentValuesArrayList.get(0);
                assetDetailsInterface.setTxtViewValue(R.id.asset_name_et,contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_NAME));
                assetDetailsInterface.setTxtViewValue(R.id.asset_model_et,contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_MODEL));
                assetDetailsInterface.setTxtViewValue(R.id.asset_make_et,contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_MAKE));
                assetDetailsInterface.setTxtViewValue(R.id.asset_sn_et,contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_SL_NO));
                assetDetailsInterface.setTxtViewValue(R.id.asset_tag_et,contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_TAG));
                if(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_USER) != null && contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_USER) != "null") {
                    assetDetailsInterface.setTxtViewValue(R.id.asset_user_et, contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_USER));
                }
                if(contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER) != null && contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER) != "null") {
                    assetDetailsInterface.setTxtViewValue(R.id.asset_owner_et, contentValues.getAsString(TableConstants.TABLE_CREATE_ASSET.ASSET_OWNER));
                }
            } else {
                assetDetailsInterface.showDialog();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("searchQuery", assetId);
                getAssetDetails.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_DETAIL, jsonObject, AssetConstant.REQUEST_GET_ASSET_DETAIL, null);
            }
        } catch (Exception e){

        }
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        assetDetailsInterface.hideDialog();
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                JSONArray assetDetails = jsonObject.getJSONArray("rows");
                for (int i = 0; i < assetDetails.length(); i++) {
                    JSONObject dataRow = assetDetails.getJSONObject(i);
                    assetDetailsInterface.setTxtViewValue(R.id.asset_name_et,dataRow.getString("AssetName"));
                    assetDetailsInterface.setTxtViewValue(R.id.asset_model_et,dataRow.getString("Model"));
                    assetDetailsInterface.setTxtViewValue(R.id.asset_make_et,dataRow.getString("Manufacturer"));
                    assetDetailsInterface.setTxtViewValue(R.id.asset_sn_et,dataRow.getString("SerialNo"));
                    assetDetailsInterface.setTxtViewValue(R.id.asset_tag_et,dataRow.getString("AssetTag"));
                    if(dataRow.getString("UserName") != null && dataRow.getString("UserName") != "null") {
                        assetDetailsInterface.setTxtViewValue(R.id.asset_user_et, dataRow.getString("UserName"));
                    }
                    if(dataRow.getString("OwnerName") != null && dataRow.getString("OwnerName") != "null") {
                        assetDetailsInterface.setTxtViewValue(R.id.asset_owner_et, dataRow.getString("OwnerName"));
                    }
                }
            } catch (Exception e){

            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {
        String errorMsg = response.errorMessage;
    }
}
