package team8.inventorymobileapp.com.inventorymobileapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.AssignRole;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.Employee;
import team8.inventorymobileapp.com.inventorymobileapp.R;


public class AssignRoleAdapter extends ArrayAdapter<AssignRole> {

    private List<AssignRole> assignroles;

    int resource;

    public AssignRoleAdapter(Context context, int resource,
                             List<AssignRole> assignroles) {
        super(context,resource,assignroles);
        this.resource=resource;
        this.assignroles = assignroles;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        AssignRole a=assignroles.get(position);
        if (a != null) {
            TextView textEmployeeName = (TextView) v.findViewById(R.id.textEmployeeName);
            textEmployeeName.setText(a.get("EmployeeName"));
            TextView textRole = (TextView) v.findViewById(R.id.textRole);
            textRole.setText(a.get("TemporaryRoleCode"));
            TextView textStartDate = (TextView) v.findViewById(R.id.textStartDate);
            textStartDate.setText(a.get("StartDate"));
            TextView  textEndDate= (TextView) v.findViewById(R.id.textEndDate);
            textEndDate.setText(a.get("EndDate"));

        }
        return v;
    }

}