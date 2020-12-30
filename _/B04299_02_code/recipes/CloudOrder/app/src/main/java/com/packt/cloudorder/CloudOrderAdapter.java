package com.packt.cloudorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CloudOrderAdapter extends ArrayAdapter<CloudOrder> {

    private Context mContext;
    private int mAdapterResourceId;
    public ArrayList<CloudOrder> mItems = null;

    static class ViewHolder{
        TextView customer;
        TextView address;
    }

    @Override
    public int getCount(){
        super.getCount();
        int count = mItems !=null ? mItems.size() : 0;
        return count;
    }

    public CloudOrderAdapter (Context context, int adapterResourceId, ArrayList<CloudOrder>items) {
        super(context, adapterResourceId, items);
        this.mItems = items;
        this.mContext = context;
        this.mAdapterResourceId = adapterResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        v = convertView;
        if (v == null){
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mAdapterResourceId, null);
            ViewHolder holder = new ViewHolder();
            holder.customer = (TextView) v.findViewById(R.id.adapter_main_customer);
            holder.address = (TextView)v.findViewById(R.id.adapter_main_address);
            v.setTag(holder);
        }

        final CloudOrder item = mItems.get(position);
        if(item != null){
            final ViewHolder holder = (ViewHolder)v.getTag();
            holder.customer.setText(item.getCustomer());
            holder.address.setText(item.getAddress());
        }
        return v;
    }
}


