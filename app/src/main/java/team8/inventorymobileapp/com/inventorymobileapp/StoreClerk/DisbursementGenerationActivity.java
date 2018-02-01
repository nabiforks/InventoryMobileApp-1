package team8.inventorymobileapp.com.inventorymobileapp.StoreClerk;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AllocatingRetrievalDetail;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.CollectionDate;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerkActivity;

public class DisbursementGenerationActivity extends StoreClerkActivity {
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_generation);
        user =     new User(DisbursementGenerationActivity.this);
        final EditText editText = findViewById(R.id.dgEtxCollectionDate);
        final TextView textView = findViewById(R.id.dgTvwNotification);
        Button button = findViewById(R.id.dgBtnNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisbursementSelectionActivity.class);
                startActivity(intent);
            }
        });

        new AsyncTask<Void, Void, CollectionDate>() {
            @Override
            protected CollectionDate doInBackground(Void... params) {
                //testEdit.setText("testing");
                return CollectionDate.getCollectionDate(user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(CollectionDate result) {
                if (result.get("Status").toLowerCase().equals("confirmed")){
                    editText.setText(result.get("DatePlanToCollect"));
                    textView.setText("Date to collect HAS BEEN CONFIRMED. \nPlease proceed to the next step");
                } else if (result.get("Status").toLowerCase().equals("not confirmed")){
                    editText.setText(result.get("DatePlanToCollect"));
                    textView.setText("Date to collect HAS NOT BEEN CONFIRMED yet. \nPlease use the web application for confirming the date.");
                } else{
                    editText.setText(result.get("DatePlanToCollect"));
                    textView.setText("There is NO DISBURSEMENT in processing at the moment.");
                }
            }
        }.execute();
    }
}
