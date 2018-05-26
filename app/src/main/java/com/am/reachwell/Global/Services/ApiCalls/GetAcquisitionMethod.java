package com.am.reachwell.Global.Services.ApiCalls;

import com.am.reachwell.Global.Interface.ApiCallsInterface;

import org.json.JSONObject;

import javax.inject.Inject;

public class GetAcquisitionMethod implements VolleyResponseListener {
    private VolleyRequest volleyRequest;
    private ApiCallsInterface apiCallsListener;

    @Inject
    public GetAcquisitionMethod(VolleyRequest volleyRequest){
        this.volleyRequest = volleyRequest;
        volleyRequest.setVolleyResponseListener(this);
    }

    public void setApiCallsListener(ApiCallsInterface apiCallsListener){
        this.apiCallsListener = apiCallsListener;
    }

    public void getData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token){
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
