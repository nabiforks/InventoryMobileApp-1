package team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHeadActivity;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Request;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.RequestDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;

public class RequestDetailsActivity extends DepartmentHeadActivity {

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        ScrollView sv = (ScrollView) findViewById(R.id.container_scroll_view);
        sv.smoothScrollTo(0, 0);
        user = new User(RequestDetailsActivity.this);

        Intent i = getIntent();
        final String rCode = i.getStringExtra("rCode");
        final String notes=i.getStringExtra("notes");
        final TextView txt1=(TextView)findViewById(R.id.txtRCode);
        final TextView txt2=(TextView)findViewById(R.id.txtNotes);
        txt1.setText(rCode);
        if(notes.equals("null")){
            txt2.setVisibility(View.INVISIBLE);
        }else{
            txt2.setText(notes);
        }

        final DetailListView lv=(DetailListView)findViewById(R.id.listView1);



        new AsyncTask<String, Void, List<RequestDetail>>() {
            @Override
            protected List<RequestDetail> doInBackground(String... params) {
                return RequestDetail.list(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<RequestDetail> result) {

                lv.setAdapter(new SimpleAdapter
                        (RequestDetailsActivity.this, result, R.layout.row, new String[]{"CategoryCode","Quantity","Description"},
                                new int[]{ R.id.text1, R.id.text2,R.id.text3}));
            }
        }.execute(rCode);

        final Button approve = (Button) findViewById(R.id.btnApprove);
        final Button reject=(Button)findViewById(R.id.btnReject);
        final TextView txt=(TextView)findViewById(R.id.textView1);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestDetailsActivity.this);
                builder.setTitle("Approve");
                builder.setMessage("Are you sure to approve the request?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Request r=new Request();
                        r.put("RequestCode",rCode);
                        r.put("Status","processing");
                       // SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String userName = user.getEmail();
                        r.put("ApprovedBy",userName);

                        final EditText edt=(EditText)findViewById(R.id.Remark);
                        String remark=edt.getText().toString();
                        if(remark.length()!=0){
                            r.put("HeadRemark",remark);
                        }


                        new AsyncTask<Request, Void, Void>() {
                            @Override
                            protected Void doInBackground(Request... params) {
                                Request.updateRequest(params[0],user.getEmail(),user.getPassword());
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void result) {
                                Intent i=new Intent(RequestDetailsActivity.this,ListRequestActivity.class);
                                startActivity(i);

                               // Toast t=Toast.makeText(getApplicationContext(),"You have approved the request successfully!",Toast.LENGTH_SHORT);
                               // t.show();


                            }
                        }.execute(r);


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RequestDetailsActivity.this);
                builder.setTitle("Reject");
                builder.setMessage("Are you sure to REJECT the request?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Request r=new Request();
                        r.put("RequestCode",rCode);
                        r.put("Status","rejected");
                        //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String userName = user.getEmail();
                        r.put("ApprovedBy",userName);
                        EditText edt=(EditText)findViewById(R.id.Remark);
                        String remark=edt.getText().toString();
                        if(remark.length()!=0){
                            r.put("HeadRemark",remark);
                        }


                        new AsyncTask<Request, Void, Void>() {
                            @Override
                            protected Void doInBackground(Request... params) {
                                Request.updateRequest(params[0],user.getEmail(),user.getPassword());
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void result) {
                                Intent i=new Intent(RequestDetailsActivity.this,ListRequestActivity.class);
                                startActivity(i);

                                Toast t=Toast.makeText(getApplicationContext(),"You have rejected the request successfully!",Toast.LENGTH_SHORT);
                                t.show();


                            }
                        }.execute(r);


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();





            }
        });


    }
}