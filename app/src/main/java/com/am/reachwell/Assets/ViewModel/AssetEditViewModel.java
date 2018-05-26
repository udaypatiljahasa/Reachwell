package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.Interface.Views.EditAssetInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Models.PictureModel;
import com.am.reachwell.Assets.Service.AssetApiCallsListener;
import com.am.reachwell.Assets.Service.GetAssetDetails;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.R;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class AssetEditViewModel implements AssetApiCallsListener {
    private PictureModel pictureModel;
    private GetAssetDetails getAssetDetails;
    private CreateAssetModel createAssetModel;
    private AssetClassModel assetClassModel;
    private ArrayList<ContentValues> assetClassArrayList;
    private ArrayList<String> assetClassList = new ArrayList<>();
    private EditAssetInterface editAssetInterface;
    private int currentClassPosition = 0;
    @Inject
    public AssetEditViewModel(PictureModel pictureModel,AssetClassModel assetClassModel,
                              GetAssetDetails getAssetDetails,CreateAssetModel createAssetModel){
        this.pictureModel = pictureModel;
        this.getAssetDetails = getAssetDetails;
        this.createAssetModel = createAssetModel;
        this.assetClassModel = assetClassModel;
        this.getAssetDetails.setAssetApiCallsListener(this);
    }

    public void setEditAssetInterface(EditAssetInterface editAssetInterface){
        this.editAssetInterface = editAssetInterface;
    }

    public void savePicture(String fileName,String filePath,String assetId){
        try {
            pictureModel.setAsset_id(assetId);
            pictureModel.setFile_path(filePath);
            pictureModel.setFile_name(fileName);
            pictureModel.insertAssetData();
            editAssetInterface.navigateToHome();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAssetClass(String assetClassId){
        try {
            assetClassArrayList = assetClassModel.getAllAssetClass();
            for (int i=0;i<assetClassArrayList.size(); i++){
                ContentValues contentValues = assetClassArrayList.get(i);
                if (assetClassId.equals(contentValues.getAsString(TableConstants.TABLE_ASSET_CLASS._ID))){
                    currentClassPosition = i;
                }
                assetClassList.add(contentValues.getAsString(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS));
            }
            editAssetInterface.addSpinnerItem(assetClassList,currentClassPosition);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateAsset(String fileName,String filePath,int classSelectedPosition){
        try {
            String assetId = createAssetModel.getAssetSpecifiedColumnDataById(TableConstants.TABLE_ASSET_CLASS._ID,createAssetModel.getId());
            createAssetModel.setAsset_name(editAssetInterface.getEditTextData(R.id.asset_name_et));
            createAssetModel.setAsset_class(getAssetClassId(classSelectedPosition));
            if (assetId.length() > 0){
                createAssetModel.updateAssetData();
            } else {
                createAssetModel.insertAssetData(createAssetModel.getId());
            }
            if (filePath != "" && filePath != null && filePath.length() != 0) {
                savePicture(fileName, filePath, createAssetModel.getId());
            }  else {
                editAssetInterface.navigateToHome();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getAssetClassId(int position){
        return assetClassArrayList.get(position).getAsString(TableConstants.TABLE_ASSET_CLASS._ID);
    }

    public void getAssetDetails(String assetId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("searchQuery",assetId);
            getAssetDetails.getData(Request.Method.POST, ApiCallsConstant.GET_ASSET_DETAIL, jsonObject, AssetConstant.REQUEST_GET_ASSET_DETAIL, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                JSONArray assetDetails = jsonObject.getJSONArray("rows");
                for (int i = 0; i < assetDetails.length(); i++) {
                    JSONObject dataRow = assetDetails.getJSONObject(i);
                    createAssetModel.setId(dataRow.getString("AM_AssetGUID"));
                    createAssetModel.setAsset_name(dataRow.getString("AssetName"));
                    createAssetModel.setAsset_model(dataRow.getString("Model"));
                    createAssetModel.setAsset_desc(dataRow.getString("AssetDescription"));
                    createAssetModel.setAsset_make(dataRow.getString("Manufacturer"));
                    createAssetModel.setAsset_sl_no(dataRow.getString("SerialNo"));
                    createAssetModel.setAsset_tag(dataRow.getString("AssetTag"));
                    createAssetModel.setAsset_owner(dataRow.getString("OwnerName"));
                    createAssetModel.setAsset_user(dataRow.getString("UserName"));
                    createAssetModel.setAsset_class(dataRow.getString("assetClassId"));
                    editAssetInterface.setEditTextData(R.id.asset_name_et,dataRow.getString("AssetName"));
                    getAssetClass(dataRow.getString("assetClassId"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
