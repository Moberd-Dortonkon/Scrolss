package com.example.koolguy.scroll.VolonteersInfo;

public class Volonteer {
    String name;
    boolean come;
    boolean eat;

    public Volonteer(String n, boolean c, boolean e)
    {
        name = n;
        come = c;
        eat = e;
    }

    public boolean isCome() {
        return come;
    }

    public void setCome(boolean come) {
        this.come = come;
    }

    public boolean isEat() {
        return eat;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
