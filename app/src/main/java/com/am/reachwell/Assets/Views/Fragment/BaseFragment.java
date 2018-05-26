package com.am.reachwell.Assets.Views.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.am.reachwell.Assets.Dependencies.component.AssetComponent;
import com.am.reachwell.Assets.Dependencies.component.DaggerAssetComponent;
import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.Global.Helpers.DialogHelper;
import com.am.reachwell.R;
import com.am.reachwell.ReachwellApplication;

import javax.inject.Inject;

public class BaseFragment  extends Fragment {

    @Inject
    DialogHelper dialogHelper;



    protected AssetComponent getComponent(){
        return DaggerAssetComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .applicationComponent(ReachwellApplication.get(getActivity()).getComponent())
                .build();
    }

    public void displayProgressDialog() {
        dialogHelper.showDialog(getActivity().getResources().getString(R.string.general_loading_dialog_text));
    }

    public void hideProgressDialog() {
        if (dialogHelper.checkIsShowing())
            dialogHelper.dismissDialog();
    }

    public void showErrorMessage(int errorMessage) {

        showAlertDialog();
    }

    public void showErrorMessage(String errorMessage) {

        showAlertDialog();
    }

    public void showToastMessage(int toastMsgId) {
        Toast.makeText(getActivity(),getActivity().getResources().getString(toastMsgId),Toast.LENGTH_LONG).show();
    }

    public void showToastMessage(String toastMsg) {
        Toast.makeText(getActivity(),toastMsg,Toast.LENGTH_LONG).show();
    }

    private void showAlertDialog(){

    }
}
