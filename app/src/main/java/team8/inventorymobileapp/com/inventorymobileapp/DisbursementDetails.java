package team8.inventorymobileapp.com.inventorymobileapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Trang Pham on 1/24/2018.
 */

public class DisbursementDetails extends HashMap<String, String> {

    final static String DisbursementDetailsURL = "http://172.20.10.4/InventoryWCF/WCF/EmployeeService.svc/DisbursementDetails";

    public DisbursementDetails(String DisbursementCode, String CollectionPointName, String RepName, String Status,
                               String ActualQuantity, String ItemDescription ) {
        put("DisbursementCode", DisbursementCode);
        put("CollectionPointName", CollectionPointName);
        put("RepName", RepName);
        put("Status", Status);
        put("ActualQuantity", ActualQuantity);
        put("ItemDescription", ItemDescription);
    }

    public static DisbursementDetails dDetails(String id) {
        JSONObject b = JSONParser.getJSONFromUrl(DisbursementDetailsURL + "/" + id);
        try {
            return new DisbursementDetails(
                    b.getString("DisbursementCode"),
                    b.getString("CollectionPointName"),
                    b.getString("RepName"),
                    b.getString("Status"),
                    b.getString("ActualQuantity"),
                    b.getString("ItemDescription")
                    );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
