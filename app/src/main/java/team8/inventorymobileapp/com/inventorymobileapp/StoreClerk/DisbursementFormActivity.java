package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingDisbursementDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.ConfirmedDisbursement;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class DisbursementFormActivity extends StoreClerkActivity {
    User user = null;
    final static int[] ids = {R.id.dfEtxCollectionPoint, R.id.dfEtxDepartmentName, R.id.dfEtxReprsentative, R.id.dfEtxDisbursementCode, R.id.dfEtxStatus};
    final static String[] keys = {"CollectionPoint", "DepartmentName", "Representative", "DisbursementCode", "Status"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_form);
        user =     new User(DisbursementFormActivity.this);
        Intent intent = getIntent();
        String departmentCode = intent.getStringExtra("DepartmentCode");
        final String disbursementCode = intent.getStringExtra("DisbursementCode");

        Button btnMarkAsNotCollected = findViewById(R.id.dfBtnMarkAsNotCollected);
        btnMarkAsNotCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        AllocatingDisbursementDetail.markAsNotCollected(params[0],user.getEmail(),user.getPassword());
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Intent intent = new Intent(getApplicationContext(), DisbursementSelectionActivity.class);
                        EditText etx = findViewById(R.id.ddEtxItemName);
                        Toast.makeText(getApplicationContext(), disbursementCode+" has been marked as not collected",Toast.LENGTH_LONG).show();

                        startActivity(intent);


                        finish();
                    }
                }.execute(disbursementCode);
            }
        });

        final ListView listView = findViewById(R.id.dfLvDisbursementDetail);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_disbursement_detail, listView, false);
        listView.addHeaderView(header, null, false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AllocatingDisbursementDetail ard = (AllocatingDisbursementDetail) parent.getAdapter().getItem(position);
                final String cp = ard.get("RequestCode");
                Toast.makeText(getApplicationContext(), cp + " selected",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), DisbursementDetailActivity.class);
                intent.putExtra("RequestCode", ard.get("RequestCode"));
                intent.putExtra("ItemCode", ard.get("ItemCode"));
                intent.putExtra("DepartmentCode", ard.get("DepartmentCode"));
                startActivity(intent);
            }
        });

        new AsyncTask<String, Void, ConfirmedDisbursement>() {
            @Override
            protected ConfirmedDisbursement doInBackground(String... params) {
                //testEdit.setText("testing");
                return ConfirmedDisbursement.getDisbursement(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(ConfirmedDisbursement result) {
                show(result);
            }
        }.execute(departmentCode);

        new AsyncTask<String, Void, List<AllocatingDisbursementDetail>>() {
            @Override
            protected List<AllocatingDisbursementDetail> doInBackground(String... strings) {
                return AllocatingDisbursementDetail.getDisbursementDetails(strings[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<AllocatingDisbursementDetail> result) {
                super.onPostExecute(result);
                BaseAdapter ba = new SimpleAdapter(DisbursementFormActivity.this, result, R.layout.row_disbursement_detail, new String[]{"ItemName", "RequestCode", "QuantityNeeded", "QuantityActual"}, new int[]{R.id.ddTxItemName,R.id.ddTvRequestCode, R.id.ddTvNeededQuantity, R.id.ddTvDisbursedQuantity});
                listView.setAdapter(ba);
            }
        }.execute(departmentCode);

    }

    void show(ConfirmedDisbursement cd){
        for (int i = 0; i<keys.length; i++){
            EditText e = (EditText) findViewById(ids[i]);
            if (cd.get(keys[i]).toLowerCase()=="null"){
                e.setText("");
            } else{
                e.setText(cd.get(keys[i]));
            }
        }
    }



}
