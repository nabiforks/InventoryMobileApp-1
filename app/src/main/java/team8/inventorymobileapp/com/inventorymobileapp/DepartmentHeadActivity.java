package team8.inventorymobileapp.com.inventorymobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead.AddAuthoriseActivity;
import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead.AuthoriseStaffActitvity;
import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead.ListRequestActivity;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.StoreClerk.RequestListActivity;

public class DepartmentHeadActivity extends AppCompatActivity {

    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_head);

        user=  new User(DepartmentHeadActivity.this);
        ImageButton ibRequestList = findViewById(R.id.wcDeptHeadRequest);
        ibRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListRequestActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibAssignRoleList = findViewById(R.id.wcDeptHeadAssignRoleList);
        ibAssignRoleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthoriseStaffActitvity.class);
                startActivity(intent);
            }
        });

        ImageButton ibAssignRole = findViewById(R.id.wcDeptHeadAssignRole);
        ibAssignRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAuthoriseActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.departmentmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                startActivity(new Intent(this, ListRequestActivity.class));
                return(true);
            case R.id.option2:
                startActivity(new Intent(this, AuthoriseStaffActitvity.class));
                return(true);
            case R.id.option3: {
                startActivity(new Intent(this, AddAuthoriseActivity.class));
                return (true);
            }
            case R.id.option4: {
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
