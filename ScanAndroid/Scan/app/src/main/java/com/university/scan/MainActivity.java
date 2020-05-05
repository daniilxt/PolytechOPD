package com.university.scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyAdapter myAdapter;

    DateBase dateBase = new DateBase();

//    List<String> s1 = new ArrayList<String>();
//    List<String> s2 = new ArrayList<String>();
//    List<Integer> images = new ArrayList<Integer>();
    List<String> s1 = dateBase.s1;
    List<String> s2 = dateBase.s2;
    List<Integer> images = dateBase.images;
    Container delData = new Container();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = dateBase.s1;
        s2 = dateBase.s2;
        images = dateBase.images;

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


        myAdapter = new MyAdapter(this, s1, s2, images);
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
            delData.setParam(s1.get(position), s2.get(position), images.get(position));

            dateBase.delete(position);
            s1 = dateBase.s1;
            s2 = dateBase.s2;
            images = dateBase.images;
            myAdapter.remove(position);
            myAdapter.notifyItemRemoved(position);
            Snackbar.make(recyclerView, delData.getString1(), Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dateBase.insert(position, delData.getString1(), delData.getString2(), delData.getImage());
                            s1 = dateBase.s1;
                            s2 = dateBase.s2;
                            images = dateBase.images;
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
}
