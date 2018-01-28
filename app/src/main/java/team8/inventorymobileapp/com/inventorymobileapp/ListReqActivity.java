package team8.inventorymobileapp.com.inventorymobileapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entity.Request;

public class ListReqActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_req);
        final ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);

        new AsyncTask<Void, Void, List<Request>>() {
            @Override
            protected List<Request> doInBackground(Void... params) {
                return Request.list();
            }
            @Override
            protected void onPostExecute(List<Request> result) {
                lv.setAdapter(new SimpleAdapter
                        (ListReqActivity.this, result, R.layout.row, new String[]{"DateCreated","Status","EmpName"},
                                new int[]{ R.id.text1, R.id.text2,R.id.text3}));
                if(result.size()==0){
                    Toast t=Toast.makeText(getApplicationContext(),"No pending request currently.",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }.execute();
    }

    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Request r = (Request) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, RequestDetailActivity.class);
        intent.putExtra("rCode", r.get("RequestCode"));
        intent.putExtra("notes",r.get("Notes"));
        startActivity(intent);
    }


}
