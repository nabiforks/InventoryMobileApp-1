package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import team8.inventorymobileapp.com.inventorymobileapp.JSONParser;

/**
 * Created by Trang Pham on 1/24/2018.
 */

public class Disbursement extends HashMap<String, String> {
    final static String ListDisbursementURL = "http://172.20.10.4/InventoryWCF/WCF/EmployeeService.svc/ListDisbursement";
    final static String UpdateDisbursementURL = "http://172.20.10.4/InventoryWCF/WCF/EmployeeService.svc/UpdateDisbursement";

    public Disbursement(String DisbursementCode, String DepartmentName, String Status) {
        put("DisbursementCode", DisbursementCode);
        put("DepartmentName", DepartmentName);
        put("Status", Status);
    }
    public Disbursement(){}

    public static ArrayList<Disbursement> dList() {
        ArrayList<Disbursement> list = new ArrayList<Disbursement>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(ListDisbursementURL);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Disbursement(
                        b.getString("DisbursementCode"),
                        b.getString("DepartmentName"),
                        b.getString("Status")));
            }
        } catch (Exception e) {
            Log.e("Disbursement.dList()", "JSONArray error");
        }
        return (list);
    }
    public static void UpdateDisbursement(Disbursement disbursement)
    {
        JSONObject object = new JSONObject();
        try{
            object.put("DisbursementCode", disbursement.get("DisbursementCode"));
            object.put("DepartmentName", disbursement.get("DepartmentName"));
            object.put("Status", disbursement.get("Status"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String result =  JSONParser.postStream( UpdateDisbursementURL, object.toString());
    }
}
