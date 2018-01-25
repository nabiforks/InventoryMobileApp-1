package team8.inventorymobileapp.com.inventorymobileapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.Disbursement;

public class ListDisbursementActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disbursement);

        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        new AsyncTask<Void, Void, List<Disbursement>>() {
            @Override
            protected ArrayList<Disbursement> doInBackground(Void... params) {
                return Disbursement.dList();
            }

            @Override
            protected void onPostExecute(List<Disbursement> result) {
                //customize row layout(can have many rows),but customization can not use with predefine feature
                lv.setAdapter(new SimpleAdapter
                        (ListDisbursementActivity.this, result, R.layout.row,
                                new String[]{"DisbursementCode", "DepartmentName", "Status"},
                                new int[]{R.id.text1, R.id.text3, R.id.text2}));

            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Disbursement b = (Disbursement) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, DisbursementDetailsActivity.class);
        intent.putExtra("DisbursementCode", b.get("DisbursementCode"));
        startActivity(intent);
    }
}

