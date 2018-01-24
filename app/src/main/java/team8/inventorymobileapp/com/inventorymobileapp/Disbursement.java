package team8.inventorymobileapp.com.inventorymobileapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Trang Pham on 1/24/2018.
 */

public class Disbursement extends HashMap<String, String> {
    final static String ListDisbursementURL = "http://172.20.10.4/InventoryWCF/WCF/EmployeeService.svc/ListDisbursement";


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
}
