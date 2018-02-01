package team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Adapter.AdjustmentAdapter;
import team8.inventorymobileapp.com.inventorymobileapp.Adapter.PendingPOAdapter;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Adjustment;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.SupervisorActivity;

public class AdjustmentActivity extends SupervisorActivity implements AdapterView.OnItemClickListener{

    int requestCode =1;
    AdjustmentAdapter listAdapter;
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment);

        final ListView lv =  (ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(this);
        user =     new User(AdjustmentActivity.this);
        new AsyncTask<Void,Void,List<Adjustment>>()
        {
            @Override
            protected List<Adjustment> doInBackground(Void...Params) {
                return Adjustment.listOfAdjustmentBySupervisor(user.getEmail(),user.getPassword());
            }

            @Override
            protected void onPostExecute(List<Adjustment> adjustments) {
                super.onPostExecute(adjustments);

                listAdapter = new AdjustmentAdapter(AdjustmentActivity.this, R.layout.adjustment_list_row, adjustments);


                lv.setAdapter(listAdapter);
                if(adjustments.size()==0){
                    Toast t=Toast.makeText(getApplicationContext(),"No pending adjustment.",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }.execute();




    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        Adjustment s = (Adjustment) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, AdjustmentDetailsActivity.class);
        intent.putExtra("adjustmentCode", s.get("AdjustmentCode"));
        //Toast.makeText(this,s.get("AdjustmentCode") ,Toast.LENGTH_LONG).show();
        startActivity(intent);
        //startActivityForResult(intent,requestCode);
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(this.requestCode==requestCode)
        {
            if(resultCode==RESULT_OK)
            {

                final ListView lv =  (ListView) findViewById(R.id.listview);
                lv.setOnItemClickListener(this);

                new AsyncTask<Void,Void,List<Adjustment>>()
                {
                    @Override
                    protected List<Adjustment> doInBackground(Void...Params) {
                        return Adjustment.listOfAdjustmentBySupervisor();
                    }

                    @Override
                    protected void onPostExecute(List<Adjustment> adjustments) {
                        super.onPostExecute(adjustments);

                        BaseAdapter b = new SimpleAdapter(AdjustmentActivity.this, adjustments, R.layout.supervisorrow, new String[]{"AdjustmentCode", "ItemCode","Reason"},
                                new int[]{ R.id.text1, R.id.text2,R.id.text3});
                        lv.setAdapter(b);
                    }
                }.execute();


            }


        }


    }*/
}