package com.am.reachwell.Assets.Views.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.am.reachwell.Assets.Dependencies.component.AssetComponent;
import com.am.reachwell.Assets.Dependencies.component.DaggerAssetComponent;
import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.ViewModel.GRNItemViewModel;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.R;
import com.am.reachwell.ReachwellApplication;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class GRNItemDialogFragment extends DialogFragment implements CreateAssetInterface {

    private View grnItemView;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

    @Inject
    GRNItemViewModel grnItemViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        grnItemView = inflater.inflate(R.layout.fragment_grnitem_dialog, container, false);
        getComponent().inject(this);
        grnItemViewModel.setCreateAssetInterface(this);
        spinner = (Spinner) grnItemView.findViewById(R.id.asset_class_spn);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        grnItemViewModel.getAssetClasses();
        grnItemView.findViewById(R.id.grn_create_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("assetData",grnItemViewModel.createAsset(spinner.getSelectedItemPosition()));
                getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);
                dismiss();
            }
        });
        return grnItemView;
    }

    @Override
    public void addSpinnerItem(ArrayList<String> arrayList) {
        spinnerAdapter.addAll(arrayList);
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addStatusSpinnerItem(ArrayList<String> arrayList) {

    }

    @Override
    public String getEditTextData(int id) {
        return ((EditText)grnItemView.findViewById(id)).getText().toString();
    }

    @Override
    public JSONObject getAssestData() {
        return null;
    }

    private AssetComponent getComponent(){
        return DaggerAssetComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .applicationComponent(ReachwellApplication.get(getActivity()).getComponent())
                .build();
    }
}
