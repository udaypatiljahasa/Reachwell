package com.am.reachwell.Assets.Views.Fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.am.reachwell.Assets.Interface.Views.CreateGRNInterface;
import com.am.reachwell.Assets.Models.CreateAssetModel;
import com.am.reachwell.Assets.ViewModel.GRNViewModel;
import com.am.reachwell.Global.Models.GoodsRecievedDModel;
import com.am.reachwell.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GRNFragment extends BaseFragment implements CreateGRNInterface,View.OnClickListener {

    @Inject
    GRNViewModel grnViewModel;
    View grnView;
    private TableLayout tableLayout;
    private Spinner spinner,suppliedBySpinner;
    private Calendar selectedPODate,selectedGRNDate,selectedInvoiceDate,
            selectedRecievedDate,selectedDCDate,currentSelected,selectedContactDate;
    private DatePickerDialog datePickerDialog;
    private ArrayAdapter<String> spinnerAdapter,suppliedByAdapter;

    private int currentETId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        grnView = inflater.inflate(R.layout.fragment_grn, container, false);
        getComponent().inject(this);
        grnViewModel.setCreateGRNInterface(this);

        initDatePicker();
        selectedPODate = Calendar.getInstance();
        selectedGRNDate = Calendar.getInstance();
        selectedInvoiceDate = Calendar.getInstance();
        selectedRecievedDate = Calendar.getInstance();
        selectedDCDate = Calendar.getInstance();
        currentSelected = Calendar.getInstance();
        selectedContactDate = Calendar.getInstance();

        spinner = (Spinner) grnView.findViewById(R.id.grn_recieved_by_spin);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        suppliedBySpinner = (Spinner) grnView.findViewById(R.id.grn_supplied_by_spin);
        suppliedByAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        suppliedByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suppliedBySpinner.setAdapter(suppliedByAdapter);

        tableLayout = (TableLayout) grnView.findViewById(R.id.grn_asset);

        grnView.findViewById(R.id.grn_po_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_po_date_et)).setInputType(InputType.TYPE_NULL);
        grnView.findViewById(R.id.grn_invoice_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_invoice_date_et)).setInputType(InputType.TYPE_NULL);
        grnView.findViewById(R.id.grn_dc_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_dc_date_et)).setInputType(InputType.TYPE_NULL);
        grnView.findViewById(R.id.grn_recieved_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_recieved_date_et)).setInputType(InputType.TYPE_NULL);
        grnView.findViewById(R.id.grn_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_date_et)).setInputType(InputType.TYPE_NULL);
        grnView.findViewById(R.id.grn_contact_date_et).setOnClickListener(this);
        ((EditText)grnView.findViewById(R.id.grn_contact_date_et)).setInputType(InputType.TYPE_NULL);

        ((Button) grnView.findViewById(R.id.grn_create_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grnViewModel.createGRN(spinner.getSelectedItemPosition(),suppliedBySpinner.getSelectedItemPosition());
            }
        });
        ((Button) grnView.findViewById(R.id.grn_add_item_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GRNItemDialogFragment grnItemDialogFragment = new GRNItemDialogFragment();
                grnItemDialogFragment.setTargetFragment(GRNFragment.this,100);
                grnItemDialogFragment.show(getFragmentManager(),null);
            }
        });
        grnViewModel.getEmployeesData();
        grnViewModel.getSupplierData();
        return grnView;
    }

    private void initDatePicker() {
        int year = 0, month = 0, day = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, null, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    this.getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatePicker dp = datePickerDialog.getDatePicker();
                            currentSelected.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                            ((EditText) grnView.findViewById(currentETId)).setText(String.valueOf(dp.getYear()) + "-" + String.valueOf(dp.getMonth()+1) + "-" + String.valueOf(dp.getDayOfMonth()));
                            Calendar cal = Calendar.getInstance();
                            cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                            cal.add(Calendar.DATE, 0);
                            dp.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                        }
                    });
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    this.getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        } else {
            datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth,
                                      int selectedday) {
                    currentSelected.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                    ((EditText) grnView.findViewById(currentETId)).setText(String.valueOf(datepicker.getYear()) + "-" + String.valueOf(datepicker.getMonth()+1) + "-" + String.valueOf(datepicker.getDayOfMonth()));
                    Calendar cal = Calendar.getInstance();
                    cal.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                    cal.add(Calendar.DATE, 0);
                    datepicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            if (resultCode == RESULT_OK){
                GoodsRecievedDModel goodsRecievedDModel = (GoodsRecievedDModel) data.getSerializableExtra("assetData");
                grnViewModel.addgoodsRecievedDData(goodsRecievedDModel);
                TableRow.LayoutParams  layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
                TableRow tableRow = new TableRow(getContext());

                TextView assetClass = new TextView(getContext());
                assetClass.setLayoutParams(layoutParams);
                assetClass.setPadding(10,10,10,10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    assetClass.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
                } else {
                    assetClass.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
                }
                assetClass.setText(goodsRecievedDModel.getAsset_class_name());

                TextView assetDescription = new TextView(getContext());
                assetDescription.setLayoutParams(layoutParams);
                assetDescription.setPadding(10,10,10,10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    assetDescription.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
                } else {
                    assetDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
                }
                assetDescription.setText(goodsRecievedDModel.getItem_description());

                TextView assetUnit = new TextView(getContext());
                assetUnit.setLayoutParams(layoutParams);
                assetUnit.setPadding(10,10,10,10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    assetUnit.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
                } else {
                    assetUnit.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
                }
                assetUnit.setText(String.valueOf(goodsRecievedDModel.getUnit()));

                TextView assetRecieved = new TextView(getContext());
                assetRecieved.setLayoutParams(layoutParams);
                assetRecieved.setPadding(10,10,10,10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    assetRecieved.setBackground(getResources().getDrawable(R.drawable.rectangular_border));
                } else {
                    assetRecieved.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangular_border));
                }
                assetRecieved.setText(String.valueOf(goodsRecievedDModel.getNow_receiving_quantity()));

                tableRow.addView(assetClass);
                tableRow.addView(assetDescription);
//                tableRow.addView(assetUnit);
                tableRow.addView(assetRecieved);
                tableLayout.addView(tableRow);


                tableLayout.requestFocus();
            }
        }
    }

    public String getEditTextData(int id){
        return (((EditText) grnView.findViewById(id)).getText().toString().trim());
    }

    @Override
    public void addSpinnerItem(ArrayList<String> arrayList) {
        spinnerAdapter.addAll(arrayList);
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addSupplierSpinnerItem(ArrayList<String> supplierList){
        suppliedByAdapter.addAll(supplierList);
        suppliedByAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void onClick(View v) {
        currentETId = v.getId();
        switch (v.getId()){
            case R.id.grn_po_date_et:
                currentSelected = selectedPODate;
                break;
            case R.id.grn_date_et:
                currentSelected = selectedGRNDate;
                break;
            case R.id.grn_dc_date_et:
                currentSelected = selectedDCDate;
                break;
            case R.id.grn_invoice_date_et:
                currentSelected = selectedInvoiceDate;
                break;
            case R.id.grn_recieved_date_et:
                currentSelected = selectedRecievedDate;
                break;
        }
        datePickerDialog.show();
    }
}
