package com.am.reachwell.Global.Services.ApiCalls;

import com.am.reachwell.Assets.Service.AssetApiCallsListener;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.Global.Services.ApiCalls.VolleyRequest;
import com.am.reachwell.Global.Services.ApiCalls.VolleyResponseListener;

import org.json.JSONObject;

import javax.inject.Inject;

public class GetAssetStatus implements VolleyResponseListener {
    private VolleyRequest volleyRequest;
    private ApiCallsInterface apiCallsInterface;

    @Inject
    public GetAssetStatus(VolleyRequest volleyRequest){
        this.volleyRequest = volleyRequest;
        volleyRequest.setVolleyResponseListener(this);
    }

    public void setApiCallsListener(ApiCallsInterface apiCallsInterface){
        this.apiCallsInterface = apiCallsInterface;
    }

    public void getData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token){
        volleyRequest.requestJSONData(requestType, requestUrl, jsonRequest, requestFlag,token);
    }

    @Override
    public void successHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        apiCallsInterface.success(jsonRequestRepsonse);
    }

    @Override
    public void errorHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        apiCallsInterface.error(jsonRequestRepsonse);
    }
}
