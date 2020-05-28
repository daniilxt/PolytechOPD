package com.university.scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.university.scan.SQL.LocalSQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Parsers.Card;
import Parsers.MainTesseract;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyAdapter myAdapter;
    private Uri outputFileUri;
    private Context context;
    private File saveDir;
    private String currentPhotoPath;
    private static int TAKE_PICTURE_REQUEST = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    public static final String MyPREFERENCES = "MyPrefs" ;


    DataBase dataBase = new DataBase();

//    List<String> s1 = new ArrayList<String>();
//    List<String> s2 = new ArrayList<String>();
//    List<Integer> images = new ArrayList<Integer>();
    //List<String> s1 = dataBase.s1;
    //List<String> s2 = dataBase.s2;
    //List<Integer> images = dataBase.images;
    Container delData = new Container();



    // Tess fields
    private MainTesseract tessractParser = new MainTesseract();
    private static final String TAG = MainActivity.class.getSimpleName();
    String TESS_DATA_PATH = "/storage/emulated/0/Android/data/com.university.scan/files/Documents/";
    String TEST_DATA_PATH = "/storage/emulated/0/Android/data/com.university.scan/files/Pictures/vis23.jpg";
    final String[] result = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        saveDir = context.getCacheDir();
        //System.out.println("before");
        //copyFileOrDir("tessData");
        //System.out.println("after");
        copyFile("eng.traineddata");
        copyFile("rus.traineddata");

        //s1 = dataBase.s1;
        //s2 = dataBase.s2;
        //images = dataBase.images;

        recyclerView = findViewById(R.id.recyclerView);
//        s1.add("C++");
//        s1.add("C#");
//        s1.add("Java");
//        s1.add("JavaScript");
//        s1.add("Kotlin");
//        s1.add("Python");
//        s1.add("Ruby");
//        s1.add("Swift");
//        s1.add("TypeScript");
//        s1.add("VisualStudio");
//        s2.add("Takoe");
//        s2.add("J+C++");
//        s2.add("Cool");
//        s2.add("Wtf with strings");
//        s2.add("JavaPro");
//        s2.add("Petuh");
//        s2.add("Ruby");
//        s2.add("Swift");
//        s2.add("Type");
//        s2.add("Ad");
//        images.add(R.drawable.cpp);
//        images.add(R.drawable.c_sharp);
//        images.add(R.drawable.java);
//        images.add(R.drawable.javascript);
//        images.add(R.drawable.kotlin);
//        images.add(R.drawable.python);
//        images.add(R.drawable.ruby);
//        images.add(R.drawable.swift);
//        images.add(R.drawable.typescript);
//        images.add(R.drawable.visual_studio);

        //LocalSQL sql = new LocalSQL(MainActivity.this);
        myAdapter = new MyAdapter(MainActivity.this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;

        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            //delData.setParam(s1.get(position), s2.get(position), images.get(position));
            delData = dataBase.getItem(position);

            //dataBase.delete(position);
            //s1 = dataBase.s1;
            //s2 = dataBase.s2;
            //images = dataBase.images;
            myAdapter.remove(position);
            myAdapter.notifyItemRemoved(position);
            Snackbar.make(recyclerView, delData.getString1(), Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //dataBase.insert(position, delData.getString1(), delData.getString2(), delData.getImage());
                            //s1 = dataBase.s1;
                            //s2 = dataBase.s2;
                            //images = dataBase.images;
                            //myAdapter.insert(delData, position);

                            myAdapter.notifyItemInserted(position);
                        }
                    }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_forever_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton2:
                dispatchTakePictureIntent();

                //Intent intent = new Intent(this, ThirdActivity.class);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap thumbnailBitmap = data.getParcelableExtra("data");
                    // Какие-то действия с миниатюрой
                    //imageView.setImageBitmap(thumbnailBitmap);
                }
            } else {
                // Какие-то действия с полноценным изображением,
                // сохраненным по адресу outputFileUri
                //imageView.setImageURI(outputFileUri);
                System.out.println("picture taken!");
                System.out.println(outputFileUri.getPath());

                //
                Thread thread = new Thread(new TessRun());
                thread.start();
                System.out.println("Path is" + currentPhotoPath);
                //
                StaticVar.var = -1;

                //Intent intent = new Intent(this, SecondActivity.class);
                //intent.putExtra("outputFileUri", outputFileUri);
                //startActivity(intent);

            }
        }
    }

    /*private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            System.out.println("assets: " + Arrays.toString(assets));
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String cachePath = getCacheDir().getAbsolutePath();
                //String fullPath = "/data/data/" + this.getPackageName() + "/" + path;
                String fullPath = getCacheDir().getAbsolutePath() + "/tessData";
                System.out.println(fullPath);
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            //System.out.println(in.read());
            String fullPath = getCacheDir().getAbsolutePath() + "/tessData";

            String newFileName = fullPath + "/" + filename;
            System.out.println(newFileName);
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        System.out.println("1");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            System.out.println("2");
            saveDir = null;
            try {
                saveDir = createImageFile();
                System.out.println(saveDir);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            System.out.println("3");
            // Continue only if the File was successfully created
            if (saveDir != null) {
                System.out.println("4");
                outputFileUri = FileProvider.getUriForFile(this,
                        "com.university.scan.fileprovider",
                        saveDir);
                System.out.println("5");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                System.out.println("6");
            }
        }
    }

    private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                //String fullPath = "/data/data/" + this.getPackageName() + "/" + path;
                String fullPath = Environment.DIRECTORY_DOCUMENTS;
                System.out.println(fullPath);
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            System.out.println(Environment.getDataDirectory().getAbsolutePath());

            //String newFileName = "/data/data/" + this.getPackageName() + "/" + filename;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File folder = new File(storageDir, "tessdata");
            folder.mkdirs();

            //String newFileName = storageDir.getAbsolutePath() + "/" + filename;
            String newFileName = folder.getAbsolutePath() + "/" + filename;
            //String newFileName = Environment.getDataDirectory().getAbsolutePath() + "/" +
             //       this.getPackageName() + "/files/tessdata/"+ filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private class TessRun implements Runnable {
        @Override
        public void run() {
            System.out.println(TESS_DATA_PATH);
            System.out.println("Path is: " + outputFileUri);
            System.out.println("Path is: " + saveDir);

            final CountDownLatch counter = new CountDownLatch(2);
            long start = System.currentTimeMillis();
            ExecutorService pool = Executors.newFixedThreadPool(2);
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    //result[0] = extractText(TEST_DATA_PATH, "rus");
                    result[0] = extractText(currentPhotoPath, "rus");
                    counter.countDown();
                }
            });
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    //  result[1] = extractText(TEST_DATA_PATH,"eng");
                    result[1] = extractText(currentPhotoPath,"eng");
                    counter.countDown();
                }
            });
            try {
                counter.await();
                long stop = System.currentTimeMillis();
                System.out.println("Time used:   "+ (stop-start)/1000);
                runTesseractParser(result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String extractText(final String dataPath, final String lang) {
        TessBaseAPI tessBaseApi = null;
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (tessBaseApi == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        tessBaseApi.init(TESS_DATA_PATH, lang);
        System.out.println("Extract part");

        Log.d(TAG, "Training file loaded");
        tessBaseApi.setImage(new File(dataPath));
        System.out.println("Set good");
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }

    private void runTesseractParser(String[] arr) {
        tessractParser.startParser(arr);
        ArrayList<Card> cardsArray =
                tessractParser.getCardsArray();


        System.out.println("asddddddddddddddddd\n");
        Card card = cardsArray.get(cardsArray.size() -1);
        card.setImage(currentPhotoPath);

        card.setFirstName("adasd");
        LocalSQL sql = new LocalSQL(MainActivity.this);
        long id = sql.addCard(card);

        StaticVar.var = id;

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
