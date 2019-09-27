package com.example.helloglide;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.appcompat.app.AppCompatActivity;

public class SpacePhotoActivity extends AppCompatActivity {
    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        mImageView = (ImageView) findViewById(R.id.image);
        SpacePhoto spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)
                .asBitmap()
                .load(spacePhoto.getUrl())
                .error(R.drawable.ic_cloud_off_red)
                //.diskCacheStrategy(DiskCacheStrategy.SOURCE) // Glide 3.x
                .diskCacheStrategy(DiskCacheStrategy.DATA) // Glide 4.x
                .into(mImageView);
    }
}
