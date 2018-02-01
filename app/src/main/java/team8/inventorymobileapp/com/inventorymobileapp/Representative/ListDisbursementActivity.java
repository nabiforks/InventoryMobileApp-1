package team8.inventorymobileapp.com.inventorymobileapp.Representative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.Disbursement;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.LoginActivity;
import team8.inventorymobileapp.com.inventorymobileapp.R;

public class ListDisbursementActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disbursement);
        user = new User(ListDisbursementActivity.this);
        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);


        new AsyncTask<String, Void, List<Disbursement>>() {
            @Override
            protected ArrayList<Disbursement> doInBackground(String... params) {
                return Disbursement.dList(params[0],user.getEmail(),user.getPassword());
            }

            @Override
            protected void onPostExecute(List<Disbursement> result) {
                //customize row layout(can have many rows),but customization can not use with predefine feature
                lv.setAdapter(new SimpleAdapter
                        (ListDisbursementActivity.this, result, R.layout.row,
                                new String[]{"DisbursementCode", "DepartmentName", "Status"},
                                new int[]{R.id.text1, R.id.text3, R.id.text2}));
                if(result.size()==0){
                    Toast t=Toast.makeText(getApplicationContext(),"No disbursement for "+user.getDeptId(),Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        }.execute(user.getDeptId());
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Disbursement b = (Disbursement) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, DisbursementDetailsActivity.class);
        intent.putExtra("DisbursementCode", b.get("DisbursementCode"));
        startActivity(intent);
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
