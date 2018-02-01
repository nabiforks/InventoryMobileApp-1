package team8.inventorymobileapp.com.inventorymobileapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.Constant;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.JSONParser;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.User;
import team8.inventorymobileapp.com.inventorymobileapp.Representative.ListDisbursementActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
   /* private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };*/
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

//    User user = new User(LoginActivity.this);
    private UserLoginTask mAuthTask = null;
    User user =null;
    private static final String TAG = "LoginActivity";
    //private static final int REQUEST_SIGNUP = 0;
    EditText emailEditText;
    EditText passwordEditText;
    Button button;
    String role =null;
    private String name;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = new User(LoginActivity.this);

        if(user.getEmail()=="") {
            //Use anthor if block to validate the role and Create  the  Intent. and start the actitvity.
            //Intent intent = new Intent();
            //startActivity(intent);

             //Log.i("Shared preference","null");
            emailEditText = (EditText) findViewById(R.id.input_email);
            passwordEditText = (EditText) findViewById(R.id.input_password);
            button = (Button) findViewById(R.id.btn_login);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    login();
                }
            });

        } else {

            //Log.i("Shared preference"," not null");
           /* emailEditText = (EditText) findViewById(R.id.input_email);
            passwordEditText = (EditText) findViewById(R.id.input_password);
            button = (Button) findViewById(R.id.btn_login);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    login();
                }
            });*/
           String role=user.getRole();
            if(role.equals("Head")||role.equals("ActHead")){
                startActivity(new Intent(LoginActivity.this, DepartmentHeadActivity.class));
                finish();
            }
            else if(role.equals("SSupervisor")||role.equals("ActSSup")){
                startActivity(new Intent(LoginActivity.this, SupervisorActivity.class));
                finish();
            }
            else if(role.equals("SClerk")){
                startActivity(new Intent(LoginActivity.this, StoreClerkActivity.class));
                finish();
            }
            else{
                startActivity(new Intent(LoginActivity.this, ListDisbursementActivity.class));
                finish();
            }


            //onLoginSuccess(name);
        }
}



    public void login() {
        if (mAuthTask != null) {
            onLoginFailed();
            return;
        }

        passwordEditText.setError(null);
        passwordEditText.setError(null);

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

       // button.setEnabled(false);

        showProgress(true);

        String email = emailEditText.getText().toString();
        Log.i("aaa",email);
        String password = passwordEditText.getText().toString();
        Log.i("bbb",password);


        // TODO: Implement your own authentication logic here.


        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);

    }

    private void showProgress(final boolean show){
        if(show){
            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        } else {
            if(progressDialog != null) progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);

    }

    public void onLoginSuccess(String name) {
        Toast.makeText(getBaseContext(), "Login Successful! Hello " + name + "!", Toast.LENGTH_LONG).show();
        showProgress(false);
      //  button.setEnabled(true);
//        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        showProgress(false);
        //
        // button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        View focusView = null;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText.setError("Enter a valid email Or Password");
            focusView = passwordEditText;
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email Or Password");
            focusView = emailEditText;
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if(!valid){
            focusView.requestFocus();
        }

        return valid;
    }
/*
    /*//**
      Represents an asynchronous login/registration task used to authenticate
      the user.
     /*//**/
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;



        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            name = "";
        }
        JSONObject loginResult = null;
        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO: CHANGE HOST URL
            String host = "http://"+ Constant.ipAddress+"/InventoryWCF/WCF/LoginService.svc/Login";


            try {
                JSONObject loginData = new JSONObject();
                loginData.put("Email", mEmail);
                loginData.put("Password", mPassword);

                loginResult = new JSONObject(JSONParser.postStream(host, loginData.toString()));
                Log.i("LOOK AT ME", loginResult.toString());

                if (loginResult.getBoolean("Result")) {
                    // Account exists, return true if the password matches.
                    name = loginResult.getString("EmpName");
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                /*SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences
                                (getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();*/
                try {
                    //editor.putString("Email", mEmail);
                   // editor.putString("Password", mPassword);
                   // editor.putString("DepartmentCode", loginResult.getString("DepartmentCode"));
                   // editor.putBoolean("Result", loginResult.getBoolean("Result"));
                  //  editor.putString("EmpName", loginResult.getString("EmpName"));
                  //  editor.putString("RoleCode", loginResult.getString("RoleCode"));
                    //User user = new User(LoginActivity.this);
                     user.setEmail(mEmail);
                     user.setPassword(mPassword);
                     user.setDeptId(loginResult.getString("DepartmentCode"));
                     user.setEmpName(loginResult.getString("EmpName"));
                     role=loginResult.getString("RoleCode");
                     user.setRole(role);
                     Log.i("Loot at me ",user.toString());
                    //editor.commit();
                }catch (Exception ex){
                    ex.getMessage();
                }

                if(role.equals("Head")||role.equals("ActHead")){
                    startActivity(new Intent(LoginActivity.this, DepartmentHeadActivity.class));
                    finish();
                }
                else if(role.equals("SSupervisor")||role.equals("ActSSup")){
                    startActivity(new Intent(LoginActivity.this, SupervisorActivity.class));
                    finish();
                }
                else if(role.equals("SClerk")){
                    startActivity(new Intent(LoginActivity.this, StoreClerkActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(LoginActivity.this, ListDisbursementActivity.class));
                    finish();
                }


                onLoginSuccess(name);
            } else {
                onLoginFailed();
                passwordEditText.setError(getString(R.string.error_incorrect_password));
                passwordEditText.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
   /* EditText emailEditText;
    EditText passwordEditText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText=(EditText)findViewById(R.id.input_email);

        passwordEditText=(EditText) findViewById(R.id.input_password);
         String mEmail = emailEditText.getText().toString();
       final String  mPassword = passwordEditText.getText().toString();
        Log.i("aaa",mEmail);
        Log.i("aaa",mPassword);
      final User user=new User(mEmail,mPassword);
        button=(Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AsyncTask<User, Void, JSONObject>() {
                    @Override
                    protected JSONObject doInBackground(User... params) {

                         return  User.validateUser(params[0]);
                    }
                    @Override
                    protected void onPostExecute(JSONObject result) {
                        try{
                        if (result.getBoolean("Result")) {
                            // Account exists, return true if the password matches.
                           String name = result.getString("Name");
                            Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                        }}catch (Exception e){

                        }
                    }
                }.execute(user);
            }
        });*/

    }
}

