package com.am.reachwell.Assets.Interface.Views;

import org.json.JSONObject;

import java.util.ArrayList;

public interface EditAssetInterface {
    void addSpinnerItem(ArrayList<String> arrayList,int position);
    void setEditTextData(int id,String value);
    void addStatusSpinnerItem(ArrayList<String> arrayList);
    String getEditTextData(int id);
    JSONObject getAssestData();
    void navigateToHome();
}
