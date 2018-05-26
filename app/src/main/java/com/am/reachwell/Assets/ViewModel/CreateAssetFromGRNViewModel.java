package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.am.reachwell.Assets.Interface.Views.CreateAssetFromGRNInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Views.Fragment.CreateAssetFromGRN;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Models.AssetClassModel;
import com.am.reachwell.Global.Models.AssetStatusModel;
import com.am.reachwell.Global.Models.EmployeesModel;
import com.am.reachwell.Global.Models.GoodsRecievedDModel;
import com.am.reachwell.Global.Models.GoodsRecievedModel;
import com.am.reachwell.Global.Models.SupplierModel;
import com.am.reachwell.R;

import java.util.ArrayList;

import javax.inject.Inject;

public class CreateAssetFromGRNViewModel {

    private GoodsRecievedModel goodsRecievedModel;
    private GoodsRecievedDModel goodsRecievedDModel;
    private NetworkHelper networkHelper;
    private ArrayList<ContentValues> goodsRecievedArray,goodsRecievedDArray,assetStatusArrayList;
    private ArrayList<String> assetStatusList = new ArrayList<>();
    private ArrayList<String> grnArrayList = new ArrayList<>();
    private CreateAssetFromGRNInterface createAssetFromGRNInterface;
    private AssetClassModel assetClassModel;
    private CreateAssetModel createAssetModel;
    private int selectedPosition;
    private AssetStatusModel assetStatusModel;

    @Inject
    public CreateAssetFromGRNViewModel(GoodsRecievedModel goodsRecievedModel, NetworkHelper networkHelper,
                                       GoodsRecievedDModel goodsRecievedDModel, AssetClassModel assetClassModel,
                                       CreateAssetModel createAssetModel,AssetStatusModel assetStatusModel){
        this.goodsRecievedModel = goodsRecievedModel;
        this.networkHelper = networkHelper;
        this.goodsRecievedDModel = goodsRecievedDModel;
        this.assetClassModel = assetClassModel;
        this.createAssetModel = createAssetModel;
        this.assetStatusModel = assetStatusModel;
    }

    public void setCreateAssetFromGRNInterface(CreateAssetFromGRNInterface createAssetFromGRNInterface){
        this.createAssetFromGRNInterface = createAssetFromGRNInterface;
    }

    public void getGRNData(){
        try {
            goodsRecievedArray = goodsRecievedModel.getAllGoodsRecieved();
            for (int i=0;i<goodsRecievedArray.size(); i++){
                ContentValues contentValues = goodsRecievedArray.get(i);
                grnArrayList.add(contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVED.PO_NUMBER));
            }
            createAssetFromGRNInterface.addSpinnerItem(grnArrayList);
        } catch (Exception e){

        }
    }

    public void getAssetStatus(){
        try {
            assetStatusArrayList = assetStatusModel.getAllAssetStatus();
            for (int i=0;i<assetStatusArrayList.size(); i++){
                ContentValues contentValues = assetStatusArrayList.get(i);
                assetStatusList.add(contentValues.getAsString(TableConstants.TABLE_ASSET_STATUS.ASSET_STATUS));
            }
            createAssetFromGRNInterface.addStatusSpinnerItem(assetStatusList);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createAsset(boolean isMaintainable,int statusSelectedPosition){
        createAssetModel.setAsset_name(createAssetFromGRNInterface.getETvalue(R.id.asset_name_et));
        createAssetModel.setAsset_model(createAssetFromGRNInterface.getETvalue(R.id.asset_model_et));
        createAssetModel.setAsset_desc(createAssetFromGRNInterface.getETvalue(R.id.asset_name_desc));
        createAssetModel.setAsset_make(createAssetFromGRNInterface.getETvalue(R.id.asset_make_et));
        createAssetModel.setAsset_class(goodsRecievedDArray.get(selectedPosition).getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS));
        createAssetModel.setAsset_status(assetStatusArrayList.get(statusSelectedPosition).getAsString(TableConstants.TABLE_ASSET_STATUS._ID));
        createAssetModel.setAsset_maintainable(isMaintainable);
        for (int i=0;i<goodsRecievedDArray.get(selectedPosition).getAsInteger(TableConstants.TABLE_GOODS_RECIEVEDD.NOW_RECEIVING_QUANTITY); i ++){
            createAssetModel.setAsset_tag(createAssetFromGRNInterface.getETvalue(CreateAssetFromGRN.ASSET_TAG_ID + i));
            createAssetModel.setAsset_sl_no(createAssetFromGRNInterface.getETvalue(CreateAssetFromGRN.ASSET_SLNO_ID + i));
            try {
                createAssetModel.insertAssetData();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        createAssetFromGRNInterface.naviagteToBack();
    }

    public void getGRNDData(int position){
        try {
            goodsRecievedDArray = goodsRecievedDModel.getGoodsDRecievedByColumn(TableConstants.TABLE_GOODS_RECIEVEDD.PM_GRNGUID,getGoodsRecievedById(position));
            for (int i=0;i<goodsRecievedDArray.size(); i++){
                ContentValues contentValues = goodsRecievedDArray.get(i);
                TextView assetClass;
                if (contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS) != "" && contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS) != null) {
                    assetClass = createAssetFromGRNInterface.getTextView(assetClassModel.getAssetSpecifiedColumnDataById(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS,contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS)));
                } else {
                    assetClass = createAssetFromGRNInterface.getTextView("");
                }
//                TableRow.LayoutParams  layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
//                assetClass.setPadding(10,0,10,10);
//                assetClass.setLayoutParams(layoutParams);
                TextView assetDesc = createAssetFromGRNInterface.getTextView(contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ITEM_DESCRIPTION));
                TextView assetNowRecieved = createAssetFromGRNInterface.getTextView(contentValues.getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.NOW_RECEIVING_QUANTITY));
                createAssetFromGRNInterface.appendToTable(i+1,assetClass,assetDesc,assetNowRecieved);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void grnItemSelected(int position){
        try {
            selectedPosition = position - 1;
            createAssetFromGRNInterface.setETValue(assetClassModel.getAssetSpecifiedColumnDataById(TableConstants.TABLE_ASSET_CLASS.ASSET_CLASS, goodsRecievedDArray.get(position-1).getAsString(TableConstants.TABLE_GOODS_RECIEVEDD.ASSET_CLASS)),R.id.asset_class_et);
            createAssetFromGRNInterface.attachSLNoTags(goodsRecievedDArray.get(position - 1).getAsInteger(TableConstants.TABLE_GOODS_RECIEVEDD.NOW_RECEIVING_QUANTITY));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getGoodsRecievedById(int position){
        return goodsRecievedArray.get(position).getAsString(TableConstants.TABLE_GOODS_RECIEVED._ID);
    }
}
