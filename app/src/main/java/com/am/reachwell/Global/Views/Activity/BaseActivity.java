package com.am.reachwell.Global.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

/**
 * Created by udaypatil on 19/02/18.
 */

public class BaseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void addFragment(int containerViewId, Fragment fragment, String backStack) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (backStack != null){
            fragmentTransaction.addToBackStack(backStack);
        }
        fragmentTransaction.replace(containerViewId, fragment).commit();
    }

    private void showAlertDialog(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();

    }

    protected void errorDialogForErrorMessage(){
        showAlertDialog();
    }




    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
