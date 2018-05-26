package com.am.reachwell.Assets.Views.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.am.reachwell.Assets.Interface.Views.CreateAssetInterface;
import com.am.reachwell.Assets.Interface.Views.EditAssetInterface;
import com.am.reachwell.Assets.ViewModel.AssetEditViewModel;
import com.am.reachwell.Assets.Views.Activity.AssetsActivity;
import com.am.reachwell.Assets.Views.Activity.CameraActivity;
import com.am.reachwell.Global.Helpers.ImageUtility;
import com.am.reachwell.R;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetEditFragment extends BaseFragment implements EditAssetInterface {

    @Inject
    AssetEditViewModel assetEditViewModel;
    private View assetEditView;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private String imagePath,fileName;

    private int CAMERA_REQUEST_CODE = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assetEditView = inflater.inflate(R.layout.fragment_asset_edit, container, false);
        getComponent().inject(this);
        assetEditView.findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},100);
                } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
                } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},102);
                } else {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });
        assetEditView.findViewById(R.id.save_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetEditViewModel.updateAsset(fileName,imagePath,spinner.getSelectedItemPosition());
            }
        });
        assetEditViewModel.setEditAssetInterface(this);
        String assetId = getArguments().getString("AssetId");
        assetEditViewModel.getAssetDetails(assetId);
//        assetEditViewModel.getAssetClass(assetId);
        spinner = (Spinner) assetEditView.findViewById(R.id.asset_class_et);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        return assetEditView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;
        try {
            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            ((ImageView)assetEditView.findViewById(R.id.asset_image)).setImageBitmap(photo);
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            fileName = ts + ".jpg";
            imagePath = ImageUtility.savePicture(photo, ts + ".jpg");
        } catch (Exception e){

        }
    }

    @Override
    public void addSpinnerItem(ArrayList<String> arrayList,int position) {
        spinnerAdapter.addAll(arrayList);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setSelection(position);
    }

    @Override
    public void addStatusSpinnerItem(ArrayList<String> arrayList) {

    }

    @Override
    public String getEditTextData(int id) {
        return ((EditText) assetEditView.findViewById(R.id.asset_name_et)).getText().toString();
    }

    @Override
    public void setEditTextData(int id,String value) {
        ((EditText) assetEditView.findViewById(R.id.asset_name_et)).setText(value);
    }

    @Override
    public JSONObject getAssestData() {
        return null;
    }

    @Override
    public void navigateToHome() {
        ((AssetsActivity)getActivity()).assetRefreshList = true;
        getFragmentManager().popBackStack();
    }
}
