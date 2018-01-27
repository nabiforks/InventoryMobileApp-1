package team8.inventorymobileapp.com.inventorymobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import team8.inventorymobileapp.com.inventorymobileapp.Entity.Adjustment;

public class AdjustmentDetails extends Activity {

 final  static    int[] ids = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,R.id.textView5,R.id.textView6,R.id.editText7};
  final  static   String[] keys = {"AdjustmentCode", "ItemCode", "Price", "AdjustmentQuant"  , "Stock", "Reason", "Remark"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment_details);

        final Intent intent = getIntent();
        String aid = intent.getStringExtra("adjustmentCode");

        new AsyncTask<String, Void, Adjustment>() {
            @Override
            protected Adjustment doInBackground(String... params) {
                return Adjustment.getAdjust(params[0]);
            }

            @Override
            protected void onPostExecute(Adjustment result) {
               show(result);
       /*TextView t1 = (TextView) findViewById( R.id.textView1);
       t1.setText(result.get("AdjustmentCode"));
       TextView t2 = (TextView) findViewById( R.id.textView2);
       t2.setText(result.get("ItemCode"));*/
                /*int []ids = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,R.id.textView5,R.id.textView6,R.id.textView7};
                String []keys = {"AdjustmentCode", "ItemCode", "Price", "AdjustmentQuant","Stock","Reason","Remark"};
                for (int i=0; i<keys.length; i++) {
                    TextView t = (TextView) findViewById(ids[i]);
                    t.setText(result.get(keys[i]));
            }*/
            }

        }.execute(aid);



        Button approveButton = (Button) findViewById(R.id.Approve);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adjustment adjustment = new Adjustment();

                TextView textView = (TextView) findViewById(R.id.textView1);
                String adjustmentCode = textView.getText().toString();
                EditText remarks = (EditText) findViewById(R.id.editText7);

                //String dateap = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);


                String status = "Approve";
                String approvedBy ="yufei@logic.edu.sg";

                adjustment.put("AdjustmentCode",adjustmentCode);
                adjustment.put("Status",status);
                adjustment.put("DateOfApproved",thisDate);
                adjustment.put("ApprovedBy",approvedBy);
                adjustment.put("Remark",remarks.getText().toString());

                new AsyncTask<Adjustment, Void, Void>() {
                    @Override
                    protected Void doInBackground(Adjustment... params) {
                        Adjustment.updateAdjustment(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        setResult(RESULT_OK,intent);
                        Toast.makeText(AdjustmentDetails.this, "Approve",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }.execute(adjustment);
            }
        });

        Button rejectButton = (Button) findViewById(R.id.Reject);

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adjustment adjustment = new Adjustment();

                TextView textView = (TextView) findViewById(R.id.textView1);
                String adjustmentCode = textView.getText().toString();
                EditText remarks = (EditText) findViewById(R.id.editText7);

                //String dateap = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);


                String status = "Reject";
                String approvedBy ="yufei@logic.edu.sg";

                adjustment.put("AdjustmentCode",adjustmentCode);
                adjustment.put("Status",status);
                adjustment.put("DateOfApproved",thisDate);
                adjustment.put("ApprovedBy",approvedBy);
                adjustment.put("Remark",remarks.getText().toString());

                new AsyncTask<Adjustment, Void, Void>() {
                    @Override
                    protected Void doInBackground(Adjustment... params) {
                        Adjustment.updateAdjustment(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        setResult(RESULT_OK,intent);
                        Toast.makeText(AdjustmentDetails.this, "Reject",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }.execute(adjustment);
            }
        });

    }
   void show (Adjustment result){
            //int[] ids = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,R.id.textView5,R.id.textView6,R.id.editText7};
           //String[] keys = {"AdjustmentCode", "ItemCode", "Price", "AdjustmentQuant"  , "Stock", "Reason", "Remark"};
            for (int i = 0; i < keys.length; i++) {
                TextView t = (TextView) findViewById(ids[i]);
                t.setText(result.get(keys[i]));
            }


        }



    }

