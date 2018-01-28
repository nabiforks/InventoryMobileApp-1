package team8.inventorymobileapp.com.inventorymobileapp.Entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import team8.inventorymobileapp.com.inventorymobileapp.JSONParser;

/**
 * Created by Fay on 25/1/2018.
 */

public class Request extends HashMap<String, String> {

   // final static String baseURL = "http://172.17.252.171/InventoryWCF/WCF/DepartmentService.svc/";
    final static String baseURL = "http://192.168.1.181/InventoryWCF/WCF/DepartmentService.svc/";

    public Request(String requestCode, String departmentCode, String dateCreated, String status,String notes,String userName,String empName) {
        put("RequestCode", requestCode);
        put("DepartmentCode",departmentCode );
        put("DateCreated",dateCreated);
        put("Status",status);
        put("Notes",notes);
        put("UserName",userName);
        put("EmpName",empName);
    }

    public Request(String requestCode, String dateCreated,String status, String empName,String notes){
       put("RequestCode",requestCode);
        put("DateCreated",dateCreated);
        put("Status",status);
        put("EmpName",empName);
        put("Notes",notes);
    }


    public Request(){

    }

    public static List<Request> list() {
        List<Request> list = new ArrayList<Request>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "List");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
//
                list.add(new Request(b.getString("RequestCode"), b.getString("DateCreated"), b.getString("Status"),
                       b.getString("EmpName"),b.getString("Notes")));
            }
        } catch (Exception e) {
            Log.e("Request.list()", "JSONArray error");
        }
        return(list);
    }

    public static void updateRequest(Request r) {
        JSONObject jRequest = new JSONObject();
        try {
            jRequest.put("RequestCode",r.get("RequestCode"));
            jRequest.put("Status",r.get("Status"));
            jRequest.put("ApprovedBy",r.get("ApprovedBy"));
            jRequest.put("HeadRemark",r.get("HeadRemark"));

        } catch (Exception e) {
        }
        String result = JSONParser.postStream(baseURL+"Update", jRequest.toString());
    }



}


