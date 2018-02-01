package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/29/2018.
 */

public class ConfirmedDisbursement extends java.util.HashMap<String,String>{
    //final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";

    public ConfirmedDisbursement(String clerkInCharge, String collectionPoint, String collectionTime, String departmentCode, String departmentName, String disbursementDate, String representative, String notes, String status, String disbursementCode) {
        put("ClerkInCharge", clerkInCharge);
        put("CollectionPoint", collectionPoint);
        put("CollectionTime", collectionTime);
        put("DepartmentCode", departmentCode);
        put("DepartmentName", departmentName);
        put("DisbursementDate", disbursementDate);
        put("Representative", representative);
        put("Notes", notes);
        put("Status", status);
        put("DisbursementCode", disbursementCode);
    }

    public ConfirmedDisbursement(){}

    public static List<ConfirmedDisbursement> getListConfirmedDisbursement(String email,String password) {
        List<ConfirmedDisbursement> addList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"ConfirmedDisbursements/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                addList.add(new ConfirmedDisbursement(b.getString("ClerkInCharge"),b.getString("CollectionPoint"),b.getString("CollectionTime"), b.getString("DepartmentCode"), b.getString("DepartmentName"), b.getString("DisbursementDate"), b.getString("Representative"), b.getString("Notes"), b.getString("Status"), b.getString("DisbursementCode")));
            }
        } catch (Exception e) {
            Log.e("ConfirmDisburse.list()", "JSONArray error");
        }
        return addList;
    }

    public static ConfirmedDisbursement getDisbursement(String departmentCode,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"ConfirmedDisbursement/"+departmentCode+"/"+email+"/"+password);
        try {
            return new ConfirmedDisbursement(b.getString("ClerkInCharge"),b.getString("CollectionPoint"),b.getString("CollectionTime"), b.getString("DepartmentCode"), b.getString("DepartmentName"), b.getString("DisbursementDate"), b.getString("Representative"), b.getString("Notes"), b.getString("Status"), b.getString("DisbursementCode"));
        } catch (Exception e) {
            Log.e("getDisbursement()", "JSONArray error");
        }
        return(null);
    }
}
