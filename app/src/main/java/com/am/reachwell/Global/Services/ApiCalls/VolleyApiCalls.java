package com.am.reachwell.Global.Services.ApiCalls;


import com.am.reachwell.User.Constants.UserConstants;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class VolleyApiCalls implements VolleyResponseListener{
    private VolleyRequest volleyRequest;
    private UserApiCallsInterface userApiCallsInterface;


    @Inject
    public VolleyApiCalls(VolleyRequest volleyRequest){
        this.volleyRequest = volleyRequest;
        this.volleyRequest.setVolleyResponseListener(this);
    }

    public void setUserApiCallsInterface(UserApiCallsInterface userApiCallsInterface){
        this.userApiCallsInterface = userApiCallsInterface;
    }

    public void requestJSONData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token){
        volleyRequest.requestJSONData(requestType, requestUrl, jsonRequest, requestFlag,token);
    }

    public void requestJSONArrayData(int requestType, String requestUrlfinal, int requestFlag) {
        volleyRequest.requestJSONArrayData(requestType,requestUrlfinal,requestFlag);
    }

    public void requestStringData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token) {
        volleyRequest.requestStringData(requestType, requestUrl, jsonRequest, requestFlag,token);
    }

    @Override
    public void successHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        switch (jsonRequestRepsonse.RequestFlag){
            case UserConstants.REQUEST_GET_NOTIFICATIONS:
                userApiCallsInterface.successHandler(jsonRequestRepsonse);
                break;

        }
    }

    @Override
    public void errorHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        switch (jsonRequestRepsonse.RequestFlag){
            case UserConstants.REQUEST_GET_NOTIFICATIONS:
                userApiCallsInterface.errorHandler(jsonRequestRepsonse);
                break;
        }
    }
}
