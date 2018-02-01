package team8.inventorymobileapp.com.inventorymobileapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import team8.inventorymobileapp.com.inventorymobileapp.Entities.PurchaseOrder;
import team8.inventorymobileapp.com.inventorymobileapp.StoreSupervisor.PendingPODetailActivity;
import team8.inventorymobileapp.com.inventorymobileapp.R;

/**
 * Created by ASUS on 18/01/26.
 */

public class PendingPOAdapter extends ArrayAdapter<PurchaseOrder> {
    private List<PurchaseOrder> items;
    int resource;

    public PendingPOAdapter(Context context, int resource, List<PurchaseOrder> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource,null);
        PurchaseOrder po = items.get(position);
        if (po != null) {
            int[] ids = { R.id.lblSupplierName,R.id.lblDateCreated , R.id.lblTotalPrice};
            String[] keys = {"SupplierName", "DateCreated", "TotalPrice"};

            final ListView lv = (ListView)v. findViewById(R.id.listViewPOItem);
            for (int i = 0; i < keys.length; i++) {
                TextView e = (TextView)v. findViewById(ids[i]);
                e.setText( po.get(keys[i]));
            }


        }
        ImageButton txtDetail = (ImageButton) v.findViewById(R.id.imageButtonDetail);
        txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent().getParent().getParent();
                final int position = listView.getPositionForView(parentRow);
                PurchaseOrder p = (PurchaseOrder) listView.getAdapter().getItem(position);
                Intent intent = new Intent(v.getContext(), PendingPODetailActivity.class);
                intent.putExtra("PurchaseOrder", p);

                v.getContext().startActivity(intent);
                }
        });

        return v;
    }


   }
