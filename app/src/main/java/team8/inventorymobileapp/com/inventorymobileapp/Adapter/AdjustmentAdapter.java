package team8.inventorymobileapp.com.inventorymobileapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.Adjustment;
import team8.inventorymobileapp.com.inventorymobileapp.Entities.PurchaseOrder;
import team8.inventorymobileapp.com.inventorymobileapp.R;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.AdjustmentDetailsActivity;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.PendingPODetailActivity;

/**
 * Created by ASUS on 18/01/31.
 */

public class AdjustmentAdapter extends ArrayAdapter<Adjustment> {
    private List<Adjustment> items;
    int resource;

    public AdjustmentAdapter(Context context, int resource, List<Adjustment> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource,null);
        Adjustment ad = items.get(position);
        if (ad != null) {
            int[] ids = { R.id.lblDescription,R.id.lblReason , R.id.lblQty};
            String[] keys = {"Description", "Reason", "AdjustmentQuant"};


            for (int i = 0; i < keys.length; i++) {
                TextView e = (TextView)v. findViewById(ids[i]);
                e.setText( ad.get(keys[i]));
            }


        }
        ImageButton txtDetail = (ImageButton) v.findViewById(R.id.imageButtonDetail);
        txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent().getParent().getParent();
                final int position = listView.getPositionForView(parentRow);
                Adjustment adj = (Adjustment) listView.getAdapter().getItem(position);
                Intent intent = new Intent(v.getContext(), AdjustmentDetailsActivity.class);
                intent.putExtra("adjustmentCode", adj.get("AdjustmentCode"));

                v.getContext().startActivity(intent);
            }
        });

        return v;
    }


}
