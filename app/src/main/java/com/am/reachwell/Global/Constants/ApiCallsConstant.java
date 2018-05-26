package com.am.reachwell.Global.Constants;

public interface ApiCallsConstant {
    int SOCKET_TIMEOUT = 300000;

//    String END_POINT = "http://122.166.13.181:900/";
//    String END_POINT = "http://192.168.43.134:83/";
    String END_POINT = "http://192.168.0.18:83/";
    String REQUEST_GET_NOTIFICATIONS = END_POINT + "rechrequest.php?dashboard=true";
    String GET_ASSET_CLASS = END_POINT + "asset/getAllAssetClass";
    String GET_ASSET_STATUS = END_POINT + "asset/getAllAssetStatus";
    String GET_ASSET_DETAILS_LIST = END_POINT + "asset/getallassets";
    String GET_ASSET_DETAIL = END_POINT + "asset/getAsset";

    String GET_AQUISITION_METHOD = END_POINT + "asset/getAllAcquisitionMethod";
    String GET_EMPLOYYES = END_POINT + "employee/getAllEmployee";
    String GET_SUPPLIER = END_POINT + "master/getAllSupplier";
    String GET_CONTRACT_TYPE = END_POINT + "asset/getAllContractType";
    String GET_ACCOUNT_HEAD = END_POINT + "asset/getAllAccountHead";
    String CREATE_ASSET = END_POINT + "asset/createAsset";
    String GET_LOCATION_TYPE="master/getAllLocationType";
    String GET_LOCATION="master/getAllLocation";
}
