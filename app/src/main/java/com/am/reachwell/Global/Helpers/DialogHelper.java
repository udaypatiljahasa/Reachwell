package com.am.reachwell.Global.Helpers;

import android.app.ProgressDialog;
import android.content.Context;

import com.am.reachwell.Global.Depedencies.ActivityContext;
import com.am.reachwell.R;

import javax.inject.Inject;



public class DialogHelper {
    private Context context;
    private ProgressDialog dialog;

    @Inject
    public DialogHelper(@ActivityContext Context context) {
        this.context = context;
        dialog = new ProgressDialog(context, R.style.Theme_ProgressDialog);
    }


    public void dismissDialog() {
        dialog.dismiss();
    }

    public void showDialog(String str) {
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(str);
        dialog.show();
    }

    public boolean checkIsShowing() {
        return dialog.isShowing();
    }
}
