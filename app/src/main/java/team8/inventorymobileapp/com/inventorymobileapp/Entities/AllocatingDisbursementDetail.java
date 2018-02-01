package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/27/2018.
 */

public class AllocatingDisbursementDetail extends java.util.HashMap<String,String> {
    //final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";

    public AllocatingDisbursementDetail(String departmentCode, String departmentName, String disbursementCode, String itemCode, String itemName, String quantityNeeded, String quantityRetrieved, String status, String notes, String quantityActual, String requestCode) {
        put("DepartmentCode", departmentCode);
        put("DepartmentName", departmentName);
        put("DisbursementCode", disbursementCode);
        put("ItemCode", itemCode);
        put("ItemName", itemName);
        put("QuantityNeeded", quantityNeeded);
        put("QuantityRetrieved", quantityRetrieved);
        put("Status", status);
        put("Notes", notes);
        put("QuantityActual", quantityActual);
        put("RequestCode", requestCode);
    }

    public AllocatingDisbursementDetail(){}

    public static List<AllocatingDisbursementDetail> getListAllocatingDisbursementDetail(String itemCode,String email,String password) {
        List<AllocatingDisbursementDetail> addList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"AllocatingDisbursementDetails/" + itemCode+"/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                addList.add(new AllocatingDisbursementDetail(b.getString("DepartmentCode"),b.getString("DepartmentName"),b.getString("DisbursementCode"), b.getString("ItemCode"), b.getString("ItemName"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("Status"), b.getString("Notes"), b.getString("QuantityActual"), b.getString("RequestCode")));
            }
        } catch (Exception e) {
            Log.e("AlocatigDisbDet.list()", "JSONArray error");
        }
        return addList;
    }

    public static AllocatingDisbursementDetail getAllocatingDisbursementDetail(String itemCode, String departmentName,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"AllocatingDisbursementDetails/"+itemCode+"/"+departmentName+"/"+email+"/"+password);
        try {
            return new AllocatingDisbursementDetail(b.getString("DepartmentCode"),b.getString("DepartmentName"),b.getString("DisbursementCode"), b.getString("ItemCode"), b.getString("ItemName"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("Status"), b.getString("Notes"), b.getString("QuantityActual"), b.getString("RequestCode"));
        } catch (Exception e) {
            Log.e("getAllocatingDisbDet()", "JSONArray error");
        }
        return(null);
    }

    public static List<AllocatingDisbursementDetail> getDisbursementDetails(String departmentCode,String email,String password) {
        List<AllocatingDisbursementDetail> addList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"ConfirmedDisbursementDetails/" + departmentCode+"/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                addList.add(new AllocatingDisbursementDetail(b.getString("DepartmentCode"),b.getString("DepartmentName"),b.getString("DisbursementCode"), b.getString("ItemCode"), b.getString("ItemName"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("Status"), b.getString("Notes"), b.getString("QuantityActual"), b.getString("RequestCode")));
            }
        } catch (Exception e) {
            Log.e("DisbursemtDetail.list()", "JSONArray error");
        }
        return addList;
    }

    public static AllocatingDisbursementDetail getDisbursementDetail(ArrayList<String> threeCode,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"DisbursementDetail/"+threeCode.get(0)+"/"+threeCode.get(1)+"/"+threeCode.get(2)+"/"+email+"/"+password);
        try {
            return new AllocatingDisbursementDetail(b.getString("DepartmentCode"),b.getString("DepartmentName"),b.getString("DisbursementCode"), b.getString("ItemCode"), b.getString("ItemName"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("Status"), b.getString("Notes"), b.getString("QuantityActual"), b.getString("RequestCode"));
        } catch (Exception e) {
            Log.e("getAllocatingDisbDet()", "JSONArray error");
        }
        return(null);
    }

    public static void updateDisbursementDetail(AllocatingDisbursementDetail rd,String email,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DisbursementCode", rd.get("DisbursementCode"));
            jsonObject.put("ItemCode", rd.get("ItemCode"));
            jsonObject.put("RequestCode", rd.get("RequestCode"));
            jsonObject.put("QuantityActual", rd.get("QuantityActual"));
            jsonObject.put("Notes", rd.get("Notes"));

        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"UpdateDisbursementDetail"+"/"+email+"/"+password, jsonObject.toString());
    }

    public static void markAsNotCollected(String disbursementCode,String email,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DisbursementCode", disbursementCode);

        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"MarkAsNotCollected/"+email+"/"+password, jsonObject.toString());
    }

}
