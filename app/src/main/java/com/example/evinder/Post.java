package com.example.evinder;

public class Post {
    private String url;
    private boolean isLiked = false;
    private String pseudo;
    private int age;
    String location;
    private String dateActivity;
    private String textActivity;
    private int id;
    private int creator;

    public Post(int id, String u, String pseudo, String location, int age, String dateActivity, String textActivity, int creator){
        this.id = id;
        this.url = u;
        this.pseudo = pseudo;
        this.location = location;
        this.age = age;
        this.dateActivity = dateActivity;
        this.textActivity = textActivity;
        this.creator = creator;
    }
    public int getId() {return this.id;}

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
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

    public int getCreator() { return this.creator;}

    public String getUrl() {
        return this.url;
    }

    public void setLiked(AppDatabase db) {
        System.out.println(this.id+" has been liked <3");
        this.isLiked = true;

        try {
            db.associationsDao().insert(new Associations(StoreConnection.connectedUser.getUser_id(), this.id));
        }catch(android.database.sqlite.SQLiteConstraintException sE) {
            System.out.println("already liked");
        }
        //maybe change that part, in order to not have the same component twice in the list
        SauvegardeFragmentPostLiked.postsILiked.add(this);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateActivity(String dateActivity) {
        this.dateActivity = dateActivity;
    }

    public void setLocation(String location) {
        this.location = location;
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
