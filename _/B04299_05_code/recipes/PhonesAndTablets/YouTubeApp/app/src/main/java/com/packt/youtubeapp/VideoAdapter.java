package com.packt.youtubeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mike on 03-07-15.
 */
public class VideoAdapter extends ArrayAdapter<VideoItem> {

private Context mContext;
private int mAdapterResourceId;
public ArrayList<VideoItem> Items = null;

static class ViewHolder{
    TextView orderNumber;
    TextView deliveryAddress;
    TextView pickUpAddress;
}

    @Override
    public int getCount(){

        super.getCount();
        int count = Items!=null ? Items.size() : 0;
        return count;
    }

    public VideoAdapter (Context context, int adapterResourceId, ArrayList<VideoItem> items)
    {
        super(context, adapterResourceId, items);
        this.Items = items;
        this.mContext = context;
        this.mAdapterResourceId = adapterResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = null;
        v = convertView;
        if (v == null){
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mAdapterResourceId, null);
            ViewHolder holder = new ViewHolder();

            holder.orderNumber = (TextView) v.findViewById(R.id.cargo_order_text_order_number);
            holder.deliveryAddress = (TextView)v.findViewById(R.id.cargo_order_text_delivery_address);
            holder.pickUpAddress = (TextView)v.findViewById(R.id.cargo_order_text_pickup_address);

            v.setTag(holder);
        }

        final VideoItem item = Items.get(position);

        if(item != null){
            final ViewHolder holder = (ViewHolder)v.getTag();
            holder.orderNumber.setText(item.getId());
            holder.pickUpAddress.setText( item.getTitle());
            holder.deliveryAddress.setText( item.getThumbnailURL());
        }
        return v;
    }
}
