package team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.PODetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.PurchaseOrder;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.SupervisorActivity;

public class PendingPODetailActivity extends SupervisorActivity {

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_podetail);
        user = new User(PendingPODetailActivity.this);
        Intent intent=getIntent();
        final HashMap<String,String> po= (HashMap<String,String>)intent.getSerializableExtra("PurchaseOrder");
        int[] ids = {R.id.lblSupplierName, R.id.lblDateCreated , R.id.lblTotalPrice, R.id.lblEmpName};
        String[] keys = {"SupplierName", "DateCreated", "TotalPrice", "EmployeeName"};

        final ListView lv = (ListView) findViewById(R.id.listViewPOItem);
        for (int i = 0; i < keys.length; i++) {
            TextView e = (TextView) findViewById(ids[i]);
            e.setText( po.get(keys[i]));
        }

        new AsyncTask<String, Void, List<PODetail>>() {
            @Override
            protected List<PODetail> doInBackground(String ... params) {
                return PODetail.getPODetail(params[0],user.getEmail(),user.getPassword());
            }

            @Override
            protected void onPostExecute(final List<PODetail> result) {

                lv.setAdapter(new SimpleAdapter
                        (PendingPODetailActivity.this, result, R.layout.pendingpodetail_row, new String[]{"ItemDescription", "Quantity","Price"},
                                new int[]{ R.id.lblDescription, R.id.lblQuantity,R.id.lblPrice}));

            }
        }.execute(po.get("PurchaseOrderCode"));

        Button btnApprove = (Button) findViewById(R.id.btnApprove);
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View v) {
               EditText edRem=(EditText) findViewById(R.id.editTextRemark);
               // SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userName = user.getEmail();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                PurchaseOrder p=new PurchaseOrder();
                p.put("PurchaseOrderCode",po.get("PurchaseOrderCode"));
                p.put("DateApproved", dateFormat.format(date));

                p.put("Status","Approved");
                p.put("ApprovedBy",userName);
                p.put("Remark",edRem.getText().toString());
                new AsyncTask<PurchaseOrder, Void, Void>() {
                    @Override
                    protected Void doInBackground(PurchaseOrder... params) {
                        PurchaseOrder.updatePurchaseOrder(params[0],user.getEmail(),user.getPassword());
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Intent intent = new Intent(getApplicationContext(), PendingPOActivity.class);
                        startActivity(intent);
                        Toast.makeText(PendingPODetailActivity.this, "Approved",Toast.LENGTH_LONG).show();
                        finish();

                    }
                }.execute(p);
            }
        });

        Button btnReject = (Button) findViewById(R.id.btnReject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View v) {
                EditText edRem=(EditText) findViewById(R.id.editTextRemark);
               // SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userName = user.getEmail();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                PurchaseOrder p=new PurchaseOrder();
                p.put("PurchaseOrderCode",po.get("PurchaseOrderCode"));
                p.put("DateApproved", dateFormat.format(date));
                p.put("Status","Rejected");
                p.put("ApprovedBy",userName);
                p.put("Remark",edRem.getText().toString());
                new AsyncTask<PurchaseOrder, Void, Void>() {
                    @Override
                    protected Void doInBackground(PurchaseOrder... params) {
                        PurchaseOrder.updatePurchaseOrder(params[0],user.getEmail(),user.getPassword());
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Intent intent = new Intent(getApplicationContext(), PendingPOActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(PendingPODetailActivity.this, "Rejected",Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(v.getContext(), PendingPOActivity.class);
                        //v.getContext().startActivity(intent);

                    }
                }.execute(p);
            }
        });

    }
}
