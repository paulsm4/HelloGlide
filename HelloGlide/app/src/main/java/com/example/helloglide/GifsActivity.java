package com.example.helloglide;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class GifsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifs);

        ImageView gifImageView = (ImageView) findViewById(R.id.iv_gif);

        Glide.with(this)
                .asBitmap()
                .load("http://i.imgur.com/Vth6CBz.gif")
                .placeholder(R.drawable.ic_cloud_off_red)
                .error(R.drawable.ic_cloud_off_red)
                .into(gifImageView);
    }
}
