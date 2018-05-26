package com.am.reachwell.Scan.Views.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.reachwell.R;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarCode extends Fragment {
    View barCodeScanView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        barCodeScanView = inflater.inflate(R.layout.fragment_bar_code, container, false);

        return barCodeScanView;
    }

}
