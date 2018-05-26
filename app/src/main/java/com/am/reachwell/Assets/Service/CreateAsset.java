package com.am.reachwell.Assets.Service;

import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.Global.Services.ApiCalls.VolleyRequest;
import com.am.reachwell.Global.Services.ApiCalls.VolleyResponseListener;

import org.json.JSONObject;

import javax.inject.Inject;

public class CreateAsset implements VolleyResponseListener {
    private VolleyRequest volleyRequest;
    private ApiCallsInterface apiCallsListener;

    @Inject
    public CreateAsset(VolleyRequest volleyRequest){
        this.volleyRequest = volleyRequest;
        volleyRequest.setVolleyResponseListener(this);
    }

    public void setApiCallsListener(ApiCallsInterface apiCallsListener){
        this.apiCallsListener = apiCallsListener;
    }

    public void setData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token){
        volleyRequest.requestJSONData(requestType, requestUrl, jsonRequest, requestFlag,token);
    }

    @Override
    public void successHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        apiCallsListener.success(jsonRequestRepsonse);
    }

    @Override
    public void errorHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        apiCallsListener.error(jsonRequestRepsonse);
    }

}
