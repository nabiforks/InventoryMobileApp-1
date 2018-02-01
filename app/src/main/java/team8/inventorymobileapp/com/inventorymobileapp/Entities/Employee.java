package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoanni on 1/25/2018.
 */

public class Employee extends java.util.HashMap<String,String>
{
    final static String host="http://"+Constant.ipAddress+"/InventoryWCF/WCF/DepartmentService.svc";
    public Employee(String EmployeeCode, String ReportTo, String DepartmentCode,
                    String CurrentRoleCode, String EmployeeName, String EmployeeTitle, String UserName)
    {
        put("EmployeeCode",EmployeeCode);
        put("ReportTo",ReportTo);
        put("DepartmentCode",DepartmentCode);
        put("CurrentRoleCode",CurrentRoleCode);
        put("EmployeeName",EmployeeName);
        put("EmployeeTitle",EmployeeTitle);
        put("UserName",UserName);
    }
    public Employee()
    {

    }
    public Employee(String EmployeeCode)
    {
        put("EmployeeCode",EmployeeCode);

    }
    public static List<Employee> ListEmployeeInDepartment(String deptCode,String email,String password)
    {
        List<Employee> elist=new ArrayList<Employee>();
        try
        {
            JSONArray a= JSONParser.getJSONArrayFromUrl(host+"/Employees/Department/DepartmentCode/"+deptCode+"/"+email+"/"+password);
            for(int i=0;i<a.length();i++)
            {
                JSONObject b=a.getJSONObject(i);
                Employee employee=new Employee(b.getString("EmployeeCode"),b.getString("ReportTo"),
                        b.getString("DepartmentCode"),b.getString("CurrentRoleCode"),
                        b.getString("EmployeeName"),b.getString("EmployeeTitle"),
                        b.getString("UserName"));
                elist.add(employee);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("In getEmployee1()","error occurs");

        }
        return elist;
    }


   /* public static List<Employee> ListEmployee()
    {
        List<Employee> elist=new ArrayList<Employee>();
        try
        {
            JSONArray a= JSONParser.getJSONArrayFromUrl(host+"/Employees");
            for(int i=0;i<a.length();i++)
            {
                JSONObject b=a.getJSONObject(i);
                Employee employee=new Employee(b.getString("EmployeeCode"));
                elist.add(employee);
            }
        }
        catch(Exception e){

        }
        return elist;
    }*/

    public static Employee getEmployee(String employeecode,String email,String password)
    {

        Employee employee=null;
        try
        {
            JSONObject b=JSONParser.getJSONFromUrl(host+"/Employee/EmployeeCode/"+employeecode+"/"+email+"/"+password);
            Log.i("employeecode",host+"/Employee/EmployeeCode/"+employeecode);
             employee=new Employee(
                    b.getString("EmployeeCode"),
                    b.getString("ReportTo"),
                    b.getString("DepartmentCode"),
                    b.getString("CurrentRoleCode"),
                    b.getString("EmployeeName"),
                    b.getString("EmployeeTitle"),
                    b.getString("UserName")
            );
            Log.i("employeecode",employee.toString());

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getEmployee2()","error occurs");
        }
        return employee;
    }

    public static void updateEmployee(Employee employee,String email,String password)
    {
        JSONObject jemployee=new JSONObject();
        try
        {
            jemployee.put("EmployeeCode",employee.get("EmployeeCode"));
            jemployee.put("ReportTo",employee.get("ReportTo"));
            jemployee.put("DepartmentCode",employee.get("DepartmentCode"));
            jemployee.put("CurrentRoleCode",employee.get("CurrentRoleCode"));
            jemployee.put("EmployeeName",employee.get("EmployeeName"));
            jemployee.put("EmployeeTitle",employee.get("EmployeeTitle"));
            jemployee.put("UserName",employee.get("UserName"));


        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getEmployee3()","error occurs");
        }
        String result=JSONParser.postStream(host+"/Employee/Update"+"/"+email+"/"+password,jemployee.toString());
    }








}
