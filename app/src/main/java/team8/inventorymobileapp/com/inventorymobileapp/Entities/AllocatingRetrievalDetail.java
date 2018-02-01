package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/27/2018.
 */

public class AllocatingRetrievalDetail extends java.util.HashMap<String,String> {
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";
//final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";

    public AllocatingRetrievalDetail(String dateRetrieved, String itemName, String notes, String quantityNeeded,String quantityRetrieved, String retrievalCode, String status, String stock, String location, String itemCode) {
        put("DateRetrieved", dateRetrieved);
        put("ItemName", itemName);
        put("Notes", notes);
        put("QuantityNeeded", quantityNeeded);
        put("QuantityRetrieved", quantityRetrieved);
        put("RetrievalCode", retrievalCode);
        put("Status", status);
        put("Stock", stock);
        put("Location", location);
        put("ItemCode", itemCode);
    }

    public AllocatingRetrievalDetail(){}

    public static List<AllocatingRetrievalDetail> getListRetrievalDetai(String email,String password) {
        List<AllocatingRetrievalDetail> rdList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"AllocatingRetrievalDetails"+"/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                rdList.add(new AllocatingRetrievalDetail(b.getString("DateRetrieved"),b.getString("ItemName"),b.getString("Notes"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("RetrievalCode"), b.getString("Status"), b.getString("Stock"), b.getString("Location"), b.getString("ItemCode")));
            }
        } catch (Exception e) {
            Log.e("RetrievalDetail.list()", "JSONArray error");
        }
        return rdList;
    }

    public static AllocatingRetrievalDetail getRetrievalDetail(String itemCode,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"AllocatingRetrievalDetail/"+itemCode+"/"+email+"/"+password);
        try {
            return new AllocatingRetrievalDetail(b.getString("DateRetrieved"),b.getString("ItemName"),b.getString("Notes"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("RetrievalCode"), b.getString("Status"), b.getString("Stock"), b.getString("Location"), b.getString("ItemCode"));
        } catch (Exception e) {
            Log.e("getRetrievalDetail()", "JSONArray error");
        }
        return(null);
    }
}