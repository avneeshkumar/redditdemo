package com.atarashi.picreddit.activities;

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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.atarashi.picreddit.R;
import com.atarashi.picreddit.utils.redditjsonclass.Child;
import com.atarashi.picreddit.utils.redditjsonclass.Image;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by avnee on 4/2/2017.
 */

public class StableArrayAdapter extends ArrayAdapter<Child> implements ListAdapter {
    private final Context context;
    private final List<Child> posts;

    public StableArrayAdapter(Context context, List<Child>posts) {
        super(context, R.layout.content_list,posts);
        this.context = context;
        this.posts = posts;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //System.out.println("getView " + position + " " + convertView);
        ViewHolder holder = null;
        if(convertView==null)
        {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.content_list, parent, false);
                holder = new ViewHolder();
                holder.firstline = (TextView) convertView.findViewById(R.id.firstLine);
                holder.secondline = (TextView) convertView.findViewById(R.id.secondLine);
                //holder.imageview = (ImageView) convertView.findViewById(R.id.icon);
                holder.imagepreview = (ImageView) convertView.findViewById(R.id.imagepreview);
                convertView.setTag(holder);



        }
        else{
            holder = (ViewHolder)convertView.getTag();

        }


        if(!(posts.get(position)==null)){
            holder.firstline.setText(posts.get(position).getData().getTitle());
            holder.secondline.setText("Comments:"+posts.get(position).getData().getNumComments()+"  Upvotes:"+posts.get(position).getData().getUps()+"  Downvotes:"+posts.get(position).getData().getDowns());
        /*if(posts.get(position).getData().getThumbnail()==null || (posts.get(position).getData().getThumbnail()=="")){
            holder.imageview.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Picasso.with(context).load(posts.get(position).getData().getThumbnail()).into(holder.imageview);
        }*/
            for (Image allimage:posts.get(position).getData().getPreview().getImages()) {

                if(!allimage.getSource().getUrl().contains(".gif")){

                    Picasso.with(context).load(allimage.getSource().getUrl()).into(holder.imagepreview);
                    break;
                }
            }


            //imageView.setImageResource(R.mipmap.ic_launcher);
            // change the icon for Windows and iPhone
        /*String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.ic_menu_gallery);
        } else {
            imageView.setImageResource(R.drawable.ic_menu_manage);
        }*/
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(posts.get(position).getData().getPreview()!=null){
                        Intent i = new Intent(context, FullscreenActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //String url = posts.get(position).getData().getPreview().getImages().get(0).getSource().getUrl();
                        //List<Image> url = posts.get(position).getData().getPreview().getImages();
                        for (Image allimage:posts.get(position).getData().getPreview().getImages()) {

                            if(!allimage.getSource().getUrl().contains(".gif")){

                                i.putExtra("imageurl", allimage.getSource().getUrl());
                                context.startActivity(i);
                                break;
                            }
                        }

                        //Toast.makeText(context,posts.get(position).getData().getPreview().getImages().get(0).getSource().getUrl(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return convertView;
    }


    public static class ViewHolder {
        public TextView firstline;
        public TextView secondline;
        public ImageView imagepreview;
    }


}
