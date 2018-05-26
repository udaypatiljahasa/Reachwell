package com.am.reachwell.Assets.Views.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.ViewModel.CreateAssetViewModel;
import com.am.reachwell.R;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;


public class CreateAssetFragment extends BaseFragment implements CreateAssetInterface {

    private View createAssetView;
    @Inject
    CreateAssetViewModel createAssetViewModel;

    private Spinner spinner,assetStatusSpinner;
    private ArrayAdapter<String> spinnerAdapter,assetStatusAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createAssetView = inflater.inflate(R.layout.fragment_create_asset, container, false);
        getComponent().inject(this);
        createAssetViewModel.setCreateAssetInterface(this);

        spinner = (Spinner) createAssetView.findViewById(R.id.asset_class_et);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        ((ConstraintLayout)createAssetView.findViewById(R.id.create_assest_sv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        assetStatusSpinner = (Spinner) createAssetView.findViewById(R.id.asset_status_et);
        assetStatusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        assetStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assetStatusSpinner.setAdapter(assetStatusAdapter);


        initViews();
        return createAssetView;
    }

    @Override
    public JSONObject getAssestData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("asset_name",(((EditText) createAssetView.findViewById(R.id.asset_name_et)).getText().toString().trim()));
            jsonObject.put("asset_tag",(((EditText) createAssetView.findViewById(R.id.asset_tag_et)).getText().toString().trim()));
            jsonObject.put("asset_desc",(((EditText) createAssetView.findViewById(R.id.asset_name_desc)).getText().toString().trim()));
            jsonObject.put("asset_make",(((EditText) createAssetView.findViewById(R.id.asset_make_et)).getText().toString().trim()));
            jsonObject.put("asset_model",(((EditText) createAssetView.findViewById(R.id.asset_model_et)).getText().toString().trim()));
            jsonObject.put("asset_sl_no",(((EditText) createAssetView.findViewById(R.id.asset_sl_no_prefix_et)).getText().toString().trim()));

            jsonObject.put("asset_class",createAssetViewModel.getAssetClassId(spinner.getSelectedItemPosition()));
            jsonObject.put("asset_status",createAssetViewModel.getAssetStatusId(assetStatusSpinner.getSelectedItemPosition()));

            jsonObject.put("asset_maintainable",(((CheckBox) createAssetView.findViewById(R.id.asset_main_et)).isChecked()));
        } catch (Exception e){

        }
        return jsonObject;
    }

    public String getEditTextData(int id){
        return (((EditText) createAssetView.findViewById(id)).getText().toString().trim());
    }

    private void initViews(){
        createAssetViewModel.getAssetClasses();
        createAssetViewModel.getAssetStatus();
    }

    @Override
    public void addSpinnerItem(ArrayList<String> arrayList){
        spinnerAdapter.addAll(arrayList);
        spinnerAdapter.notifyDataSetChanged();
        ((Button)createAssetView.findViewById(R.id.asset_create_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAssetViewModel.createAsset(spinner.getSelectedItemPosition(),assetStatusSpinner.getSelectedItemPosition(),((CheckBox) createAssetView.findViewById(R.id.asset_main_et)).isChecked());
            }
        });
    }

    @Override
    public void addStatusSpinnerItem(ArrayList<String> arrayList){
        assetStatusAdapter.addAll(arrayList);
        assetStatusAdapter.notifyDataSetChanged();
    }
}
