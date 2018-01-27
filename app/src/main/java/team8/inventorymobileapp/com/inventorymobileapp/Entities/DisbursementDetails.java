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

public class DisbursementDetails extends HashMap<String, String> {

    final static String DisbursementDetailsURL = "http://192.168.1.225/InventoryWCF/WCF/EmployeeService.svc/DisbursementDetails";
   // final static String UpdateDisbursementURL = "http://172.20.10.4/InventoryWCF/WCF/EmployeeService.svc/UpdateDisbursement";

    public DisbursementDetails(String DisbursementCode, String CollectionPointName, String NeededQuantity, String DepartmentName , String RepName, String Status,
                               String ActualQuantity, String ItemDescription ) {
        put("DisbursementCode", DisbursementCode);
        put("CollectionPointName", CollectionPointName);
        put("NeededQuantity", NeededQuantity);
        put("DepartmentName", DepartmentName);
        put("RepName", RepName);
        put("Status", Status);
        put("ActualQuantity", ActualQuantity);
        put("ItemDescription", ItemDescription);
    }
    public static ArrayList<DisbursementDetails> dDetails(String id) {
        ArrayList<DisbursementDetails> list = new ArrayList<DisbursementDetails>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(DisbursementDetailsURL + "/" + id);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new DisbursementDetails(
                        b.getString("DisbursementCode"),
                        b.getString("CollectionPointName"),
                        b.getString("NeededQuantity"),
                        b.getString("DepartmentName"),
                        b.getString("RepName"),
                        b.getString("Status"),
                        b.getString("ActualQuantity"),
                        b.getString("ItemDescription")));
            }
        } catch (Exception e) {
            Log.e("Disbursement.dDetails()", "JSONArray error");
        }
        return list;
    }
}
