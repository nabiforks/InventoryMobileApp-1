package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.ProcessingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class AllocationListActivity extends StoreClerkActivity implements AdapterView.OnItemClickListener{

    List<ProcessingRetrievalDetail> rdList = new ArrayList<>();
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation_list);
        user =     new User(AllocationListActivity.this);
        final TextView tvwInfo = findViewById(R.id.alTvInfo);
        tvwInfo.setText("");

        final ListView lvRetrieval = (ListView) findViewById(R.id.lvRetrieval);
        lvRetrieval.setOnItemClickListener(this);


        new AsyncTask<Void, Void, List<AllocatingRetrievalDetail>>() {
            @Override
            protected List<AllocatingRetrievalDetail> doInBackground(Void... params) {
                return AllocatingRetrievalDetail.getListRetrievalDetai(user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<AllocatingRetrievalDetail> result) {
                super.onPostExecute(result);
                if (result.size()==0){
                    tvwInfo.setText("There is no allocation at the moment.");
                    TextView tvRetrieved = findViewById(R.id.rfaTvRetrieved);
                    tvRetrieved.setText("");
                    TextView tvNeeded = findViewById(R.id.rfaTvNeeded);
                    tvNeeded.setText("");

                } else{
                    BaseAdapter ba = new SimpleAdapter(AllocationListActivity.this, result, R.layout.row_retrieval_list, new String[]{"ItemName", "QuantityRetrieved", "QuantityNeeded","Stock","ItemCode"}, new int[]{R.id.rfaitemName, R.id.rfaretrievedQuant, R.id.rfaneededQuant, R.id.rfastock,R.id.rfaitemCode});
                    lvRetrieval.setAdapter(ba);
                }
            }
        }.execute();

        Button btnToDisbursementGeneration = (Button) findViewById(R.id.btnToDisbursementGeneration);
        btnToDisbursementGeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisbursementGenerationActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> av, View view, int i, long l) {
        AllocatingRetrievalDetail ard = (AllocatingRetrievalDetail) av.getAdapter().getItem(i);
        Toast.makeText(getApplicationContext(), ard.get("ItemCode") + " selected",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AllocationDetailActivity.class);
        intent.putExtra("itemCode", ard.get("ItemCode"));
        startActivity(intent);
    }
}
