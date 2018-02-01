package team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Adapter.PendingPOAdapter;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.PurchaseOrder;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.SupervisorActivity;

public class PendingPOActivity extends SupervisorActivity {

    User user = null;
    PendingPOAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_po);
        user = new User(PendingPOActivity.this);
        // get the listview
        final ListView listView = (ListView) findViewById(R.id.listView);


        // preparing list data
        new AsyncTask<Void, Void, List<PurchaseOrder>>() {
            @Override
            protected List<PurchaseOrder> doInBackground(Void... params) {
                return PurchaseOrder.list(user.getEmail(),user.getPassword());
            }

            @Override
            protected void onPostExecute(final List<PurchaseOrder> result) {
                listAdapter = new PendingPOAdapter(PendingPOActivity.this, R.layout.pendingpo_row, result);


                listView.setAdapter(listAdapter);
                if(result.size()==0){
                    Toast t=Toast.makeText(getApplicationContext(),"No pending PO found.",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }.execute();


    }

}