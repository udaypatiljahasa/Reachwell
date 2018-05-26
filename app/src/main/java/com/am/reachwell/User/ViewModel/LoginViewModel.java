package com.am.reachwell.User.ViewModel;

import android.os.Build;

import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.Global.Services.ApiCalls.VolleyApiCalls;
import com.am.reachwell.User.Constants.UserConstants;
import com.am.reachwell.User.Interfaces.ApiCalls.LoginApiCallsInterface;
import com.am.reachwell.User.Services.ApiCalls.UserApiCalls;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class LoginViewModel implements LoginApiCallsInterface {
    private UserApiCalls userApiCalls;
    private NetworkHelper networkHelper;

    @Inject
    public LoginViewModel(UserApiCalls userApiCalls,NetworkHelper networkHelper){
        this.userApiCalls = userApiCalls;
        this.networkHelper = networkHelper;
    }

    public boolean validateUser(String username,String password){
        boolean valid = true;
        if (username == "" || username == null || username.length() == 0){
            valid = false;
        }
        if (password == "" || password == null || password.length() == 0){
            valid = false;
        }
        return valid;
    }

    public void authenticateUser(String username,String password){
        if (validateUser(username,password)) {
            if (networkHelper.checkNetwork()) {
                userApiCalls.checkUserLoginStatus(username, password, null);
            } else {

            }
        } else {

        }
    }

    @Override
    public void onAuthenticationSuccess(JSONRequestRepsonse jsonRequestRepsonse) {

    }
}
