package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.BusinessLogic.StoreClerkController;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.ConfirmedDisbursement;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class DisbursementSelectionActivity extends StoreClerkActivity {
    User user = null;
    StoreClerkController sClerkCtrl = new StoreClerkController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_selection);
        user =     new User(DisbursementSelectionActivity.this);
        final TextView tvSelectCollectionPoint = findViewById(R.id.dsTvSelectCollectionPoint);
        final TextView tvSelectDepartment = findViewById(R.id.dsTvSelectDepartment);
        tvSelectDepartment.setVisibility(View.INVISIBLE);
        final ListView lvCollectionPoint = findViewById(R.id.dsLvCollectionPoint);
        final ListView lvDepartment = findViewById(R.id.dsLvDepartment);

        lvDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConfirmedDisbursement ard = (ConfirmedDisbursement) parent.getAdapter().getItem(position);
                final String cp = ard.get("DepartmentCode");
                Toast.makeText(getApplicationContext(), cp + " selected",Toast.LENGTH_LONG).show();
                tvSelectDepartment.setVisibility(View.VISIBLE);

                Intent intent = new Intent(getApplicationContext(), DisbursementFormActivity.class);
                intent.putExtra("DepartmentCode", ard.get("DepartmentCode"));
                intent.putExtra("DisbursementCode", ard.get("DisbursementCode"));
                startActivity(intent);
            }
        });

        lvCollectionPoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> ard = (HashMap<String,String>) parent.getAdapter().getItem(position);
                final String cp = ard.get("CollectionPoint");
                Toast.makeText(getApplicationContext(), cp + " selected",Toast.LENGTH_LONG).show();
                tvSelectDepartment.setVisibility(View.VISIBLE);

                new AsyncTask<Void, Void, List<ConfirmedDisbursement>>() {
                    @Override
                    protected List<ConfirmedDisbursement> doInBackground(Void... params) {
                        return ConfirmedDisbursement.getListConfirmedDisbursement(user.getEmail(),user.getPassword());
                    }
                    @Override
                    protected void onPostExecute(List<ConfirmedDisbursement> result) {
                        super.onPostExecute(result);
                        List<ConfirmedDisbursement> cdList = sClerkCtrl.getConfirmedDisbursementByCollectionPoint(result,cp);
                        BaseAdapter ba = new SimpleAdapter(DisbursementSelectionActivity.this, cdList, R.layout.row_confirmed_disbursement, new String[]{"DepartmentName","CollectionTime","Representative"}, new int[]{R.id.dsRowDepartmentName, R.id.dsRowCollectionTime, R.id.dsRowRepresentative});
                        lvDepartment.setAdapter(ba);
                    }
                }.execute();

            }
        });
        new AsyncTask<Void, Void, List<ConfirmedDisbursement>>() {
            @Override
            protected List<ConfirmedDisbursement> doInBackground(Void... params) {
                return ConfirmedDisbursement.getListConfirmedDisbursement(user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<ConfirmedDisbursement> result) {
                super.onPostExecute(result);
                if (result.size()==0){
                    tvSelectCollectionPoint.setText("There is no Disbursement Collection");
                } else{
                    List<HashMap<String, String>> cpList = sClerkCtrl.getCollectionPointList(result);
                    BaseAdapter ba = new SimpleAdapter(DisbursementSelectionActivity.this, cpList, R.layout.row_collection_point, new String[]{"CollectionPoint"}, new int[]{R.id.dsRowCollectionPoint});
                    lvCollectionPoint.setAdapter(ba);
                }
            }
        }.execute();





    }
}
