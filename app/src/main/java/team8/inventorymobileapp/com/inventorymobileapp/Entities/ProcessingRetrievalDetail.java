package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/25/2018.
 */

public class ProcessingRetrievalDetail extends java.util.HashMap<String,String> {
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";
    //final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";

    public ProcessingRetrievalDetail(String dateRetrieved, String itemName, String notes, String quantityNeeded,String quantityRetrieved, String retrievalCode, String status, String stock, String location, String itemCode) {
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

    public ProcessingRetrievalDetail(){}

    public static List<ProcessingRetrievalDetail> getListRetrievalDetai(String email,String password) {
        List<ProcessingRetrievalDetail> rdList = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"ProcessingRetrievalDetails/"+email+"/"+password);
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                rdList.add(new ProcessingRetrievalDetail(b.getString("DateRetrieved"),b.getString("ItemName"),b.getString("Notes"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("RetrievalCode"), b.getString("Status"), b.getString("Stock"), b.getString("Location"), b.getString("ItemCode")));
            }
        } catch (Exception e) {
            Log.e("RetrievalDetail.list()", "JSONArray error");
        }
        return rdList;
    }

    public static ProcessingRetrievalDetail getRetrievalDetail(String itemCode,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"ProcessingRetrievalDetail/"+itemCode+"/"+email+"/"+password);
        try {
            return new ProcessingRetrievalDetail(b.getString("DateRetrieved"),b.getString("ItemName"),b.getString("Notes"), b.getString("QuantityNeeded"), b.getString("QuantityRetrieved"), b.getString("RetrievalCode"), b.getString("Status"), b.getString("Stock"), b.getString("Location"), b.getString("ItemCode"));
        } catch (Exception e) {
            Log.e("getRetrievalDetail()", "JSONArray error");
        }
        return(null);
    }

    public static void updateRetrievalDetail(ProcessingRetrievalDetail rd,String email,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Notes", rd.get("Notes"));
            jsonObject.put("ItemCode", rd.get("ItemCode"));

            if(rd.get("QuantityRetrieved") .equals("null")|| rd.get("QuantityRetrieved").isEmpty()||rd.get("QuantityRetrieved").equals(null) || rd.get("QuantityRetrieved").equals("")){
                jsonObject.put("QuantityRetrieved",0);
            }
            else{
                jsonObject.put("QuantityRetrieved", Integer.parseInt(rd.get("QuantityRetrieved")));
            }

        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"UpdateRetrievalDetail/"+email+"/"+password, jsonObject.toString());
    }


}
