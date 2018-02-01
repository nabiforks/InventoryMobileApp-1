package team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHeadActivity;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Request;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;

public class ListRequestActivity extends DepartmentHeadActivity implements AdapterView.OnItemClickListener{

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_request);
        user = new User(ListRequestActivity.this);
        final ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);
/*        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String depCode = pref.getString("DepartmentCode", "");*/

        new AsyncTask<String, Void, List<Request>>() {
            @Override
            protected List<Request> doInBackground(String... params) {

                return Request.list(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<Request> result) {
                lv.setAdapter(new SimpleAdapter
                        (ListRequestActivity.this, result, R.layout.request_row, new String[]{"DateCreated","Status","EmpName"},
                                new int[]{ R.id.text1, R.id.text2,R.id.text3}));
                if(result.size()==0){
                    Toast t=Toast.makeText(getApplicationContext(),"No pending request currently.",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }.execute(user.getDeptId());
    }

    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Request r = (Request) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, RequestDetailsActivity.class);
        intent.putExtra("rCode", r.get("RequestCode"));
        intent.putExtra("notes",r.get("Notes"));
        startActivity(intent);
    }


}