package com.packt.wateryou.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.packt.wateryou.EntryActivity;
import com.packt.wateryou.R;
import com.packt.wateryou.models.Drink;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {



    private ArrayList<Drink> mDrinks;
    private Context mContext;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mCommentTextView;
        public TextView mDateTimeTextView;
        public ImageView mImageView;
        //1
        public Drink mDrink;

        public ViewHolder(View v) {
            super(v);
        }
    }

    public MainAdapter(Context context, ArrayList<Drink> drinks) {
        mDrinks = drinks;
        mContext = context;
    }

    private int REQUEST_EDIT_ENTRY = 2;

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mDateTimeTextView = (TextView)v.findViewById(R.id.main_date_time_textview);
        viewHolder.mCommentTextView = (TextView)v.findViewById(R.id.main_comment_textview);
        viewHolder.mImageView = (ImageView)v.findViewById(R.id.main_image_view);

        //4
        v.setTag(viewHolder);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) view.getTag();
                if (view.getId() == holder.itemView.getId()) {

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            ((Activity)mContext), holder.mImageView, mContext.getString(R.string.transition_preview));

                    Intent intent = new Intent(mContext, EntryActivity.class);
                    intent.putExtra("edit_drink", holder.mDrink);
                    ActivityCompat.startActivityForResult(((Activity) mContext), intent, REQUEST_EDIT_ENTRY, options.toBundle());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Drink currentDrink = mDrinks.get(position);
        holder.mDrink = currentDrink;

        holder.mCommentTextView.setText(currentDrink.comments);
        holder.mDateTimeTextView.setText(currentDrink.dateAndTime.toString());
        if (currentDrink.imageUri != null){
            Bitmap bitmap = getBitmapFromUri(Uri.parse(currentDrink.imageUri));
            holder.mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }

    public Bitmap getBitmapFromUri(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null);
        ContentResolver cr = mContext.getContentResolver();
        try {
            Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);
            return bitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
