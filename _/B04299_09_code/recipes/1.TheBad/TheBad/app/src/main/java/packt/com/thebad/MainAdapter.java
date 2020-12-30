package packt.com.thebad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by mike on 26-08-15.
 */
public class MainAdapter  extends ArrayAdapter<BadMovie> {

    private Context mContext;
    private int mAdapterResourceId;
    public List<BadMovie> Items = null;

    static class ViewHolder {
        TextView title;
        TextView genre;
        ImageView image;
        TextView actors;
        TextView director;
        TextView year;
    }

    @Override
    public int getCount() {
        super.getCount();
        int count = Items != null ? Items.size() : 0;
        return count;
    }

    public MainAdapter(Context context, int adapterResourceId, List<BadMovie> items) {
        super(context, adapterResourceId, items);
        this.Items = items;
        this.mContext = context;
        this.mAdapterResourceId = adapterResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mAdapterResourceId, null);
            ViewHolder holder = new ViewHolder();

            holder.title = (TextView) v.findViewById(R.id.main_text_title);
            holder.actors = (TextView) v.findViewById(R.id.main_text_actors);
            holder.image = (ImageView) v.findViewById(R.id.main_image);
            holder.genre = (TextView) v.findViewById(R.id.main_text_genre);
            holder.director = (TextView) v.findViewById(R.id.main_text_director);
            holder.year = (TextView) v.findViewById(R.id.main_text_year);
            v.setTag(holder);
        }

        final BadMovie item = Items.get(position);

        if (item != null) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.director.setText(item.director);
            holder.actors.setText(item.actors);
            holder.genre.setText(item.genre);
            holder.year.setText(item.year);
            holder.title.setText(item.title);



            new Thread(new Runnable() {
                public void run(){
                    try {
                        final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(item.imageUrl).getContent());
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.image.setImageBitmap(bitmap);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();



        }
        return v;
    }


}



