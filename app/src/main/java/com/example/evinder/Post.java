package com.example.evinder;

public class Post {
    private String url;
    private boolean isLiked = false;
    private String pseudo;
    private int distance, age;
    private String dateActivity;
    private String textActivity;

    public Post(String u, String pseudo, int distance, int age, String dateActivity, String textActivity){
        this.url = u;
        this.pseudo = pseudo;
        this.distance = distance;
        this.age = age;
        this.dateActivity = dateActivity;
        this.textActivity = textActivity;
    }

    public int getAge() {
        return age;
    }

    public int getDistance() {
        return distance;
    }

    public String getDateActivity() {
        return dateActivity;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getTextActivity() {
        return textActivity;
    }

    public String getUrl() {
        return this.url;
    }

    public void setLiked() {
        this.isLiked = true;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateActivity(String dateActivity) {
        this.dateActivity = dateActivity;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setTextActivity(String textActivity) {
        this.textActivity = textActivity;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
