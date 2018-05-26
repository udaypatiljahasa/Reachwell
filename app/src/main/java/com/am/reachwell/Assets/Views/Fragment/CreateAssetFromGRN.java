package com.am.reachwell.Assets.Views.Fragment;


import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.am.reachwell.Assets.Interface.Views.CreateAssetFromGRNInterface;
import com.am.reachwell.Assets.ViewModel.CreateAssetFromGRNViewModel;
import com.am.reachwell.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAssetFromGRN extends BaseFragment implements CreateAssetFromGRNInterface {
    private View grnAssetView;
    private Spinner grnPONumber,assetStatusSpinner;
    private ArrayAdapter<String> spinnerAdapter,spinnerStatusAdapter;
    private TableLayout goodsDTable,assetTagSlnoTbl;
    private RadioButton currentRadioBtn;
    private CheckBox maintainableCheckbox;
    private int assetSLNOTagCount = 0;
    public static int ASSET_SLNO_ID = 200;
    public static int ASSET_TAG_ID = 100;
    private int GOODS_TABLE_ROW_ID = 400;

    @Inject
    CreateAssetFromGRNViewModel createAssetFromGRNViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        grnAssetView = inflater.inflate(R.layout.fragment_create_asset_from_grn, container, false);
        getComponent().inject(this);
        createAssetFromGRNViewModel.setCreateAssetFromGRNInterface(this);
        grnPONumber = (Spinner) grnAssetView.findViewById(R.id.grn);
        goodsDTable = (TableLayout) grnAssetView.findViewById(R.id.grn_asset_tbl);
        assetTagSlnoTbl = (TableLayout) grnAssetView.findViewById(R.id.asset_tag_sl_no_tbl);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grnPONumber.setAdapter(spinnerAdapter);
        createAssetFromGRNViewModel.getGRNData();

        assetStatusSpinner = (Spinner) grnAssetView.findViewById(R.id.asset_status_et);
        spinnerStatusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assetStatusSpinner.setAdapter(spinnerStatusAdapter);

        maintainableCheckbox = (CheckBox) grnAssetView.findViewById(R.id.asset_main_et);

        ((ConstraintLayout)grnAssetView.findViewById(R.id.create_assest_sv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        ((ConstraintLayout)grnAssetView.findViewById(R.id.assest_sv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        grnPONumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int childCount = goodsDTable.getChildCount();
                if (childCount > 1) {
                    for (int i = 1; i < childCount; i++) {
                        ((TableRow)grnAssetView.findViewById(GOODS_TABLE_ROW_ID + i)).removeAllViewsInLayout();
                        goodsDTable.removeView(((TableRow)grnAssetView.findViewById(GOODS_TABLE_ROW_ID + i)));
                    }
                }
                createAssetFromGRNViewModel.getGRNDData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        grnAssetView.findViewById(R.id.asset_create_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createAssetFromGRNViewModel.createAsset(maintainableCheckbox.isChecked(), assetStatusSpinner.getSelectedItemPosition());
                }
            }
        });
        createAssetFromGRNViewModel.getAssetStatus();
        assetTagSlnoTbl.requestFocus();
        return grnAssetView;
    }

    public boolean validate(){
        boolean valid = true;
        if (assetSLNOTagCount > 0) {
            for (int i = 0; i < assetSLNOTagCount; i++) {
                if (((EditText) grnAssetView.findViewById(CreateAssetFromGRN.ASSET_TAG_ID + i)).getText().toString().length() == 0) {
                    ((EditText) grnAssetView.findViewById(CreateAssetFromGRN.ASSET_TAG_ID + i)).setError("Please enter the tag value");
                    valid = false;
                }
                if (((EditText) grnAssetView.findViewById(CreateAssetFromGRN.ASSET_SLNO_ID + i)).getText().toString().length() == 0) {
                    ((EditText) grnAssetView.findViewById(CreateAssetFromGRN.ASSET_SLNO_ID + i)).setError("Please enter the slno value");
                    valid = false;
                }
            }
        } else {
            valid = false;
            Toast.makeText(getContext(),"Please select any one grn item",Toast.LENGTH_LONG).show();
        }
        return valid;
    }

    @Override
    public void addSpinnerItem(ArrayList<String> arrayList) {
        spinnerAdapter.addAll(arrayList);
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addStatusSpinnerItem(ArrayList<String> arrayList) {
        spinnerStatusAdapter.addAll(arrayList);
        spinnerStatusAdapter.notifyDataSetChanged();
    }

    @Override
    public TextView getTextView(String value){
        TableRow.LayoutParams  layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setPadding(10,10,10,10);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
        } else {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
        }
        textView.setText(value);
        return textView;
    }

    @Override
    public void attachSLNoTags(int count){
        assetSLNOTagCount = count;
        int childCount = assetTagSlnoTbl.getChildCount();
        if (childCount > 1) {
            for (int i = 1; i < childCount; i++) {
                ((TableRow)grnAssetView.findViewById(CreateAssetFromGRN.ASSET_TAG_ID + CreateAssetFromGRN.ASSET_SLNO_ID + i -1)).removeAllViewsInLayout();
                assetTagSlnoTbl.removeView(((TableRow)grnAssetView.findViewById(CreateAssetFromGRN.ASSET_TAG_ID + CreateAssetFromGRN.ASSET_SLNO_ID + i -1)));
            }
        }
        for (int i=0;i<count;i++){
            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(CreateAssetFromGRN.ASSET_TAG_ID + CreateAssetFromGRN.ASSET_SLNO_ID + i);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, 150,1.0f);
            layoutParams.bottomMargin = 5;
            layoutParams.topMargin = 5;
            EditText assetTag = new EditText(getContext());
            assetTag.setLayoutParams(layoutParams);
            assetTag.setCursorVisible(true);
            assetTag.setId(CreateAssetFromGRN.ASSET_TAG_ID + i);
            assetTag.setPadding(10,0,10,0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                assetTag.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
//            } else {
//                assetTag.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                assetTag.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), android.R.color.holo_red_light));
            } else {
                ViewCompat.setBackgroundTintList(assetTag,ContextCompat.getColorStateList(getContext(), android.R.color.holo_red_light));
            }

            EditText assetSlNo = new EditText(getContext());
            assetSlNo.setLayoutParams(layoutParams);
            assetSlNo.setId(CreateAssetFromGRN.ASSET_SLNO_ID + i);
            assetSlNo.setPadding(10,0,10,0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                assetSlNo.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
//            } else {
//                assetSlNo.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                assetSlNo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), android.R.color.holo_red_light));
            } else {
                ViewCompat.setBackgroundTintList(assetSlNo,ContextCompat.getColorStateList(getContext(), android.R.color.holo_red_light));
            }
            try {
                // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                f.set(assetTag, R.drawable.cursor_color);
                f.set(assetSlNo, R.drawable.cursor_color);
            } catch (Exception ignored) {
            }
            tableRow.addView(assetTag);
            tableRow.addView(assetSlNo);
            assetTagSlnoTbl.addView(tableRow);
        }
    }

    @Override
    public String getETvalue(int id) {
        return ((EditText)grnAssetView.findViewById(id)).getText().toString();
    }

    @Override
    public void setETValue(String value,int id) {
        ((EditText)grnAssetView.findViewById(id)).setText(value);
    }

    @Override
    public void naviagteToBack() {
        Toast.makeText(getContext(),"Assets created successfully",Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    @Override
    public void appendToTable(int position, TextView className,TextView description,TextView recievedQuantity){
        TableRow tableRow = new TableRow(getContext());
        tableRow.setId(GOODS_TABLE_ROW_ID + position);
        tableRow.addView(getRadioButton(position));
        tableRow.addView(className);
        tableRow.addView(description);
        tableRow.addView(recievedQuantity);
        goodsDTable.addView(tableRow);
    }

    private RadioButton getRadioButton(int position){
        TableRow.LayoutParams  layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0.4f);
        RadioButton rdbtn = new RadioButton(getContext());
        rdbtn.setLayoutParams(layoutParams);
        rdbtn.setId(position);
        rdbtn.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rdbtn.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rdbtn.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
        } else {
            rdbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
        }
        rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (currentRadioBtn!= null){
                        currentRadioBtn.setChecked(false);
                    }
                    currentRadioBtn = rdbtn;
                    createAssetFromGRNViewModel.grnItemSelected(rdbtn.getId());
                }
            }
        });
        return rdbtn;
    }
}
