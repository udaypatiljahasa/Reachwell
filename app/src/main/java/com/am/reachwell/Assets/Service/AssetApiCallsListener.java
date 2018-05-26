package com.am.reachwell.Assets.Service;

import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;

public interface AssetApiCallsListener {
    void success(JSONRequestRepsonse response);
    void error(JSONRequestRepsonse response);
}

