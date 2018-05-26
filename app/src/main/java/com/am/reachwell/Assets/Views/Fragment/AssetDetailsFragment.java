package com.am.reachwell.Assets.Views.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.reachwell.Assets.Interface.Views.AssetDetailsInterface;
import com.am.reachwell.Assets.ViewModel.AssetDetailsViewModel;
import com.am.reachwell.Assets.Views.Activity.AssetsActivity;
import com.am.reachwell.Global.Helpers.DialogHelper;
import com.am.reachwell.R;

import javax.inject.Inject;


public class AssetDetailsFragment extends BaseFragment implements AssetDetailsInterface {
    private View assetDetialsFragmentView;

    @Inject
    AssetDetailsViewModel assetDetailsViewModel;

    @Inject
    DialogHelper dialogHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(assetDetialsFragmentView == null) {
            assetDetialsFragmentView = inflater.inflate(R.layout.fragment_asset_details, container, false);
            getComponent().inject(this);
            String assetId = getArguments().getString("AssetId");
            assetDetailsViewModel.setAssetDetailsInterface(this);
            assetDetailsViewModel.getAssetDetails(assetId);
            assetDetialsFragmentView.findViewById(R.id.asset_edit_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AssetsActivity)getActivity()).attachFragment(assetId,1);
                }
            });
        } else {
            String assetId = getArguments().getString("AssetId");
            assetDetailsViewModel.getAssetDetails(assetId);
        }
        return assetDetialsFragmentView;
    }

    @Override
    public void setTxtViewValue(int id, String value) {
        ((TextView) assetDetialsFragmentView.findViewById(id)).setText(value);
    }

    @Override
    public void showDialog() {
        dialogHelper.showDialog("Processing...");
    }

    @Override
    public void hideDialog() {
        if (dialogHelper.checkIsShowing())
            dialogHelper.dismissDialog();
    }
}
