package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.ProcessingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class RetrievalListActivity extends StoreClerkActivity implements AdapterView.OnItemClickListener{
    User user = null;
    List<ProcessingRetrievalDetail> rdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_list);
        user =     new User(RetrievalListActivity.this);
        final TextView tvwNotification = findViewById(R.id.rfaNotification);
        tvwNotification.setText("");

        final ListView lvRetrieval = (ListView) findViewById(R.id.lvRetrieval);
        lvRetrieval.setOnItemClickListener(this);

        final Button btnConfirmRetrieval = (Button) findViewById(R.id.btnConfirmRetrieval);

        new AsyncTask<Void, Void, List<ProcessingRetrievalDetail>>() {
            @Override
            protected List<ProcessingRetrievalDetail> doInBackground(Void... params) {
                return ProcessingRetrievalDetail.getListRetrievalDetai(user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<ProcessingRetrievalDetail> result) {
                super.onPostExecute(result);
                if (result.size()==0){
                    tvwNotification.setText("There is no active retrieval at the moment.");
                    btnConfirmRetrieval.setText("Proceed to Allocation>");
                    TextView tvRetrieved = findViewById(R.id.rfaTvRetrieved);
                    tvRetrieved.setText("");
                    TextView tvNeeded = findViewById(R.id.rfaTvNeeded);
                    tvNeeded.setText("");

                } else{
                    BaseAdapter ba = new SimpleAdapter(RetrievalListActivity.this, result, R.layout.row_retrieval_list, new String[]{"ItemName", "QuantityRetrieved", "QuantityNeeded","Stock","ItemCode"}, new int[]{R.id.rfaitemName, R.id.rfaretrievedQuant, R.id.rfaneededQuant, R.id.rfastock,R.id.rfaitemCode});
                    lvRetrieval.setAdapter(ba);
                }
            }
        }.execute();

        btnConfirmRetrieval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirmRetrieval.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), AllocationListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> av, View view, int i, long l) {
        ProcessingRetrievalDetail rd = (ProcessingRetrievalDetail) av.getAdapter().getItem(i);
        //Toast.makeText(getApplicationContext(), rd.get("ItemCode") + " selected",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RetrievalDetailActivity.class);
        intent.putExtra("itemCode", rd.get("ItemCode"));
        startActivity(intent);
    }
}
