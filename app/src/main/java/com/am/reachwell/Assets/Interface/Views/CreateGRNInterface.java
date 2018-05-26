package com.am.reachwell.Assets.Interface.Views;

import java.util.ArrayList;

public interface CreateGRNInterface {
    String getEditTextData(int id);
    void addSpinnerItem(ArrayList<String> arrayList);
    void addSupplierSpinnerItem(ArrayList<String> arrayList);
    void navigateBack();
}
