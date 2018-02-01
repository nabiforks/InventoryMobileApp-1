package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 18/01/27.
 */

    public class Disbursement extends HashMap<String, String> {
        final static String ListDisbursementURL = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/EmployeeService.svc/ListDisbursement";
        final static String UpdateDisbursementURL = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/EmployeeService.svc/UpdateDisbursement";

        public Disbursement(String DisbursementCode, String DepartmentName, String Status,String RepName) {
            put("DisbursementCode", DisbursementCode);
            put("DepartmentName", DepartmentName);
            put("Status", Status);
            put("RepName",RepName);
        }
        public Disbursement(){}

        public static ArrayList<Disbursement> dList(String depCode,String email,String password) {
            ArrayList<Disbursement> list = new ArrayList<Disbursement>();
            JSONArray a = JSONParser.getJSONArrayFromUrl(ListDisbursementURL+"/"+depCode+"/"+email+"/"+password);
            try {
                for (int i = 0; i < a.length(); i++) {
                    JSONObject b = a.getJSONObject(i);
                    list.add(new Disbursement(
                            b.getString("DisbursementCode"),
                            b.getString("DepartmentName"),
                            b.getString("Status"),
                            b.getString("RepName")));
                }
            } catch (Exception e) {
                Log.e("Disbursement.dList()", "JSONArray error");
            }
            return (list);
        }
        public static void UpdateDisbursement(Disbursement disbursement,String email,String password)
        {
            JSONObject object = new JSONObject();
            try{
                object.put("DisbursementCode", disbursement.get("DisbursementCode"));
                object.put("DepartmentName", disbursement.get("DepartmentName"));
                object.put("Status", disbursement.get("Status"));
                object.put("RepName", disbursement.get("RepName"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            String result =  JSONParser.postStream( UpdateDisbursementURL+"/"+email+"/"+password, object.toString());
        }
    }

