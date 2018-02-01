package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ASUS on 18/01/25.
 */

public class PODetail extends HashMap<String, String> {
    final static String baseURL = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/SupervisorService.svc/";

    public PODetail(String itemDescription, String price, String quantity) {
        put("ItemDescription", itemDescription);
        put("Price", price);
        put("Quantity", quantity);


    }

    public static List<PODetail>  getPODetail(String id,String email,String password) {
        List<PODetail> list = new ArrayList<PODetail>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "PODetail/" + id+"/"+email+"/"+password);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new PODetail(b.getString("ItemDescription"), b.getString("Price"), b.getString("Quantity")));
            }
        } catch (Exception e) {
            Log.e("PODetail.getPODetail()", "JSONArray error");
        }
        return (list);
    }
}
