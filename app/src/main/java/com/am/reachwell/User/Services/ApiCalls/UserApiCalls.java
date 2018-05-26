package com.am.reachwell.User.Services.ApiCalls;

import android.os.Build;

import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.Global.Services.ApiCalls.UserApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.VolleyApiCalls;
import com.am.reachwell.User.Constants.UserConstants;
import com.am.reachwell.User.Interfaces.ApiCalls.DashboardApiCallsInterface;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import javax.inject.Inject;

public class UserApiCalls implements UserApiCallsInterface {
    private VolleyApiCalls volleyApiCalls;
    private DashboardApiCallsInterface dashboardApiCallsInterface;

    @Inject
    public UserApiCalls(VolleyApiCalls volleyApiCalls){
        this.volleyApiCalls = volleyApiCalls;
        this.volleyApiCalls.setUserApiCallsInterface(this);
    }

    public void setDashboardApiCallsInterface(DashboardApiCallsInterface dashboardApiCallsInterface){
        this.dashboardApiCallsInterface = dashboardApiCallsInterface;
    }

    public void checkUserLoginStatus(String userName, String password, String deviceId) {
        JSONObject jsonBody = new JSONObject();
        try {
            String versionRelease = Build.VERSION.RELEASE;
            String fieldName = null;
            Field[] fields = Build.VERSION_CODES.class.getFields();
            for (Field field : fields) {
                fieldName = field.getName();
            }
            jsonBody.put("os_version", versionRelease);
            jsonBody.put("os", fieldName);
            jsonBody.put("mobile", userName);
            jsonBody.put("password", password);
            jsonBody.put("device_id", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyApiCalls.requestJSONData(Request.Method.POST, ApiCallsConstant.REQUEST_GET_NOTIFICATIONS, jsonBody, UserConstants.REQUEST_FLAG_CHECK_USER_LOGIN_STATUS, null);
    }

    public void getNotifications(){
        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("dashboard", true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        volleyApiCalls.requestStringData(Request.Method.POST, ApiCallsConstant.REQUEST_GET_NOTIFICATIONS, jsonBody, UserConstants.REQUEST_GET_NOTIFICATIONS, null);

    }

    @Override
    public void successHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        switch (jsonRequestRepsonse.RequestFlag){
            case UserConstants.REQUEST_GET_NOTIFICATIONS:
                dashboardApiCallsInterface.onNotificationSuccess(jsonRequestRepsonse);
                break;
        }
    }

    @Override
    public void errorHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        switch (jsonRequestRepsonse.RequestFlag){
            case UserConstants.REQUEST_GET_NOTIFICATIONS:
                dashboardApiCallsInterface.onNotificationSuccess(jsonRequestRepsonse);
                break;
        }
    }
}
