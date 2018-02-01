package team8.inventorymobileapp.com.inventorymobileapp.BusinessLogic;

import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingDisbursementDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.ConfirmedDisbursement;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.OutstandingRequest;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.RequestListActivity;

/**
 * Created by natha on 1/29/2018.
 */

public class StoreClerkController {
    public List<HashMap<String,String>> getCollectionPointList(List<ConfirmedDisbursement> cdList){
        HashSet<String> collectionPointList = new HashSet<>();
        for (ConfirmedDisbursement item: cdList) {
            collectionPointList.add(item.get("CollectionPoint"));
        }
        List<HashMap<String,String>> cpList = new ArrayList<>();
        HashMap<String, String> hmAll = new HashMap<>();
        hmAll.put("CollectionPoint","All");
        cpList.add(hmAll);
        for (String s : collectionPointList){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("CollectionPoint",s);
            cpList.add(hm);
        }

        return cpList;
    }

    public List<ConfirmedDisbursement> getConfirmedDisbursementByCollectionPoint(List<ConfirmedDisbursement> cdList, String collectionPoint){
        if (collectionPoint.toLowerCase().equals("all")){
            return cdList;
        }
        List<ConfirmedDisbursement> scdList = new ArrayList<>();
        for (ConfirmedDisbursement cd:cdList){
            if (cd.get("CollectionPoint").equals(collectionPoint)){
                scdList.add(cd);
            }
        }
        return scdList;
    }


}
