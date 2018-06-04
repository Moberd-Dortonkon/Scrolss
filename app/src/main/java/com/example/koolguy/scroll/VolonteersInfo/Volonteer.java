package com.example.koolguy.scroll.VolonteersInfo;

public class Volonteer {

    String name;
    boolean come;
    public boolean isEat() {
        return eat;
    }
    public void setCome(boolean come) {
        this.come = come;
    }
    boolean eat;
    public void setName(String name) {
        this.name = name;
    }
    public void setEat(boolean eat) {
        this.eat = eat;
    }
    public Volonteer(String n,boolean c,boolean e)
    {

        name = n;
        come = c;
        eat = e;
    }
    public String getName() {
        return name;
    }
    public boolean isCome() {
        return come;
    }
}