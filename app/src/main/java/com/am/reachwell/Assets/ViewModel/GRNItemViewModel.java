package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.GoodsRecievedDModel;
import com.am.reachwell.R;
import com.android.volley.Request;

import java.util.ArrayList;

import javax.inject.Inject;

public class GRNItemViewModel {

    private ArrayList<ContentValues> assetClassArrayList;
    private ArrayList<String> assetClassList = new ArrayList<>();
    private AssetClassModel assetClassModel;
    private NetworkHelper networkHelper;
    private GoodsRecievedDModel goodsRecievedDModel;
    private CreateAssetInterface createAssetInterface;

    @Inject
    public GRNItemViewModel(AssetClassModel assetClassModel,NetworkHelper networkHelper,GoodsRecievedDModel goodsRecievedDModel){
        this.assetClassModel = assetClassModel;
        this.networkHelper = networkHelper;
        this.goodsRecievedDModel = goodsRecievedDModel;
    }

    public void setCreateAssetInterface(CreateAssetInterface createAssetInterface){
        this.createAssetInterface = createAssetInterface;
    }

    public void getAssetClasses(){
        try {
            assetClassArrayList = assetClassModel.getAllAssetClass();
            for (int i=0;i<assetClassArrayList.size(); i++){
                ContentValues contentValues = assetClassArrayList.get(i);
                assetClassList.add(contentValues.getAsString(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS));
            }
            createAssetInterface.addSpinnerItem(assetClassList);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public GoodsRecievedDModel createAsset(int classPosition){
        try {
            if (classPosition != -1) {
                goodsRecievedDModel.setAsset_class(getAssetClassId(classPosition));
                goodsRecievedDModel.setAsset_class_name(getAssetClassName(classPosition));
            }
            goodsRecievedDModel.setItem_description(createAssetInterface.getEditTextData(R.id.asset_description));
            goodsRecievedDModel.setNow_receiving_quantity(Float.valueOf(createAssetInterface.getEditTextData(R.id.asset_quantity_et)));
        } catch (Exception e){

        }
        return goodsRecievedDModel;
    }

    public String getAssetClassId(int position){
        return assetClassArrayList.get(position).getAsString(TableConstants.TABLE_ASSET_CLASS._ID);
    }

    public String getAssetClassName(int position){
        return assetClassArrayList.get(position).getAsString(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS);
    }
}
