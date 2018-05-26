package com.am.reachwell.User.ViewModel;

import com.am.reachwell.Global.Helpers.NetworkHelper;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.User.Interfaces.ApiCalls.DashboardApiCallsInterface;
import com.am.reachwell.User.Interfaces.Views.DahsboardViewInterface;
import com.am.reachwell.User.Services.ApiCalls.UserApiCalls;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class DashBoardViewModel implements DashboardApiCallsInterface {
    private UserApiCalls userApiCalls;
    private NetworkHelper networkHelper;
    private DahsboardViewInterface dahsboardViewInterface;

    @Inject
    public DashBoardViewModel(UserApiCalls userApiCalls,NetworkHelper networkHelper){
        this.userApiCalls = userApiCalls;
        this.networkHelper = networkHelper;
        this.userApiCalls.setDashboardApiCallsInterface(this);
    }

    public void setDahsboardViewInterface(DahsboardViewInterface dahsboardViewInterface){
        this.dahsboardViewInterface = dahsboardViewInterface;
    }

    public void getNotifications(){
        if (networkHelper.checkNetwork()){
            userApiCalls.getNotifications();
        }
    }

    @Override
    public void onNotificationSuccess(JSONRequestRepsonse response) {
        if (response.Success){
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                JSONObject data = jsonObject.getJSONObject("data");
                String notification = data.getString("Notifications");
                String mails = data.getString("Mails");
                dahsboardViewInterface.showNotifications(notification,mails);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
