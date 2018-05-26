package com.am.reachwell.Assets.ViewModel;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Assets.Interface.Views.CreateGRNInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.Models.GRNModel;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Models.EmployeesModel;
import com.am.reachwell.Global.Models.GoodsRecievedDModel;
import com.am.reachwell.Global.Models.GoodsRecievedModel;
import com.am.reachwell.Global.Models.SupplierModel;
import com.am.reachwell.R;
import com.android.volley.Request;

import java.util.ArrayList;

import javax.inject.Inject;

public class GRNViewModel {

    private GoodsRecievedModel goodsRecievedModel;

    private NetworkHelper networkHelper;
    private CreateGRNInterface createGRNInterface;
    private ArrayList<GoodsRecievedDModel> goodsRecievedDModelArrayList = new ArrayList<>();
    private EmployeesModel employeesModel;
    private SupplierModel supplierModel;
    private ArrayList<String> employeeNameList = new ArrayList<>();
    private ArrayList<String> supplierList = new ArrayList<>();
    private ArrayList<ContentValues> employeeListValues,supplierListValues;

    @Inject
    public GRNViewModel(GoodsRecievedModel goodsRecievedModel,NetworkHelper networkHelper,
                        EmployeesModel employeesModel,SupplierModel supplierModel){
        this.goodsRecievedModel = goodsRecievedModel;
        this.networkHelper = networkHelper;
        this.employeesModel = employeesModel;
        this.supplierModel = supplierModel;
    }
    
    public void setCreateGRNInterface(CreateGRNInterface createGRNInterface){
        this.createGRNInterface = createGRNInterface;
    }

    public void getEmployeesData(){
        try {
            employeeListValues = employeesModel.getAllEmployees();
            for (int i=0;i<employeeListValues.size(); i++){
                ContentValues contentValues = employeeListValues.get(i);
                employeeNameList.add(contentValues.getAsString(TableConstants.TABLE_EMPLOYEE.FIRST_NAME) + " " + contentValues.getAsString(TableConstants.TABLE_EMPLOYEE.LAST_NAME));
            }
            createGRNInterface.addSpinnerItem(employeeNameList);
        } catch (Exception e){

        }
    }

    public void getSupplierData(){
        try {
            supplierListValues = supplierModel.getAllSuppliers();
            for (int i=0;i<supplierListValues.size(); i++){
                ContentValues contentValues = supplierListValues.get(i);
                supplierList.add(contentValues.getAsString(TableConstants.TABLE_SUPPLIER.SUPPLIER_NAME));
            }
            createGRNInterface.addSupplierSpinnerItem(supplierList);
        } catch (Exception e){

        }
    }

    public void createGRN(int selectedEmpPos,int selectedSupplierPos){
        try {
            goodsRecievedModel.setContact_date(createGRNInterface.getEditTextData(R.id.grn_contact_date_et));
            goodsRecievedModel.setContact_number(createGRNInterface.getEditTextData(R.id.grn_contact_number_et));
            goodsRecievedModel.setPo_date(createGRNInterface.getEditTextData(R.id.grn_po_date_et));
            goodsRecievedModel.setPo_number(createGRNInterface.getEditTextData(R.id.grn_po_number_et));
            goodsRecievedModel.setDc_date(createGRNInterface.getEditTextData(R.id.grn_dc_date_et));
            goodsRecievedModel.setDc_number(createGRNInterface.getEditTextData(R.id.grn_dc_no_et));
            goodsRecievedModel.setGrn_date(createGRNInterface.getEditTextData(R.id.grn_date_et));
            goodsRecievedModel.setGrn_number(createGRNInterface.getEditTextData(R.id.grn_no_et));
            goodsRecievedModel.setInvoice_date(createGRNInterface.getEditTextData(R.id.grn_invoice_date_et));
            goodsRecievedModel.setInvoice_number(createGRNInterface.getEditTextData(R.id.grn_invoice_no_et));
            if (selectedEmpPos != -1) {
                goodsRecievedModel.setRecieved_by(getEmployeeByPosition(selectedEmpPos));
            }
            goodsRecievedModel.setRecieved_date(createGRNInterface.getEditTextData(R.id.grn_invoice_date_et));
            if (selectedSupplierPos != -1) {
                goodsRecievedModel.setSupplied_by(getSupplierByPosition(selectedSupplierPos));
            }
            String goodsRecievedId = goodsRecievedModel.insertGoodsRecieved();
            for (int i=0;i<goodsRecievedDModelArrayList.size();i++){
                GoodsRecievedDModel goodsRecievedDModel = goodsRecievedDModelArrayList.get(i);
                goodsRecievedDModel.setPm_grn_guid(goodsRecievedId);
                goodsRecievedDModel.insertGoodsDRecieved();
            }
            if (networkHelper.checkNetwork()) {
//                createAsset.setData(Request.Method.POST, ApiCallsConstant.CREATE_ASSET, createGRNInterface.getAssestData(), AssetConstant.REQUEST_CREATE_ASSET, null);
            } else {
            }
            createGRNInterface.navigateBack();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addgoodsRecievedDData(GoodsRecievedDModel goodsRecievedDModel){
        goodsRecievedDModelArrayList.add(goodsRecievedDModel);
    }

    public String getSupplierByPosition(int position){
        return supplierListValues.get(position).getAsString(TableConstants.TABLE_SUPPLIER._ID);
    }

    public String getEmployeeByPosition(int position){
        return employeeListValues.get(position).getAsString(TableConstants.TABLE_EMPLOYEE._ID);
    }
}
