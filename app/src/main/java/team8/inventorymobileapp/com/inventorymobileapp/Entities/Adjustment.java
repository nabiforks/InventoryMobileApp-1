package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Amit on 25/1/2018.
 */

public class Adjustment extends HashMap<String ,String>{

    final static String baseURL = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/SupervisorService.svc/";




    public Adjustment(String adjustmentCode, String itemCode, String price, String adjustmentQuant, String stock, String reason, String remark,String description) {
        put("AdjustmentCode", adjustmentCode);
        put("ItemCode", itemCode);
        put("Price", price);
        put("AdjustmentQuant", adjustmentQuant);
        put("Stock", stock);
        put("Reason", reason);
        put("Remark", remark);
        put("Description",description);



    }
    public  Adjustment(String adjustmentCode,String status,String dateOfApprove,String approvedBy,String headRemarks )
    {
        put("AdjustmentCode", adjustmentCode);
        put("Status", status);
        put("DateOfApproved", dateOfApprove);
        put("ApprovedBy", approvedBy);
        put("Remark", headRemarks);

    }

    public Adjustment(String adjustmentCode,  String reason,String description,String adjustmentQuant) {
        put("AdjustmentCode", adjustmentCode);
       // put("ItemCode", itemCode);
        put("Reason", reason);
        put("Description", description);
        put("AdjustmentQuant", adjustmentQuant);
    }

    public  Adjustment(){

    }


    public static List<Adjustment> listOfAdjustmentBySupervisor(String  email,String password) {
        List<Adjustment> list = new ArrayList<Adjustment>();

        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "PendingAdjustmentSupervisor/"+email+"/"+password);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Adjustment(b.getString("AdjustmentCode"),
                       b.getString(("Reason")),b.getString("ItemDescription"),
                        b.getString("AdjustmentQuant")));
            }
        } catch (Exception e) {
            Log.e("Adjustment.list()", "JSONArray error");
        }
        return list;
    }

    public static Adjustment getAdjust(String adjustmentId,String email,String password) {
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "Adjustment/" + adjustmentId+"/"+email+"/"+password);
        try {
            return new Adjustment(b.getString("AdjustmentCode"), b.getString("ItemCode"),
                    b.getString("Price"), b.getString("AdjustmentQuant"),b.getString("Stock"),
                    b.getString("Reason"),b.getString("Remark"),b.getString("ItemDescription"));
        } catch (Exception e) {
            Log.e("Adjustment.getAdjust()", "JSONArray error");
        }
        return(null);
    }

    //"http://172.17.254.27/InventoryWCF/WCF/SupervisorService.svc/UpdateAdSupervisor"
    public static void updateAdjustment(Adjustment adjustment,String email,String password) {
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
        //Log.d("AMIT",jadjustment.toString());
        String result = JSONParser.postStream(baseURL+"UpdateAdjustmentSupervisor/"+email+"/"+password, jadjustment.toString());
        //Log.d("AMIT",result);
    }


}