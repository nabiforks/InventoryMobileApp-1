package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by natha on 1/28/2018.
 */

public class CollectionDate extends HashMap<String, String> {
    final static String host = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/ClerkService.svc/";
    //final static String host = "http://192.168.1.112/InventoryWCF/WCF/ClerkService.svc/";
    public CollectionDate(String datePlanToCollect, String status){
        put("DatePlanToCollect", datePlanToCollect);
        put("Status", status);
    }

    public CollectionDate(){}

    public static CollectionDate getCollectionDate(String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(host +"GetCollectionDate/"+email+"/"+password);
        try {
            return new CollectionDate(b.getString("DatePlanToCollect"),b.getString("Status"));
        } catch (Exception e) {
            Log.e("getCollectioNDate()", "JSONArray error");
        }
        return(null);
    }
}
