package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUS on 18/01/30.
 */

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private   String email;
    private String password;
    private  String role;

    private  String deptId;

    public  User(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        this.role="Head";
    }

    /*public  User(String email,String password){
        email=email;
        sharedPreferences.edit().putString("userdata",userName).commit();
        password=password;
    }*/
    public String getRole() {
        role =   sharedPreferences.getString("role","");
        return role;
    }

    public  void setRole(String role)
    {
        this.role =  role;
        sharedPreferences.edit().putString("role",role).commit();
    }
    public String getEmail() {
        email =   sharedPreferences.getString("email","");
        return email;
    }

    public  void setEmail(String email)
    {
         this.email =  email;
        sharedPreferences.edit().putString("email",email).commit();
    }
    public  void setPassword(String password)
    {
        this.password = password;
        sharedPreferences.edit().putString("password",password).commit();
    }

    public String getPassword()
    {
        password =   sharedPreferences.getString("password","");
        return  password;
    }

    public  void setDeptId(String deptId)
    {
        this.deptId = deptId;
        sharedPreferences.edit().putString("deptId",deptId).commit();

    }

    public String getDeptId()
    {
        deptId = sharedPreferences.getString("deptId","");
        return deptId;
    }

    public  void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }
}
