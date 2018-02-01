package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingDisbursementDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class AllocationDetailActivity extends StoreClerkActivity {
    User user = null;
    final static int[] ids = {R.id.adEtxDescription, R.id.adEtxStock, R.id.adEtxRetrieved};
    final static String[] keys = {"ItemName", "Stock", "QuantityRetrieved"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation_detail);
        user =     new User(AllocationDetailActivity.this);
        final ListView lvAllocationDetail = findViewById(R.id.lvAllocationDetail);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_allocation_detail, lvAllocationDetail, false);
        lvAllocationDetail.addHeaderView(header, null, false);

        Intent i = getIntent();
        String itemCode = i.getStringExtra("itemCode");

        new AsyncTask<String, Void, AllocatingRetrievalDetail>() {
            @Override
            protected AllocatingRetrievalDetail doInBackground(String... params) {
                //testEdit.setText("testing");
                return AllocatingRetrievalDetail.getRetrievalDetail(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(AllocatingRetrievalDetail result) {
                show(result);
            }
        }.execute(itemCode);

        new AsyncTask<String, Void, List<AllocatingDisbursementDetail>>() {
            @Override
            protected List<AllocatingDisbursementDetail> doInBackground(String... strings) {
                return AllocatingDisbursementDetail.getListAllocatingDisbursementDetail(strings[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<AllocatingDisbursementDetail> result) {
                super.onPostExecute(result);
                BaseAdapter ba = new SimpleAdapter(AllocationDetailActivity.this, result, R.layout.row_allocation_detail, new String[]{"DepartmentName", "QuantityNeeded", "QuantityRetrieved"}, new int[]{R.id.adTvDepartment,R.id.adTvNeededQuantity, R.id.adTvDisbursedQuantity});
                lvAllocationDetail.setAdapter(ba);
            }
        }.execute(itemCode);
    }

    void show(AllocatingRetrievalDetail ard){
        for (int i = 0; i<keys.length; i++){
            EditText e = (EditText) findViewById(ids[i]);
            if (ard.get(keys[i]).toLowerCase()=="null"){
                e.setText("");
            } else{
                e.setText(ard.get(keys[i]));
            }
        }
    }
}
