package team8.inventorymobileapp.com.inventorymobileapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.AllocationListActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.DisbursementGenerationActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.DisbursementSelectionActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.RequestListActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.RetrievalListActivity;

public class StoreClerkActivity extends AppCompatActivity {
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_cleark);
        user=new User(StoreClerkActivity.this);
        ImageButton ibRequestList = findViewById(R.id.wcSClerkRequestList);
        ibRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibRetrieval = findViewById(R.id.wcSClerkRetrieval);
        ibRetrieval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RetrievalListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibAllocation = findViewById(R.id.wcSClerkAllocation);
        ibAllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllocationListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibDisbursementDate = findViewById(R.id.wcSClerkDisbursementDate);
        ibDisbursementDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisbursementGenerationActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibDisbursementForm = findViewById(R.id.wcSClerkDisbursementForm);
        ibDisbursementForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisbursementSelectionActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        // moveTaskToBack(true);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clerkmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option0:
                startActivity(new Intent(this, StoreClerkActivity.class));
                return(true);
            case R.id.option1:
                startActivity(new Intent(this,RequestListActivity .class));
                return(true);
            case R.id.option2:
                startActivity(new Intent(this, RetrievalListActivity.class));
                return(true);
            case R.id.option3:
                startActivity(new Intent(this, AllocationListActivity.class));
                return (true);
            case R.id.option4:
                startActivity(new Intent(this, DisbursementGenerationActivity.class));
                return (true);
            case R.id.option5:
                startActivity(new Intent(this, DisbursementSelectionActivity.class));
                return (true);
            case R.id.option6: {
                user.removeUser();
                startActivity(new Intent(this, LoginActivity.class));
                return (true);
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
