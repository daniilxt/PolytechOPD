package com.university.scan;

public class Container {
    private String string1;
    private String string2;
    private Integer image;

    public Container()
    {

    }

    public void setParam(String s1, String s2, Integer im)
    {
        string1 = s1;
        string2 = s2;
        image = im;
    }

    public Integer getImage() {
        return image;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }
}
