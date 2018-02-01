package team8.inventorymobileapp.com.inventorymobileapp.Representative;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.Disbursement;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.DisbursementDetails;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.LoginActivity;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.DisbursementDetailActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.PendingPODetailActivity;

public class DisbursementDetailsActivity extends AppCompatActivity {

    User user = null;
    int[] ids = {R.id.tvCollectionPoint, R.id.tvDepartmentName , R.id.tvReprsentativeName, R.id.tvDisbursementNumber, R.id.tvstatus};
    String[] keys = {"CollectionPointName", "DepartmentName", "RepName", "DisbursementCode", "Status"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_details);
        user =  new User(DisbursementDetailsActivity.this);
        final ListView lv = (ListView) findViewById(R.id.listview1);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.headerlayout, lv, false);
        lv.addHeaderView(headerView);
        //get Disbursement Code from the previous page
        Intent i = getIntent();
        final String id = i.getStringExtra("DisbursementCode");

        //check the Disbursement Code before display
        if (id != null && !id.isEmpty()) {
            new AsyncTask<String, Void, ArrayList<DisbursementDetails>>() {
                @Override
                protected ArrayList<DisbursementDetails> doInBackground(String... params) {
                    return DisbursementDetails.dDetails(params[0],user.getEmail(),user.getPassword());
                }

                @Override
                protected void onPostExecute(ArrayList<DisbursementDetails> result) {
                    //display the disbursement details info
                    for (int i = 0; i < keys.length; i++) {
                        TextView e = (TextView) findViewById(ids[i]);
                        e.setText(e.getText() + " : " + result.get(0).get(keys[i]));
                    }
                    // display the list of items disbursement
                    lv.setAdapter(new SimpleAdapter(DisbursementDetailsActivity.this, result, R.layout.rowlayout,
                            new String[]{"ItemDescription","RequestCode", "NeededQuantity", "ActualQuantity"},
                            new int[]{R.id.txStationeryDescription, R.id.txRequestCode, R.id.txNeededQuantity, R.id.txActualQuantity}));
                }
            }.execute(id);
        }

        Button btnAcknowledge = findViewById(R.id.button);
        btnAcknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disbursement disbursement = new Disbursement();
                disbursement.put("Status","completed");
                disbursement.put("DisbursementCode",id);
                //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userName = user.getEmail();

                disbursement.put("RepName",userName);
                new AsyncTask<Disbursement, Void, Void>() {
                    @Override
                    protected  Void doInBackground(Disbursement... params)
                    {
                        Disbursement.UpdateDisbursement(params[0],user.getEmail(),user.getPassword());
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result)
                    {
                        super.onPostExecute(result);
                        Intent intent = new Intent(getApplicationContext(), ListDisbursementActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(DisbursementDetailsActivity.this, "Acknowledged",Toast.LENGTH_LONG).show();
                    }
                }.execute(disbursement);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.disbursementmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.option1:
                startActivity(new Intent(this, ListDisbursementActivity.class));
                return(true);

            case R.id.option2: {
                user.removeUser();
                startActivity(new Intent(this, LoginActivity.class));

                return (true);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}