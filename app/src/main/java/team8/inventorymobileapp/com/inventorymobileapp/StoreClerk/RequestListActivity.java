package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.OutstandingRequest;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class RequestListActivity extends StoreClerkActivity implements AdapterView.OnItemClickListener{
    User user = null;
    List<OutstandingRequest> rList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        user =     new User(RequestListActivity.this);

        final ImageView imNotFound = findViewById(R.id.rfaIvNotFound);

        final ListView lvRequest = (ListView) findViewById(R.id.lvRequest);
        final TextView tvwNotification = findViewById(R.id.rfaNotification);
        tvwNotification.setText("");
        lvRequest.setOnItemClickListener(this);


        new AsyncTask<Void, Void, List<OutstandingRequest>>() {
            @Override
            protected List<OutstandingRequest> doInBackground(Void... params) {
                return OutstandingRequest.getListOutstandingRequests(user.getEmail(), user.getPassword());
            }
            @Override
            protected void onPostExecute(List<OutstandingRequest> result) {
                super.onPostExecute(result);
                if (result.size()==0){
                    tvwNotification.setText("There is no outstanding requests at the moment");
                    imNotFound.setVisibility(View.VISIBLE);
                } else{
                    BaseAdapter ba = new SimpleAdapter(RequestListActivity.this, result, R.layout.row_request_list, new String[]{"RequestCode", "DepartmentName", "Status"}, new int[]{R.id.rlRowrequestCode, R.id.rlRowdepartmentName, R.id.rlRowstatus});
                    lvRequest.setAdapter(ba);
                }
            }
        }.execute();

        Button btnProceedToRetrievalForm = (Button) findViewById(R.id.btnGenerateRetrievalForm);
        btnProceedToRetrievalForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RetrievalListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> av, View view, int i, long l) {
        OutstandingRequest r = (OutstandingRequest) av.getAdapter().getItem(i);
        Toast.makeText(getApplicationContext(), r.get("RequestCode") + "selected",Toast.LENGTH_LONG).show();
    }
}
