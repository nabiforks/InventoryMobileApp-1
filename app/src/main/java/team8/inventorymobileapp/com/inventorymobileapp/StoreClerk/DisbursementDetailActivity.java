package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingDisbursementDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class DisbursementDetailActivity extends StoreClerkActivity {
    User user = null;
    final static int[] ids = {R.id.ddEtxItemName, R.id.ddEtxRequestCode, R.id.ddEtxQuantityNeeded, R.id.ddEtxQuantityRetrieved, R.id.ddEtxQuantityActual, R.id.ddEtxNotes, R.id.ddInviDisbursementCode, R.id.ddInviItemCode};
    final static String[] keys = {"ItemName", "RequestCode", "QuantityNeeded", "QuantityRetrieved", "QuantityActual", "Notes", "DisbursementCode", "ItemCode"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);
        user =     new User(DisbursementDetailActivity.this);
        Intent intent = getIntent();
        String departmentCode = intent.getStringExtra("DepartmentCode");
        String requestCode = intent.getStringExtra("RequestCode");
        String itemCode = intent.getStringExtra("ItemCode");
        ArrayList<String> threeCode = new ArrayList<>();
        threeCode.add(departmentCode);
        threeCode.add(requestCode);
        threeCode.add(itemCode);

        Button button = findViewById(R.id.ddBtnUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllocatingDisbursementDetail dd = new AllocatingDisbursementDetail();
                for (int i=0; i<ids.length; i++) {
                    EditText t = (EditText) findViewById(ids[i]);
                    dd.put(keys[i], t.getText().toString());
                }
//Validation

                int actual = Integer.parseInt(dd.get("QuantityActual"));
                int retrieved = Integer.parseInt(dd.get("QuantityRetrieved"));

                if (actual>retrieved){
                    Toast.makeText(getApplicationContext(), "The number disbursed cannot be more than " + retrieved,Toast.LENGTH_LONG).show();
                } else{
                    new AsyncTask<AllocatingDisbursementDetail, Void, Void>() {
                        @Override
                        protected Void doInBackground(AllocatingDisbursementDetail... params) {
                            AllocatingDisbursementDetail.updateDisbursementDetail(params[0],user.getEmail(),user.getPassword());
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            Intent intent = new Intent(getApplicationContext(), DisbursementFormActivity.class);
                            EditText etx = findViewById(R.id.ddEtxItemName);
                            Toast.makeText(getApplicationContext(), etx.getText()+" has been updated",Toast.LENGTH_LONG).show();

                            startActivity(intent);


                            finish();
                        }
                    }.execute(dd);
                }
            }
        });

        new AsyncTask<ArrayList<String>, Void, AllocatingDisbursementDetail>() {
            @Override
            protected AllocatingDisbursementDetail doInBackground(ArrayList<String>[] arrayLists) {
                return AllocatingDisbursementDetail.getDisbursementDetail(arrayLists[0],user.getEmail(),user.getPassword());

            }

            @Override
            protected void onPostExecute(AllocatingDisbursementDetail result) {
                show(result);
            }
        }.execute(threeCode);

    }
    void show(AllocatingDisbursementDetail rd){
        for (int i = 0; i<keys.length; i++){
            EditText e = (EditText) findViewById(ids[i]);
            if (rd.get(keys[i]).toLowerCase()=="null"){
                e.setText("");
            } else{
                e.setText(rd.get(keys[i]));
            }
        }
    }
}
