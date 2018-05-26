package com.am.reachwell.Assets.Interface.Views;

import org.json.JSONObject;

import java.util.ArrayList;

public interface CreateAssetInterface {
    void addSpinnerItem(ArrayList<String> arrayList);
    void addStatusSpinnerItem(ArrayList<String> arrayList);
    String getEditTextData(int id);
    JSONObject getAssestData();
}
