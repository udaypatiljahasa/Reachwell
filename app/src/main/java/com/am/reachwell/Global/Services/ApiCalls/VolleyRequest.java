package com.am.reachwell.Global.Services.ApiCalls;

import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by udaypatil on 19/02/18.
 */

public class VolleyRequest {

    private RequestQueue requestQueue;

    private VolleyResponseListener volleyResponseListener;

    @Inject
    public VolleyRequest(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void setVolleyResponseListener(VolleyResponseListener volleyResponseListener) {
        this.volleyResponseListener = volleyResponseListener;
    }

    public void requestJSONData(int requestType, String requestUrl, JSONObject jsonRequest, final int requestFlag, final String token) {
        JsonObjectRequest feedRequest = new JsonObjectRequest(requestType, requestUrl, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String Status = response.optString("success");
                        if (Status.equals("true")) {
                            JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                            jsonRequestRepsonse.Success = true;
                            jsonRequestRepsonse.RequestFlag = requestFlag;
                            jsonRequestRepsonse.ResponseData = response.toString();
                            volleyResponseListener.successHandler(jsonRequestRepsonse);
                        } else if (Status.contentEquals("error")) {
                            JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                            jsonRequestRepsonse.RequestFlag = requestFlag;
                            jsonRequestRepsonse.Success = false;
                            jsonRequestRepsonse.ResponseData = response.toString();
                            volleyResponseListener.successHandler(jsonRequestRepsonse);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                        jsonRequestRepsonse.RequestFlag = requestFlag;
                        jsonRequestRepsonse.errorMessage = res;
                        jsonRequestRepsonse.exception = error;
                        volleyResponseListener.errorHandler(jsonRequestRepsonse);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (token != null) {
                    params.put("Authorization", token);
                }
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiCallsConstant.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        feedRequest.setRetryPolicy(policy);
        requestQueue.add(feedRequest);
    }

    public void requestJSONArrayData(int requestType, String requestUrlfinal, final int requestFlag) {
        JsonArrayRequest feedRequest = new JsonArrayRequest(requestType, requestUrlfinal, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                        jsonRequestRepsonse.Success = true;
                        jsonRequestRepsonse.RequestFlag = requestFlag;
                        jsonRequestRepsonse.ResponseData = response.toString();
                        volleyResponseListener.successHandler(jsonRequestRepsonse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                        jsonRequestRepsonse.RequestFlag = requestFlag;
                        jsonRequestRepsonse.errorMessage = res;
                        jsonRequestRepsonse.exception = error;
                        volleyResponseListener.errorHandler(jsonRequestRepsonse);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(ApiCallsConstant.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        feedRequest.setRetryPolicy(policy);
        requestQueue.add(feedRequest);
    }

    public void requestStringData(int requestType, String requestUrl, final JSONObject jsonRequest, final int requestFlag, final String token) {
        StringRequest feedRequest = new StringRequest(requestType, requestUrl,
                response -> {
                    try {
                        JSONObject responseData = new JSONObject(response);
                        String Status = responseData.optString("success");
                        if (Status.equals("true")) {
                            JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                            jsonRequestRepsonse.Success = true;
                            jsonRequestRepsonse.RequestFlag = requestFlag;
                            jsonRequestRepsonse.ResponseData = responseData.toString();
                            volleyResponseListener.successHandler(jsonRequestRepsonse);
                        } else if (Status.contentEquals("error")) {
                            JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                            jsonRequestRepsonse.RequestFlag = requestFlag;
                            jsonRequestRepsonse.Success = false;
                            jsonRequestRepsonse.ResponseData = responseData.toString();
                            volleyResponseListener.successHandler(jsonRequestRepsonse);
                        } else if(responseData.optBoolean("error")){
                            JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                            jsonRequestRepsonse.RequestFlag = requestFlag;
                            jsonRequestRepsonse.Success = false;
                            jsonRequestRepsonse.ResponseData = responseData.toString();
                            volleyResponseListener.successHandler(jsonRequestRepsonse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
            NetworkResponse response = error.networkResponse;
            if (response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    JSONRequestRepsonse jsonRequestRepsonse = new JSONRequestRepsonse();
                    jsonRequestRepsonse.RequestFlag = requestFlag;
                    jsonRequestRepsonse.errorMessage = res;
                    jsonRequestRepsonse.exception = error;
                    volleyResponseListener.errorHandler(jsonRequestRepsonse);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (token != null) {
                    params.put("Authorization", token);
                }
                return params;
            }

            @Override
            public String getBodyContentType() {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "application/x-www-form-urlencoded");
                //return pars;
                return "application/x-www-form-urlencoded";
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params = toMap(jsonRequest);
                } catch (Exception e){

                }
//                params.put("data", jsonRequest.toString());
                return params;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiCallsConstant.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        feedRequest.setRetryPolicy(policy);
        requestQueue.add(feedRequest);
    }

    private Map<String, String> toMap(JSONObject jsonobj)  throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> keys = jsonobj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.getString(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value.toString());
        }   return map;
    }

    private List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }   return list;
    }
}
