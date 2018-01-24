package team8.inventorymobileapp.com.inventorymobileapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListDisbursementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disbursement);
        final ListView lv = (ListView) findViewById(R.id.listView);


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
                                new int[]{R.id.text1, R.id.text2, R.id.text3}));

            }
        }.execute();
    }
}
