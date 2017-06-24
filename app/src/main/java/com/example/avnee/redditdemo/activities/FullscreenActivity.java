package com.example.avnee.redditdemo.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.avnee.redditdemo.R;
import com.squareup.picasso.Picasso;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    @SuppressLint("NewApi")


    //ImageView imgDisplay;
    private PhotoDraweeView mPhotoDraweeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fullscreen);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("imageurl");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("imageurl");
        }




        Button btnClose;

        mPhotoDraweeView = (PhotoDraweeView) findViewById(R.id.photo_drawee_view);
        mPhotoDraweeView.setPhotoUri(Uri.parse(newString));
        //imgDisplay = (ImageView) findViewById(R.id.imgDisplay);

        btnClose = (Button) findViewById(R.id.btnClose);
        //Picasso.with(this).load(newString).into(imgDisplay);

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FullscreenActivity.this.finish();
            }
        });




    }

}