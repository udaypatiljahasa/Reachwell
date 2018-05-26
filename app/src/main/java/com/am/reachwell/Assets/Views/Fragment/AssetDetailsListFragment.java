package com.am.reachwell.Assets.Views.Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.am.reachwell.Assets.Interface.Views.AssetDetailsListinterface;
import com.am.reachwell.Assets.Models.AssetListModel;
import com.am.reachwell.Assets.ViewModel.AssetDetailListViewModel;
import com.am.reachwell.Assets.Views.Activity.AssetsActivity;
import com.am.reachwell.Assets.Views.Adapter.AssetDetialsListAdapter;
import com.am.reachwell.Global.Helpers.DialogHelper;
import com.am.reachwell.R;

import java.util.ArrayList;

import javax.inject.Inject;

public class AssetDetailsListFragment extends BaseFragment implements AssetDetailsListinterface,AssetDetialsListAdapter.OnItemClickListener {

    private View assestDetialsView;
    private AssetDetialsListAdapter assetDetialsListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView assetListView;

    @Inject
    DialogHelper dialogHelper;

    @Inject
    AssetDetailListViewModel assetDetailListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (assestDetialsView == null) {
            assestDetialsView = inflater.inflate(R.layout.fragment_asset_details_list, container, false);
            getComponent().inject(this);
            assetListView = (RecyclerView) assestDetialsView.findViewById(R.id.asset_details_list);
            ArrayList<AssetListModel> list = new ArrayList<>();
            linearLayoutManager = new LinearLayoutManager(getContext());
            assetDetailListViewModel.setAssetDetailsListinterface(this);
            assetListView.setLayoutManager(linearLayoutManager);
            assetDetialsListAdapter = new AssetDetialsListAdapter(getContext(), list, this);
            assetListView.setAdapter(assetDetialsListAdapter);
            assetDetailListViewModel.getLocalAssetList("");
        } else {
            if(((AssetsActivity)getActivity()).assetRefreshList){
                ((AssetsActivity)getActivity()).assetRefreshList = false;
                assetDetailListViewModel.getLocalAssetList("");
            }
        }
        ((ImageView)assestDetialsView.findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)assestDetialsView.findViewById(R.id.search_field)).getText().toString().trim().length() > 0)
                {
                    assetDetailListViewModel.clearArray();
                    assetDetailListViewModel.getAssetList(((EditText) assestDetialsView.findViewById(R.id.search_field)).getText().toString().trim());
                } else {
                    assetDetailListViewModel.clearArray();
                    assetDetailListViewModel.getAssetList("");
                }
            }
        });

        assetListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                assetDetailListViewModel.onScrolledAPICall(dy, ((EditText)assestDetialsView.findViewById(R.id.search_field)).getText().toString().trim());
            }
        });

        return assestDetialsView;
    }

    @Override
    public void displayList(ArrayList<AssetListModel> detailsList) {
        assetDetialsListAdapter.setAssetList(detailsList);
    }

    @Override
    public void onItemClick(View v, int position) {
        String assetId = assetDetailListViewModel.getAssetId(position);
        ((AssetsActivity)getActivity()).attachFragment(assetId,0);
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
