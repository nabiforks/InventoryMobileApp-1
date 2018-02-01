package team8.inventorymobileapp.com.inventorymobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.AdjustmentActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.PendingPOActivity;

public class SupervisorActivity extends AppCompatActivity {

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);
        user=new User(SupervisorActivity.this);
        ImageButton ibAdjustment = findViewById(R.id.wcSSupAdjustment);
        ibAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdjustmentActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibPendingPO = findViewById(R.id.wcSSupPendingPO);
        ibPendingPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PendingPOActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.supervisormenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option0:
                startActivity(new Intent(this, SupervisorActivity.class));
                return(true);
            case R.id.option1:
                startActivity(new Intent(this, AdjustmentActivity.class));
                return(true);
            case R.id.option2:
                startActivity(new Intent(this, PendingPOActivity.class));
                return(true);
            case R.id.option3: {
                user.removeUser();
                startActivity(new Intent(this, LoginActivity.class));

                return (true);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
       // moveTaskToBack(true);
        finish();

    }
}
