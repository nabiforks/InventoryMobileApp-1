
package team8.inventorymobileapp.com.inventorymobileapp.Entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoanni on 1/25/2018.
 */

public class Role extends java.util.HashMap<String,String>
{
    final static String host="http://"+Constant.ipAddress+"/InventoryWCF/WCF/DepartmentService.svc";
    public Role(String Id, String Name)
    {
        put("Id",Id);
        put("Name",Name);

    }
    public Role(String Id)
    {
        put("Id",Id);

    }
    public static List<Role> ListRoleInDepartment(String email,String password)
    {
        List<Role> rlist=new ArrayList<Role>();
        try
        {
            JSONArray a= JSONParser.getJSONArrayFromUrl(host+ "/Roles/Department/"+email+"/"+password);
            for(int i=0;i<a.length();i++)
            {
                JSONObject b=a.getJSONObject(i);
                Role role=new Role(b.getString("Id"));
                rlist.add(role);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("In getRole()","error occurs");

        }
        return rlist;
    }



    public static Role getRole(String id,String email,String password)
    {
        Role role=null;
        try
        {
            JSONObject b=JSONParser.getJSONFromUrl(host+"/Role/Id/"+id+"/"+email+"/"+password);
            Log.i("rrrr",host+"/Role/Id/"+id);
            role=new Role(
                    b.getString("Id"),
                    b.getString("Name")

            );
            Log.i("rrrr",role.toString());

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getRole()","error occurs");
        }
        return role;
    }

    public static void updateRole(Role role,String email,String password)
    {
        JSONObject jrole=new JSONObject();
        try
        {
            jrole.put("Id",role.get("Id"));
            jrole.put("Name",role.get("Name"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("In getRole()","error occurs");
        }
        String result=JSONParser.postStream(host+"/Update/"+email+"/"+password,jrole.toString());
    }






}
