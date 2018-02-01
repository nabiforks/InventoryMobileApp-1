package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/24/2018.
 */

public class OutstandingRequest extends java.util.HashMap<String,String> {
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";
    //final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";

    public OutstandingRequest(String requestCode, String departmentName, String status) {
        put("RequestCode", requestCode);
        put("DepartmentName", departmentName);
        put("Status", status);
    }

    public OutstandingRequest(){}

    public static List<OutstandingRequest> getListOutstandingRequests(String email,String password) {
        List<OutstandingRequest> rList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"OutstandingRequests/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                rList.add(new OutstandingRequest(b.getString("RequestCode"),b.getString("DepartmentName"),b.getString("Status")));
            }
        } catch (Exception e) {
            Log.e("Request.list()", "JSONArray error");
        }
        return rList;
    }


}
