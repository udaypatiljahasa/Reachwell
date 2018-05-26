package com.am.reachwell.Global.Models;

import android.content.ContentValues;

import com.am.reachwell.Assets.Constant.AssetConstant;
import com.am.reachwell.Global.Constants.ApiCallsConstant;
import com.am.reachwell.Global.Constants.TableConstants;
import com.am.reachwell.Global.Helpers.SQliteDatabase;
import com.am.reachwell.Global.Interface.ApiCallsInterface;
import com.am.reachwell.Global.Services.ApiCalls.GetEmployees;
import com.am.reachwell.Global.Services.ApiCalls.JSONRequestRepsonse;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Data;

@Data
public class EmployeesModel implements ApiCallsInterface {
    String cm_ouguid;
    String cm_group_guid;
    String employee_code;
    String employee_type;
    String first_name;
    String middle_name;
    String last_name;
    int employee_status;

    private SQliteDatabase sQliteDatabase;


    private GetEmployees getEmployees;

    @Inject
    public EmployeesModel(SQliteDatabase sQliteDatabase,GetEmployees getEmployees){
        this.sQliteDatabase = sQliteDatabase;
        this.getEmployees = getEmployees;
        this.getEmployees.setApiCallsListener(this);
    }

    public void getEmployees(){
        try {
            getEmployees.getData(Request.Method.POST, ApiCallsConstant.GET_EMPLOYYES, null, AssetConstant.REQUEST_GET_EMPLOYYES, null);
        } catch (Exception e){

        }
    }

    public void insertClassData(JSONArray classList) throws Exception{
        ArrayList<ContentValues> classDataList = new ArrayList<>();
        for (int i=0;i<classList.length();i++){
            ContentValues contentValues = new ContentValues();
            JSONObject classObject = classList.getJSONObject(i);
            contentValues.put(TableConstants.TABLE_EMPLOYEE._ID , classObject.getString("CM_EmployeeGUID"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.CM_OUGUID , classObject.getString("CM_OUGUID"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.CM_GROUPGUID , classObject.getString("CM_GroupGUID"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.EMPLOYEE_CODE , classObject.getString("EmployeeCode"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.FIRST_NAME , classObject.getString("FirstName"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.MIDDLE_NAME ,classObject.getString("MiddleName"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.LAST_NAME ,classObject.getString("LastName"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.EMPLOYEE_STATUS ,classObject.getString("EmployeeStatus"));
            contentValues.put(TableConstants.TABLE_EMPLOYEE.EMPLOYEE_TYPE ,classObject.getString("EmployeeType"));
            classDataList.add(contentValues);
        }
        sQliteDatabase.checkAndInsertData(TableConstants.TABLE_EMPLOYEE.TABLE_NAME,classDataList,TableConstants.TABLE_EMPLOYEE._ID);
    }


    public ArrayList<ContentValues> getAllEmployees() throws Exception{
        return sQliteDatabase.getAllData(TableConstants.TABLE_EMPLOYEE.TABLE_NAME);
    }

    @Override
    public void success(JSONRequestRepsonse response) {
        if (response.Success) {
            try {
                JSONObject jsonObject = new JSONObject(response.ResponseData);
                insertClassData(jsonObject.getJSONArray("rows"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(JSONRequestRepsonse response) {

    }
}
