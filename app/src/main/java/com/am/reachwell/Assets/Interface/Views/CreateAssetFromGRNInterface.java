package com.am.reachwell.Assets.Interface.Views;

import android.widget.TextView;

import java.util.ArrayList;

public interface CreateAssetFromGRNInterface {
    void addSpinnerItem(ArrayList<String> arrayList);
    void addStatusSpinnerItem(ArrayList<String> arrayList);
    TextView getTextView(String value);
    void appendToTable(int position,TextView className,TextView description,TextView recievedQuantity);
    void attachSLNoTags(int count);
    String getETvalue(int id);
    void setETValue(String value,int id);
    void naviagteToBack();
}
