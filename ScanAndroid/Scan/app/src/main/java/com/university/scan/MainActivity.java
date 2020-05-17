package com.university.scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyAdapter myAdapter;

    DataBase dataBase = new DataBase();

//    List<String> s1 = new ArrayList<String>();
//    List<String> s2 = new ArrayList<String>();
//    List<Integer> images = new ArrayList<Integer>();
    //List<String> s1 = dataBase.s1;
    //List<String> s2 = dataBase.s2;
    //List<Integer> images = dataBase.images;
    Container delData = new Container();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //System.out.println("before");
        //copyFileOrDir("tessData");
        //System.out.println("after");
        copyFile("eng_traineddata.traineddata");
        copyFile("rus_traineddata.traineddata");

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


        myAdapter = new MyAdapter(this, dataBase);
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
                            myAdapter.insert(delData, position);
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
                Intent intent = new Intent(this, ThirdActivity.class);
                startActivity(intent);
                break;
            default:
                break;
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
}
