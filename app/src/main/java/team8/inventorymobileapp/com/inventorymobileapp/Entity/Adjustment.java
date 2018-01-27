package team8.inventorymobileapp.com.inventorymobileapp.Entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.JSONParser;


/**
 * Created by Amit on 25/1/2018.
 */

public class Adjustment extends HashMap<String ,String>{

    //final static String baseURL = "http://172.17.254.27/InventoryWCF/WCF/SupervisorService.svc/";

    final static String baseURL = "http://192.168.0.163/InventoryWCF/WCF/SupervisorService.svc/";


    public Adjustment(String adjustmentCode, String itemCode, String price, String adjustmentQuant, String stock, String reason, String remark) {
        put("AdjustmentCode", adjustmentCode);
        put("ItemCode", itemCode);
        put("Price", price);
        put("AdjustmentQuant", adjustmentQuant);
        put("Stock", stock);
        put("Reason", reason);
        put("Remark", remark);



    }
public  Adjustment(String adjustmentCode,String status,String dateOfApprove,String approvedBy,String headRemarks )
{
    put("AdjustmentCode", adjustmentCode);
    put("Status", status);
    put("DateOfApproved", dateOfApprove);
    put("ApprovedBy", approvedBy);
    put("Remark", headRemarks);

}

    public Adjustment(String adjustmentCode, String itemCode,  String reason) {
        put("AdjustmentCode", adjustmentCode);
        put("ItemCode", itemCode);
        put("Reason", reason);
    }

public  Adjustment(){}


    public static List<Adjustment> listOfAdjustmentBySupervisor() {
        List<Adjustment> list = new ArrayList<Adjustment>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "PendingSupervisor");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Adjustment(b.getString("AdjustmentCode"), b.getString("ItemCode"),b.getString(("Reason"))));
            }
        } catch (Exception e) {
            Log.e("Adjustment.list()", "JSONArray error");
        }
        return list;
    }

    public static Adjustment getAdjust(String adjustmentId) {
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "Adjustment/" + adjustmentId);
        try {
            return new Adjustment(b.getString("AdjustmentCode"), b.getString("ItemCode"),
                    b.getString("Price"), b.getString("AdjustmentQuant"),b.getString("Stock"),
                   b.getString("Reason"),b.getString("Remark"));
        } catch (Exception e) {
            Log.e("Adjustment.getAdjust()", "JSONArray error");
        }
        return(null);
    }

//"http://172.17.254.27/InventoryWCF/WCF/SupervisorService.svc/UpdateAdSupervisor"
    public static void updateAdjustment(Adjustment adjustment) {
        JSONObject jadjustment = new JSONObject();

        try {
            jadjustment.put("AdjustmentCode", adjustment.get("AdjustmentCode"));
            jadjustment.put("Status", adjustment.get("Status"));
            jadjustment.put("DateOfApprove",adjustment.get("DateOfApproved"));
            jadjustment.put("ApprovedBy", adjustment.get("ApprovedBy"));
            jadjustment.put("Remark", adjustment.get("Remark"));
        } catch (Exception e) {
        }
        //Log.d("AMIT","http://172.17.254.27/InventoryWCF/WCF/SupervisorService.svc/UpdateAdSupervisor");
        Log.d("AMIT",jadjustment.toString());
        String result = JSONParser.postStream("http://192.168.0.163/InventoryWCF/WCF/SupervisorService.svc/UpdateAdSupervisor", jadjustment.toString());
        Log.d("AMIT",result);
    }


}
