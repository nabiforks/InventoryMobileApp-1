package team8.inventorymobileapp.com.inventorymobileapp.DepartmentHead;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.text.InputType;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;

import team8.inventorymobileapp.com.inventorymobileapp.DepartmentHeadActivity;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.AssignRole;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Employee;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Role;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.R;

public class AddAuthoriseActivity extends DepartmentHeadActivity implements OnClickListener {
    User user = null;
    private Spinner empspinner;
    private Spinner rolespinner;

    private List<String> empList;

    private List<String> roleList;
    private ArrayAdapter<String> empadapter;
    private ArrayAdapter<String> roleadapter;

    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private TextView txtEmployeeName;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView txtMessage;
    private  Date startdate;
    private Date enddate;
    private String role;
    private String empcode;
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authorise);
        user =     new User(AddAuthoriseActivity.this);
        txtMessage=(TextView)findViewById(R.id.txtMessage);
        empspinner = (Spinner) findViewById(R.id.listEmployeeCode);
        new AsyncTask<Void, Void, List<Employee>>() {
            @Override
            protected List<Employee> doInBackground(Void... params) {
                List<Employee> eList = Employee.ListEmployeeInDepartment(user.getDeptId(),user.getEmail(),user.getPassword());
                return eList;
            }

            @Override
            protected void onPostExecute(List<Employee> result) {
                loadView(result);
            }
        }.execute();


        rolespinner = (Spinner) findViewById(R.id.listRole);
        new AsyncTask<Void, Void, List<Role>>() {
            @Override
            protected List<Role> doInBackground(Void... params) {
                List<Role> rList = Role.ListRoleInDepartment(user.getEmail(),user.getPassword());
                return rList;
            }

            @Override
            protected void onPostExecute(List<Role> result2) {
                loadView2(result2);
            }
        }.execute();



        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.CHINA);
        findViewsById();
        setDateTimeField();

        txtEmployeeName=findViewById(R.id.txtEmployeeName);
        final Button button = findViewById(R.id.btnAdd);

        fromDateEtxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AssignRole assignRole=new AssignRole();
                String start= fromDateEtxt.getText().toString();
                String end=toDateEtxt .getText().toString();
                Date convertedstartDate = new Date();
                Date convertedendDate = new Date();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy",Locale.US);
                SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy/MM/dd",Locale.US);
                // Log.i("enddddddddddd",end);
                role=rolespinner.getSelectedItem().toString();
                try {

                    convertedstartDate=dateFormat.parse(start);
                    convertedendDate=dateFormat.parse(end);



                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.i("Starttttttttt",start);
                if(convertedstartDate.after(convertedendDate)){
                    txtMessage.setText("Start Date should be earlier than end Date");
                }
                else if((start.equals("")||end.equals("") )&& role.equals("ActHead")){


                    txtMessage.setText("Date can't be empty");
                }
                else{
                    txtMessage.setText("");
                    Log.i("roleCode",role);
                    txtMessage=(TextView)findViewById(R.id.txtMessage);
                    // Log.i("Start date in object",assignRole.get("StartDate"));
                    // Log.i("Objectttttttttt",assignRole.toString());
                    String date=dateFormat2.format(convertedstartDate);
                    //Log.i("startttttttttt",date);
                    assignRole.put("StartDate",date);

                    date=dateFormat2.format(convertedendDate);
                    //Log.i("endddddddddd",date);
                    assignRole.put("EndDate",date);
                    empcode = empspinner.getSelectedItem().toString();
                    assignRole.put("EmployeeCode",empcode);
                    //Log.i("empcode",empcode);
                    assignRole.put("AssignedBy",user.getEmail());
                    assignRole.put("TemporaryRoleCode",role);

                    new AsyncTask<AssignRole, Void, String>() {
                        @Override
                        protected String doInBackground(AssignRole... params) {
                            AssignRole.addAssignRole(params[0],user.getEmail(),user.getPassword());
                            return null;
                        }
                        @Override
                        protected void onPostExecute(String result) {
                            Log.i("result",result);
                            if(result=="false"){
                                txtMessage.setText("Already assigned!!!");
                            }
                            else
                            {
                                Toast t = Toast.makeText(getApplicationContext(),
                                        "You have approved the authorise!", Toast.LENGTH_SHORT);
                                t.show();
                                // Intent launchActivity1= new Intent(AddAuthorise.this,AuthoriseStaffActitvity.class);
                                //startActivity(launchActivity1);

                            }}
                    }.execute(assignRole);
                }}
        });

    }

    /*
    public void test(List<AssignRole> list)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sdate = null;
        Date edate = null;

        for (AssignRole a : list)

        {
            if (a.get("EmployeeCode") == empcode&&a.get("TemporaryRoleCode") != "ActHead")
            {
                txtMessage.setText("The employee has already assigned.");
            }
            else if(a.get("EmployeeCode") == empcode&&a.get("TemporaryRoleCode") == "ActHead")
            {
                try
                {
                    sdate = sdf.parse(a.get("StartDate"));
                    edate = sdf.parse(a.get("StartDate"));
                    startdate = sdf.parse(fromDatePickerDialog.getDatePicker().toString());
                    Log.i("Startdate", startdate.toString());
                    enddate = sdf.parse(toDatePickerDialog.getDatePicker().toString());
                    Log.i("endDate", enddate.toString());
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                if ((sdate.getTime() > startdate.getTime() && startdate.getTime() < edate.getTime())
                        || (sdate.getTime() > enddate.getTime() && enddate.getTime() < edate.getTime()))
                {
                    txtMessage.setText("The period has already assigned.");
                }
                else
                {
                    AssignRole assignrole= new AssignRole( role, empcode, startdate.toString(),
                            enddate.toString(),"");
                    new AsyncTask<AssignRole, Void, Void>() {
                        @Override
                        protected Void doInBackground(AssignRole... params) {
                            AssignRole.addAssignRole(params[0]);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            Toast t=Toast.makeText(getApplicationContext(),
                                    "You have approved the request successfully!",Toast.LENGTH_SHORT);
                            t.show();                    }
                    }.execute(assignrole);

                }
            }
             else if(a.get("EmployeeCode")!= empcode&&a.get("TemporaryRoleCode")== "ActHead")
            {
                if((sdate.getTime() > startdate.getTime() && startdate.getTime() < edate.getTime())
                        || (sdate.getTime() > enddate.getTime() && enddate.getTime() < edate.getTime()))
                {
                    txtMessage.setText("The period has already assigned.");
                }
                else
                {
                    AssignRole assignrole = new AssignRole(role, empcode, startdate.toString(),
                            enddate.toString(), "");
                    new AsyncTask<AssignRole, Void, Void>() {
                        @Override
                        protected Void doInBackground(AssignRole... params) {
                            AssignRole.addAssignRole(params[0]);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            Toast t = Toast.makeText(getApplicationContext(),
                                    "You have approved the request successfully!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }.execute(assignrole);
                }
            }
            else if(a.get("EmployeeCode")!= empcode&&a.get("TemporaryRoleCode")!= "ActHead")
            {
                if(a.get("TemporaryRoleCode")==role)
                {
                    txtMessage.setText("The role is already assigned");
                }
                else
                {
                    AssignRole assignrole = new AssignRole(role, empcode, "",
                            "", "");
                    new AsyncTask<AssignRole, Void, Void>() {
                        @Override
                        protected Void doInBackground(AssignRole... params) {
                            AssignRole.addAssignRole(params[0]);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            Toast t=Toast.makeText(getApplicationContext(),
                                    "You have approved the request successfully!",Toast.LENGTH_SHORT);
                            t.show();                    }
                    }.execute(assignrole);
                }
            }
            }
        }
*/

    @SuppressLint("StaticFieldLeak")
    private void loadView(List<Employee> result) {
        final List<Employee> eList = result;
        empList = new ArrayList<String>();
        new AsyncTask<Void, Void, List<AssignRole>>() {
            List<AssignRole> arList;

            @Override
            protected List<AssignRole> doInBackground(Void... params) {
                arList = AssignRole.ListAssignRoleInDepartment(user.getDeptId(),user.getEmail(),user.getPassword());
                return arList;
            }


            @Override
            protected void onPostExecute(List<AssignRole> result) {
                for (Employee e : eList) {
                    empList.add(e.get("EmployeeCode"));
                }
                loadEmployeeSpinner();
            }
        }.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private void loadView2(List<Role> result2) {
        final List<Role> rList = result2;
        roleList = new ArrayList<String>();
        new AsyncTask<Void, Void, List<AssignRole>>() {
            List<AssignRole> arList;

            @Override
            protected List<AssignRole> doInBackground(Void... params) {
                arList = AssignRole.ListAssignRoleInDepartment(user.getDeptId(),user.getEmail(),user.getPassword());
                return arList;
            }

            @Override
            protected void onPostExecute(List<AssignRole> result2) {
                for (Role r : rList) {
                    roleList.add(r.get("Id"));
                }
                loadRoleSpinner();
            }
        }.execute();

    }

    private void loadEmployeeSpinner() {
        empadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, empList);
        empadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empspinner.setAdapter(empadapter);
        empspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                new AsyncTask<String, Void, Employee>() {
                    @Override
                    protected Employee doInBackground(String... params) {
                        return Employee.getEmployee(params[0],user.getEmail(),user.getPassword());
                        //String employeename=employee.get("EmployeeName");

                    }
                    @Override
                    protected void onPostExecute(Employee result)
                    {
                        txtEmployeeName.setText(result.get("EmployeeName"));
                    }
                }.execute(empadapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                textView.setText("Please select your choice");
            }
        });
    }


    private void loadRoleSpinner() {
        roleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleList);
        roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolespinner.setAdapter(roleadapter);
        rolespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                textView.setText("You have choosen " + adapter.getItem(position));
                role=rolespinner.getSelectedItem().toString();
                if(role.equals("Rep")||role.equals("Head")){
                    fromDateEtxt.setEnabled(false);
                    toDateEtxt.setEnabled(false);
                }
                else{
                    fromDateEtxt.setEnabled(true);
                    toDateEtxt.setEnabled(true);
                }
                txtMessage.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                textView.setText("Please select your choice");
            }
        });
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.editTxtStartDate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.editTxtEndDate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view)
    {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }


}







