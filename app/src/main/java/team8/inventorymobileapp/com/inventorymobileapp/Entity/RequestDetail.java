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

public class RequestDetail extends HashMap<String,String>{

    //final static String baseURL = "http://172.17.252.171/InventoryWCF/WCF/DepartmentService.svc/";
    final static String baseURL = "http://192.168.1.181/InventoryWCF/WCF/DepartmentService.svc/";

    public RequestDetail(String itemCode, String categoryCode, String description, String quantity) {
        put("ItemCode",itemCode);
        put("CategoryCode",categoryCode);
        put("Description",description);
        put("Quantity",quantity);
    }




    public RequestDetail(){

    }

    public static List<RequestDetail> list(String rCode) {
        List<RequestDetail> list = new ArrayList<RequestDetail>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Detail/"+rCode);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
//
                list.add(new RequestDetail(b.getString("ItemCode"), b.getString("CategoryCode"), b.getString("Description"),
                        b.getString("Quantity")));
            }
        } catch (Exception e) {
            Log.e("RequestDetail.list()", "JSONArray error");
        }
        return(list);
    }
}
