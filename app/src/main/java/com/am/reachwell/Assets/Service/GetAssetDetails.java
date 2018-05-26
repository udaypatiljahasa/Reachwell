package com.am.reachwell.Assets.Service;

import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.am.reachwell.Global.Services.ApiCalls.VolleyRequest;
import com.am.reachwell.Global.Services.ApiCalls.VolleyResponseListener;

import org.json.JSONObject;

import javax.inject.Inject;

public class GetAssetDetails implements VolleyResponseListener {
    private VolleyRequest volleyRequest;
    private AssetApiCallsListener assetApiCallsListener;

    @Inject
    public GetAssetDetails(VolleyRequest volleyRequest){
        this.volleyRequest = volleyRequest;
        volleyRequest.setVolleyResponseListener(this);
    }

    public void setAssetApiCallsListener(AssetApiCallsListener assetApiCallsListener){
        this.assetApiCallsListener = assetApiCallsListener;
    }

    public void getData(int requestType, String requestUrl, JSONObject jsonRequest, int requestFlag, String token){
        volleyRequest.requestStringData(requestType, requestUrl, jsonRequest, requestFlag,token);
    }

    @Override
    public void successHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        assetApiCallsListener.success(jsonRequestRepsonse);
    }

    @Override
    public void errorHandler(JSONRequestRepsonse jsonRequestRepsonse) {
        assetApiCallsListener.error(jsonRequestRepsonse);
    }
}
