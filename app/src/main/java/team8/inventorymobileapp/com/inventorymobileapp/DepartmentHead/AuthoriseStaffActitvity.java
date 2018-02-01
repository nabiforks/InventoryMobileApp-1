package team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Adapter.AssignRoleAdapter;
import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHeadActivity;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.AssignRole;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;

public class AuthoriseStaffActitvity extends DepartmentHeadActivity {
    User user = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        //List<AssignRole> asss = AssignRole.ListAssignRoleInDepartment("CPSC");
        //setListAdapter(new AssignRoleAdapter(this, R.layout.assignrole, asss));

        setContentView(R.layout.activity_authorise_staff_actitvity);
        user =     new User(AuthoriseStaffActitvity.this);
        final ListView listView=(ListView) findViewById(R.id.list_view);

        new AsyncTask<String, Void, List<AssignRole>>() {
            @Override
            protected List<AssignRole> doInBackground(String... params) {
                return AssignRole.ListAssignRoleInDepartment(params[0],user.getEmail(),user.getPassword());
            }
            @Override
            protected void onPostExecute(List<AssignRole> result) {
                AssignRoleAdapter roleAdapter=new AssignRoleAdapter(AuthoriseStaffActitvity.this,R.layout.assignrole,result);
                listView.setAdapter(roleAdapter);


            }
        }.execute(user.getDeptId());

    }

}

/*
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        List<String> emps = Employee.list();
        setListAdapter(new MyAdapter(this, R.layout.row3, emps));
    }


 */