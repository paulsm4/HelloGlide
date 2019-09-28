package com.example.helloglide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int RESULT_LOAD_GALLERY_IMAGE = 1;
    public static final int RESULT_FILE_SELECT = 2;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);
        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = SpaceGalleryActivity.newIntent(MainActivity.this);
                startActivity(galleryIntent);
            }
        });

        Button showGif = (Button) findViewById(R.id.btn_show_gif);
        showGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gifIntent = GifsActivity.newIntent(MainActivity.this);
                startActivity(gifIntent);
            }
        });

        Button browseLocal = (Button) findViewById(R.id.btn_browse_gallery);
        browseLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent browseLocalIntent = BrowseLocalActivity.newIntent(MainActivity.this);
                //startActivity(browseLocalIntent);
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_GALLERY_IMAGE);
            }
        });

        Button browseFilesystem = (Button) findViewById(R.id.btn_browse_filesystem);
        browseFilesystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), RESULT_FILE_SELECT);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(mContext, "Please install a File Manager", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mContext = this;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_LOAD_GALLERY_IMAGE:
                    String clipDescription = data.getClipData().getDescription().toString();
                    Toast.makeText(this, "Selected image: " + clipDescription, Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_FILE_SELECT:
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    //String path = FileUtils.getPath(this, uri);
                    //Log.d(TAG, "File Path: " + path);
                    break;
            }
        }
    }


}
