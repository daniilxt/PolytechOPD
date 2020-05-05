package com.university.scan;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private List<String> s1 = new ArrayList<String>();
    private List<String> s2 = new ArrayList<String>();
    private List<Integer> images = new ArrayList<Integer>();

    public DataBase(){
        s1.add("C++");
        s1.add("C#");
        s1.add("Java");
        s1.add("JavaScript");
        s1.add("Kotlin");
        s1.add("Python");
        s1.add("Ruby");
        s1.add("Swift");
        s1.add("TypeScript");
        s1.add("VisualStudio");
        s2.add("Takoe");
        s2.add("J+C++");
        s2.add("Cool");
        s2.add("Wtf with strings");
        s2.add("JavaPro");
        s2.add("Petuh");
        s2.add("Ruby");
        s2.add("Swift");
        s2.add("Type");
        s2.add("Ad");
        images.add(R.drawable.cpp);
        images.add(R.drawable.c_sharp);
        images.add(R.drawable.java);
        images.add(R.drawable.javascript);
        images.add(R.drawable.kotlin);
        images.add(R.drawable.python);
        images.add(R.drawable.ruby);
        images.add(R.drawable.swift);
        images.add(R.drawable.typescript);
        images.add(R.drawable.visual_studio);
    }

    public void delete(int position){
        s1.remove(position);
        s2.remove(position);
        images.remove(position);
    }

    public void insert(int position, String st1, String st2, Integer in){
        s1.add(position, st1);
        s2.add(position, st2);
        images.add(position, in);
    }

    public List<String> getS1() {
        return s1;
    }

    public List<String> getS2() {
        return s2;
    }

    public List<Integer> getImages() {
        return images;
    }

    public Container getItem(int position) {
        Container con = new Container();
        con.setParam(s1.get(position), s2.get(position), images.get(position));
        return con;
    }

}
