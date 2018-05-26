package com.am.reachwell.Global.Services.ApiCalls;

/**
 * Created by udaypatil on 19/02/18.
 */

public interface VolleyResponseListener {
    void successHandler(JSONRequestRepsonse jsonRequestRepsonse);
    void errorHandler(JSONRequestRepsonse jsonRequestRepsonse);
}
