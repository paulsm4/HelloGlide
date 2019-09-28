9.25.2019:
---------
* HelloGlide/ImportToGallery:
https://code.tutsplus.com/tutorials/code-an-image-gallery-android-app-with-glide--cms-28207, Chike Mgbemena
  - References:
    - https://github.com/bumptech/glide
      <= Current version v4.9.0; tutorial uses Glide3
    - http://bumptech.github.io/glide/doc/download-setup.html
      <= Must be compiled against SDK 27 or higher; uses SDK 27 support library (vs. "androidx"?)
    - http://bumptech.github.io/glide/doc/getting-started.html

  - Downloaded and tried building as-is:
https://github.com/tutsplus/code-an-image-gallery-with-glide
    - A/S > Open > d:\paul\proj\HelloGlide\code-an-image-gallery-android-app-with-glide
      <= Error: failed to find Build Tools 25.02
      - toplevel build.gradle:
        <= Changed dependencies to classpath 'com.android.tools.build:gradle:3.5.0'
===================================================================================================
9.26.2019:
---------
* Build A/S project from scratch, copy/paste tutorial code/layouts as needed
  - Links:
https://code.tutsplus.com/tutorials/code-an-image-gallery-android-app-with-glide--cms-28207

  - PROJECT:
     RobertPC >  A/S > New Project > Empty Activity >
       Name= HelloGlide, Pkg= com.example.helloglide, Save location= c:\paul\proj\HelloGlide, Min API= 19 (KitKat)

  - build.gradle:
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    ...
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    // Skip this if you don't want to use integration libraries or configure Glide.
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'    
    ...  <= Tutorial uses Glide 3; BoxPik doesn't have a Glide dependency at all ...

  - AndroidManifest.xml:
<uses-permission android:name="android.permission.INTERNET" />


  - layouts/activity_main.xml:
    <LinearLayout
      <Button
        id="@+id/btn_show_gallery  "
      <Button
        id="@+id/btn_show_gif"

  - layouts/item_photo.xml:
    <LinearLayout
      <ImageView
        id="@+id/iv_photo" adjustViewBounds="true" layout_height="200dp" scaleType="centerCrop"

  - Create "model": class SpacePhoto.
    <= Implements "Parcelable", for efficient transfer of data from SpaceGalleryActivity to SpacePhotoActivity.

  - Create new SpaceGalleryActivity.java'

  - Create recyclerview adapter:
    MainActivity.java:
public class MainActivity extends AppCompatActivity {
    // ... 
    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {
       ...

  - Elaborate "load images from URL":
    SpaceGalleryActivity.java
       @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            SpacePhoto spacePhoto = mSpacePhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)
                    .load(spacePhoto.getUrl())
                    .placeholder(R.drawable.ic_cloud_off_red)
                    .into(imageView);
        }
     - NOTES:
       - with(Context context): we begin the load process by first passing our context into the with() method. 
       - load(String string): the image source is specified as either a directory path, a URI, or a URL.
       - placeholder(int resourceId): a local application resource id, preferably a drawable, 
         that will be a placeholder until the image is loaded and displayed.
       - into(ImageView imageView): the target image view into which the image will be placed.
       - Be aware that Glide can also load local images. Just pass either the Android resource id,
         the file path, or a URI as an argument to the load() method. 

  - Initializing the adapter:
    SpaceGalleryActivity.java:
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
 
          RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
          RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
          recyclerView.setHasFixedSize(true);
          recyclerView.setLayoutManager(layoutManager);
 
          ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, SpacePhoto.getSpacePhotos());
          recyclerView.setAdapter(adapter);
      }

  - Create new SpacePhotoActivity

  - Create Detail Layout: activity_photo_detail.xml:

  - MISSING PIECES:
    - R.drawable.ic_cloud_off_red  Cannot resolve symbol
    <= Copied drawables/* from .zip to new project

    - layouts/activity_space_gallery.xml
   
    - GifsActivity.java, layouts/activity_gifs.xml

    - AndroidManifest.xml:
        <activity android:name=".SpaceGalleryActivity" />
        <activity android:name=".GifsActivity" />

        <activity android:name=".SpacePhotoActivity" />


    - MainActivity.java:
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);
        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(MainActivity.this, SpaceGalleryActivity.class);
                startActivity(galleryIntent);
            }
        });

        Button showGif = (Button) findViewById(R.id.btn_show_gif);
        showGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gifIntent = new Intent(MainActivity.this, GifsActivity.class);
                startActivity(gifIntent);
            }
        });

    }

    - AndroidManifest.xml:
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.helloglide">
        ...
        <activity android:name=".SpaceGalleryActivity" />
        <activity android:name=".GifsActivity" />
        <activity android:name=".SpacePhotoActivity" />


    - Glide unresolveds:
https://bumptech.github.io/glide/doc/migrating.html
      - Glide.with()...
         .asBitmap()  // Cannot resolve method
         <= Simply needed to change the order: "asBitmap()" first, *THEN* load()" afterwards...

      - Glide.with()...
         .diskCacheStrategy(DiskCacheStrategy.SOURCE)  // Cannot resolve "SOURCE"
https://stackoverflow.com/questions/46349657/difference-diskcachestrategy-in-glide-v4
https://futurestud.io/tutorials/glide-caching-basics
        - Glide 3.x & 4.x: DiskCacheStrategy.NONE caches nothing
        - Glide 4.x: DiskCacheStrategy.DATA, Glide 3.x: DiskCacheStrategy.SOURCE caches only the original full-resolution image.
        - Glide 4.x: DiskCacheStrategy.RESOURCE Glide 3.x: DiskCacheStrategy.RESULT caches only the final image, after reducing the resolution (and possibly transformations) (default behavior of Glide 3.x)
        - Glide 4.x only: DiskCacheStrategy.AUTOMATIC intelligently chooses a cache strategy based on the resource (default behavior of Glide 4.x)
        - Glide 3.x & 4.x: DiskCacheStrategy.ALL caches all versions of the image

  - A/S > Debug tutorial:
    - Build => OK
    - Gallery => OK
    - Click on gallery image => OK
     
===================================================================================================
9.26.2019:
---------
*  Checked initial version in to Git, GitHub:
https://help.github.com/en/articles/adding-an-existing-project-to-github-using-the-command-line
    1. Create a new repository on GitHub => https://github.com/paulsm4/HelloGlide.git
    2. Create local repository, add files, commit
    3. Git bash: Add remote; push local
         git remote add origin https://github.com/paulsm4/HelloGlide.git
         git remote -v  # Verify
         git push origin master
    4. Verify GitHub project in browser:
         https://github.com/paulsm4/HelloGlide

*  Added "public static Intent newIntent()" to all activities:
   EXAMPLE:
   - SpacePhotoActivity.java:
     -----------------------
public class SpacePhotoActivity extends AppCompatActivity {
    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    ...
    public static Intent newIntent(Context packageContext, SpacePhoto spacePhoto) {
        Intent intent = new Intent(packageContext, SpacePhotoActivity.class);
        intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
        return intent;
    }

   - SpaceGalleryActivity.java:
     --------------------------
public class SpaceGalleryActivity extends AppCompatActivity {
    ...
    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {
        ...
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ...
            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    SpacePhoto spacePhoto = mSpacePhotos[position];
                    Intent intent = SpacePhotoActivity.newIntent(mContext, spacePhoto);
                    startActivity(intent);
                }

* HelloGlide (EVE):
  - HelloGlide: Added "Browse Android Gallery" button =>
ClipData { text/uri-list {U:content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F32/ORIGINAL/NONE/562073873} }

  - How to save an image to Android Gallery:
https://stackoverflow.com/questions/20859584/how-to-save-image-in-android-gallery
    <= Need to save image from wherever it exists to the "MediaStore provider"

  - HelloGlide: Added "Browse File System" button:
https://stackoverflow.com/questions/7856959/android-file-chooser
   
  << OK: at this point we have all the pieces we need for an "import image file into Android Gallery" if we need one... >>

