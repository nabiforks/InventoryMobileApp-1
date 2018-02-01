package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.ProcessingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class RetrievalDetailActivity extends StoreClerkActivity implements View.OnClickListener{
    User user = null;
    final static int[] ids = {R.id.rdEtxDescription, R.id.rdEtxLocation, R.id.rdEtxStock, R.id.rdEtxNeeded, R.id.rdEtxRetrieved, R.id.rdEtxNotes, R.id.rdEtxItemCode};
    final static String[] keys = {"ItemName", "Location", "Stock", "QuantityNeeded", "QuantityRetrieved", "Notes", "ItemCode"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_detail);
        user =     new User(RetrievalDetailActivity.this);
        Intent i = getIntent();
        String itemCode = i.getStringExtra("itemCode");

        new AsyncTask<String, Void, ProcessingRetrievalDetail>() {
            @Override
            protected ProcessingRetrievalDetail doInBackground(String... params) {
                //testEdit.setText("testing");
                return ProcessingRetrievalDetail.getRetrievalDetail(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(ProcessingRetrievalDetail result) {
                show(result);
            }
        }.execute(itemCode);

        Button b = (Button) findViewById(R.id.rdBtnUpdate);
        b.setOnClickListener(this);
    }
    void show(ProcessingRetrievalDetail rd){
        for (int i = 0; i<keys.length; i++){
            EditText e = (EditText) findViewById(ids[i]);
            if (rd.get(keys[i]).toLowerCase()=="null"){
                e.setText("");
            } else{
                e.setText(rd.get(keys[i]));
            }
        }
    }

    @Override
    public void onClick(View v) {
        ProcessingRetrievalDetail rd = new ProcessingRetrievalDetail();
        for (int i=0; i<ids.length; i++) {
            EditText t = (EditText) findViewById(ids[i]);
            rd.put(keys[i], t.getText().toString());
        }
        int stock = Integer.parseInt(rd.get("Stock"));
        int needed = Integer.parseInt(rd.get("QuantityNeeded"));
        int retrieved = Integer.parseInt(rd.get("QuantityRetrieved"));
        int compare = stock>needed?needed:stock;

        if (retrieved>compare){
            Toast.makeText(getApplicationContext(), "Cannot retrieve more than "+compare,Toast.LENGTH_LONG).show();
        } else if (retrieved<0){
            Toast.makeText(getApplicationContext(), "Cannot retrieve less than 0.",Toast.LENGTH_LONG).show();
        } else{
            new AsyncTask<ProcessingRetrievalDetail, Void, Void>() {
                @Override
                protected Void doInBackground(ProcessingRetrievalDetail... params) {
                    ProcessingRetrievalDetail.updateRetrievalDetail(params[0],user.getEmail(),user.getPassword());
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {

                    Intent intent = new Intent(getApplicationContext(), RetrievalListActivity.class);
                    EditText etx = findViewById(R.id.rdEtxDescription);
                    Toast.makeText(getApplicationContext(), etx.getText()+" has been updated",Toast.LENGTH_LONG).show();

                    startActivity(intent);


                    finish();
                }
            }.execute(rd);
        }
    }
}
