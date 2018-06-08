package com.moberd.koolguy.scroll.VolonteersInfo;

public class Volonteer {

    String name;
    boolean come;
    String eattime;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isCome() {
        return come;
    }
    public void setCome(boolean come) {
        this.come = come;
    }
    public String getEattime() {
        return eattime;
    }
    public void setEattime(String eattime) {
        this.eattime = eattime;
    }
    public Volonteer(String name, boolean come, String eattime) {
        super();
        this.name = name;
        this.come = come;
        this.eattime = eattime;
    }

}