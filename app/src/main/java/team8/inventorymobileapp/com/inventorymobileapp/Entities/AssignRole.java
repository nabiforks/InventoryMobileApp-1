package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoanni on 1/25/2018.
 */

public class AssignRole extends java.util.HashMap<String,String>
{
    final static String host="http://"+Constant.ipAddress+"/InventoryWCF/WCF/DepartmentService.svc";
    public AssignRole(String AssignRoleCode, String TemporaryRoleCode, String EmployeeCode,
                      String StartDate, String EndDate, String AssignedBy,String EmployeeName)
    {
        put("AssignRoleCode",AssignRoleCode);
        put("TemporaryRoleCode",TemporaryRoleCode);
        put("EmployeeCode",EmployeeCode);
        put("StartDate",StartDate);
        put("EndDate",EndDate);
        put("AssignedBy",AssignedBy);
        put("EmployeeName",EmployeeName);
    }
    public AssignRole( String TemporaryRoleCode, String EmployeeCode,
                      String StartDate, String EndDate, String AssignedBy)
    {

        put("TemporaryRoleCode",TemporaryRoleCode);
        put("EmployeeCode",EmployeeCode);
        put("StartDate",StartDate);
        put("EndDate",EndDate);
        put("AssignedBy",AssignedBy);

    }
    public  AssignRole(){}

    public AssignRole(String AssignRoleCode)
    {
        put("AssignRoleCode",AssignRoleCode);

    }
    public static List<AssignRole> ListAssignRoleInDepartment(String deptCode,String email,String password)
    {
        List<AssignRole> alist=new ArrayList<AssignRole>();
        try
        {
            JSONArray a= JSONParser.getJSONArrayFromUrl(host+"/AssignRoles/Department/DepartmentCode/"+deptCode+"/"+email+"/"+password);
            for(int i=0;i<a.length();i++)
            {
                JSONObject b=a.getJSONObject(i);
                AssignRole assignrole=new AssignRole(b.getString("AssignRoleCode"),b.getString("TemporaryRoleCode"),
                        b.getString("EmployeeCode"),
                        b.getString("StartDate"),b.getString("EndDate") ,
                        b.getString("AssignedBy"),b.getString("EmployeeName"));
                alist.add(assignrole);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("In getAssignRole1()","error occurs");

        }
        return alist;
    }

   /* public static List<AssignRole> ListAssignRole()
    {
        List<AssignRole> alist=new ArrayList<AssignRole>();
        try
        {
            JSONArray a= JSONParser.getJSONArrayFromUrl(host+"/AssignRoles");
            for(int i=0;i<a.length();i++)
            {
                JSONObject b=a.getJSONObject(i);
                AssignRole assignrole=new AssignRole(b.getString("AssignRoleCode"));
                alist.add(assignrole);
            }
        }
        catch(Exception e){

        }
        return alist;
    }*/
    public static AssignRole getAssignRole(String assignrolecode,String email,String password)
    {
        AssignRole assignrole =null;
        try
        {
            JSONObject b=JSONParser.getJSONFromUrl(host+"/AssignRole/AssignRoleCode/"+assignrolecode+"/"+email+"/"+password);
            Log.i("aaaa",host+"/AssignRole/AssignRoleCode/"+assignrolecode);
            assignrole=new AssignRole(
                    b.getString("AssignRoleCode"),
                    b.getString("TemporaryRoleCode"),
                    b.getString("EmployeeCode"),
                    b.getString("StartDate"),
                    b.getString("EndDate"),
                    b.getString("AssignedBy"),
                    b.getString("EmployeeName")
                    );
            Log.i("aaaa",assignrole.toString());

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getAssignRole2()","error occurs");
        }
        return assignrole;
    }


    public static void updateAssignRole(AssignRole assignrole,String email,String password)
    {
        JSONObject jassignrole=new JSONObject();
        try
        {
            jassignrole.put("AssignRoleCode",assignrole.get("AssignRoleCode"));
            jassignrole.put("TemporaryRoleCode",assignrole.get("TemporaryRoleCode"));
            jassignrole.put("EmployeeCode",assignrole.get("EmployeeCode"));
            jassignrole.put("StartDate",assignrole.get("StartDate"));
            jassignrole.put("EndDate",assignrole.get("EndDate"));
            jassignrole.put("AssignedBy",assignrole.get("AssignedBy"));
            jassignrole.put("EmployeeName",assignrole.get("EmployeeName"));



        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getAssignRole3()","error occurs");
        }
       String result=JSONParser.postStream(host+"/Update/"+email+"/"+password,jassignrole.toString());
    }

    public static String addAssignRole(AssignRole assignrole,String email,String password)
    {
        JSONObject jassignrole=new JSONObject();
        try
        {
            //jassignrole.put("AssignRoleCode",assignrole.get("AssignRoleCode"));
            jassignrole.put("TemporaryRoleCode",assignrole.get("TemporaryRoleCode"));
            jassignrole.put("EmployeeCode",assignrole.get("EmployeeCode"));
            jassignrole.put("StartDate",assignrole.get("StartDate"));
            jassignrole.put("EndDate",assignrole.get("EndDate"));
            jassignrole.put("AssignedBy",assignrole.get("AssignedBy"));
            jassignrole.put("EmployeeName",assignrole.get("EmployeeName"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getAssignRole3()","error occurs");
        }
        Log.d("yf",jassignrole.toString());
        String result=JSONParser.postStream(host+"/Add/"+email+"/"+password,jassignrole.toString());

        Log.d("yf",result);
        return  result;

    }



}
