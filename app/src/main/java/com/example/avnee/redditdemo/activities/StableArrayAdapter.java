package com.example.avnee.redditdemo.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avnee.redditdemo.R;
import com.example.avnee.redditdemo.utils.redditjsonclass.Child;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by avnee on 4/2/2017.
 */

public class StableArrayAdapter extends ArrayAdapter<Child> {
    private final Context context;
    private final List<Child> posts;

    public StableArrayAdapter(Context context, List<Child>posts) {
        super(context, R.layout.content_list,posts);
        this.context = context;
        this.posts = posts;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.content_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secodnline = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(posts.get(position).getData().getTitle());
        secodnline.setText("Comments:"+posts.get(position).getData().getNumComments()+"  Upvotes:"+posts.get(position).getData().getUps()+"  Downvotes:"+posts.get(position).getData().getDowns());
        if(posts.get(position).getData().getThumbnail()==null || (posts.get(position).getData().getThumbnail()=="")){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Picasso.with(context).load(posts.get(position).getData().getThumbnail()).into(imageView);
        }

        //imageView.setImageResource(R.mipmap.ic_launcher);
        // change the icon for Windows and iPhone
        /*String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.ic_menu_gallery);
        } else {
            imageView.setImageResource(R.drawable.ic_menu_manage);
        }*/
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(posts.get(position).getData().getPreview().getImages().get(0).getSource()!=null){
                    Intent i = new Intent(context, FullscreenActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String url = posts.get(position).getData().getPreview().getImages().get(0).getSource().getUrl();
                    i.putExtra("imageurl", url);
                    context.startActivity(i);
                    //Toast.makeText(context,posts.get(position).getData().getPreview().getImages().get(0).getSource().getUrl(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rowView;
    }
}
