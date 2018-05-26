package com.am.reachwell.Global.Interface;

import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;

public interface ApiCallsInterface {
    void success(JSONRequestRepsonse response);
    void error(JSONRequestRepsonse response);
}
