package com.am.reachwell.Assets.Interface.Views;

import com.am.reachwell.Assets.Models.AssetListModel;

import java.util.ArrayList;

public interface AssetDetailsListinterface {
    void displayList(ArrayList<AssetListModel> detailsList);
    void showDialog();
    void hideDialog();
}
