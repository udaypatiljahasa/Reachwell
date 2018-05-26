package com.am.reachwell.Assets.Views.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.reachwell.Assets.Views.Adapter.AssetDetialsListAdapter;
import com.am.reachwell.Assets.Views.Adapter.AssetMaintainanceListAdapter;
import com.am.reachwell.R;


import java.util.ArrayList;

public class AssetMaintainanceListFragment extends Fragment {

    private View assestDetialsView;
    private AssetMaintainanceListAdapter assetDetialsListAdapter;
    private RecyclerView assetListView;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assestDetialsView = inflater.inflate(R.layout.fragment_asset_maintainance_list, container, false);
        assetListView = (RecyclerView) assestDetialsView.findViewById(R.id.list);
        ArrayList<String> list = new ArrayList<>();
        list.add("JOBPLAN-001");
        list.add("JOBPLAN-002");
        list.add("JOBPLAN-003");
        list.add("JOBPLAN-004");
        list.add("JOBPLAN-005");
        assetDetialsListAdapter = new AssetMaintainanceListAdapter(getContext(),list);
        assetListView.setAdapter(assetDetialsListAdapter);
        return assestDetialsView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
