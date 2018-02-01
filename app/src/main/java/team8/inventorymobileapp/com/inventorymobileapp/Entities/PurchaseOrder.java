package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ASUS on 18/01/24.
 */

public class PurchaseOrder extends HashMap<String, String> {
    final static String baseURL = "http://"+Constant.ipAddress+"/InventoryWCF/WCF/SupervisorService.svc/";

    public PurchaseOrder(String purchaseOrderCode, String dateCreated, String supplierName,String employeeName,String totalPrice,String dateApproved,String status,String approveBy,String remark) {
        put("PurchaseOrderCode", purchaseOrderCode);
        put("DateCreated", dateCreated);
        put("SupplierName", supplierName);
        put("EmployeeName", employeeName);
        put("TotalPrice", totalPrice);
        put("DateApproved",dateApproved);
        put("Status",status);
        put("ApprovedBy",approveBy);
        put ("Remark",remark);


    }
    public PurchaseOrder(String purchaseOrderCode, String dateCreated, String supplierName,String employeeName,String totalPrice) {
        put("PurchaseOrderCode", purchaseOrderCode);
        put("DateCreated", dateCreated);
        put("SupplierName", supplierName);
        put("EmployeeName", employeeName);
        put("TotalPrice", totalPrice);



    }
    public  PurchaseOrder(){}
    public static List<PurchaseOrder> list(String email,String password) {
        List<PurchaseOrder> list = new ArrayList<PurchaseOrder>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Pending/"+email+"/"+password);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new PurchaseOrder(b.getString("PurchaseOrderCode"), b.getString("DateCreated"),b.getString("SupplierName"),
                        b.getString("EmpName"),
                        b.getString("TotalPrice")));
            }
        } catch (Exception e) {
            Log.e("PurchaseOrder.list()", "JSONArray error");
        }
        return(list);
    }
    public static void updatePurchaseOrder(PurchaseOrder po,String email,String password) {
        JSONObject jpurchase = new JSONObject();
        try {
            jpurchase.put("PurchaseOrderCode", po.get("PurchaseOrderCode"));
            jpurchase.put("DateApproved", po.get("DateApproved"));
            jpurchase.put("Status", po.get("Status"));
            jpurchase.put("HeadRemark", po.get("Remark"));
            jpurchase.put("ApprovedBy", po.get("ApprovedBy"));
        } catch (Exception e) {
        }
        String result = JSONParser.postStream(baseURL+"/UpdatePendingPO/"+email+"/"+password, jpurchase.toString());
    }

}
