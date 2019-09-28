package com.example.helloglide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.appcompat.app.AppCompatActivity;

public class SpacePhotoActivity extends AppCompatActivity {

    private static final String TAG = "SpacePhotoActivity";

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    private ImageView mImageView;

    public static Intent newIntent(Context packageContext, SpacePhoto spacePhoto) {
        Intent intent = new Intent(packageContext, SpacePhotoActivity.class);
        intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
        return intent;
    }

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
