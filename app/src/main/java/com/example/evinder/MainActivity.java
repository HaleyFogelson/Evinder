package com.example.evinder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.evinder.databinding.ActivityMainBinding;
import com.example.evinder.ui.login.LoginActivity;
import com.example.evinder.ui.register.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog date_p;
    private ActivityMainBinding binding;
    private Calendar calendar;
    private DatePickerDialog picker;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        db = AppDatabase.getInstance(getApplicationContext());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings,R.id.navigation_add_event)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /**
        SauvegardeFragmentPostsView.posts.add(new Post("https://images.pexels.com/photos/8455350/pexels-photo-8455350.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "Paul", 4, 22, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        SauvegardeFragmentPostsView.posts.add(new Post("https://barozziveiga.com/media/pages/projects/museum-of-fine-arts/4029461183-1623917242/12_19-10-mcba-ebv_simon-menges_06_hires.jpg",
                "Lucy", 1, 25, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        */

        //INIT USER
        this.initUser();

        List<Events> ev = db.eventsDao().getAllEvents();

        for (Events e : ev) {
            SauvegardeFragmentPostsView.posts.add(new Post(e.getEvent_id(), e.getEventPic(), e.getName(), e.getLocation(), 21, e.getDate()+"", e.getDescription(),e.getCreator()));
        }

        this.initPost();
        this.initListener();
        this.initPostILiked();

        System.out.println("AFFICHAGE ASSOCIATIONS");
        ArrayList<Associations> as = (ArrayList<Associations>) db.associationsDao().getAllAssociations();
        for (Associations a : as) {
            System.out.println("ASSO : "+a.event_id_assoc+"/"+a.user_id_assoc);
        }
        System.out.println("FIN ASSOS");


        System.out.println("\nPOST I LIKED :");
        for(Post p:SauvegardeFragmentPostLiked.postsILiked) {
            System.out.println("POST : "+p.getTextActivity());
        }
        System.out.println("\nEND \n");

    }

    public void initPostILiked() {
        List<Associations> associationsILiked = db.associationsDao().getAssociationsForUser(StoreConnection.connectedUser.getUser_id());

        ArrayList<Events> evILiked = new ArrayList<>();
        for(Associations a:associationsILiked) {
            int evId = a.getEvent_id_assoc();
            Events e = db.eventsDao().getEventById(evId);
            SauvegardeFragmentPostLiked.postsILiked.add(new Post(e.getEvent_id(), e.getEventPic(), e.getName(), e.getLocation(), 21, e.getDate()+"", e.getDescription(),e.getCreator()));
        }
    }

    public ArrayList<Events> getEventsWithCreatorId(int id) {
        ArrayList<Events> events = new ArrayList<>();

        events = (ArrayList<Events>) db.eventsDao().getEventsByCreator(id);

        return events;
    }

    public void initUser() {
        //Simulate with Paul Bridier -> need to change when a user will really be connected
        Users currentUser = null;
        try {
            currentUser = db.usersDao().getUsersByName("Paul Bridier").get(0);
            StoreConnection.connectedUser = currentUser;
        }catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Unfounded");
        }catch(Exception e){
            System.out.println("Failed");
        }
    }

    public Users getUsersWithId(int id) {
        Users u = db.usersDao().getUserById(id);
        return u;
    }

    public void initListener() {
        ImageView imageView = (ImageView) findViewById(R.id.main_card);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    Context context = getApplicationContext();
                    CharSequence text = null;
                    Toast toast;

                    if (x < 225) {
                        text = "Dislike";
                    } else {
                        if (x > 775) {
                            text = "Like";
                            likePost();
                        }
                    }
                    int duration = Toast.LENGTH_SHORT;

                    if (x < 225 || x > 775) {
                        System.out.println("CLICK COTE");
                        switchImage();
                        if (text != null) {
                            toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                }

                return true;
            }
        });
    }

    public void disconnect(View view) {
        StoreConnection.connectedUser = null;
        StoreConnection.connectedUserString = null;
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void initPost() {
        if(SauvegardeFragmentPostsView.indexView >= SauvegardeFragmentPostsView.posts.size()) {
            CardView cv = (CardView) findViewById(R.id.cardViewContainer);
            cv.setVisibility(View.GONE);

            TextView tv = (TextView) findViewById(R.id.seenEverything);
            tv.setVisibility(View.VISIBLE);
        }
        else {
            TextView infosOwner, infosText;
            infosOwner = (TextView) findViewById(R.id.infosOwnerPost);
            infosText = (TextView) findViewById(R.id.infosTextPost);
            new DownloadImageTask((ImageView) findViewById(R.id.main_card)).execute(SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getUrl());
            infosText.setText(SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getTextActivity());
            //System.out.println("INDICE : " + SauvegardeFragmentPostsView.indexView + "\n");
            //System.out.println("TEXT : " + SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getPseudo() + "\n");

            String builder = "";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getPseudo() + ", ";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getAge() + " yo, ";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getLocation() + "\n";
            Long ts = Long.parseLong(SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getDateActivity());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            builder += formatter.format(ts);
            infosOwner.setText(builder);
        }
    }

    public ArrayList<Users> getParticipantsWithEvents(int events_id) {
        ArrayList<Users> participants = new ArrayList<>();
        ArrayList<Associations> asso_participants;
        asso_participants = (ArrayList<Associations>) db.associationsDao().getUsersForAssociation(events_id);

        for(Associations a:asso_participants) {
            Users u = db.usersDao().getUserById(a.getUser_id_assoc());
            participants.add(u);
        }

        return participants;
    }

    public void removeEvent(int id) {
        Events eventRemoved =  db.eventsDao().getEventById(id);
        db.eventsDao().delete(eventRemoved);
    }

    public void removeAssociation(int id) {
        System.out.println("JE VEUX REMOVE : "+id+"/"+StoreConnection.connectedUser.getUser_id());
        System.out.println("AFFICHAGE ASSOCIATIONS AVANT REMOVE");
        ArrayList<Associations> as = (ArrayList<Associations>) db.associationsDao().getAllAssociations();
        for (Associations a : as) {
            System.out.println("ASSO : "+a.event_id_assoc+"/"+a.user_id_assoc);
        }
        System.out.println("FIN ASSOS AVANT REMOVE");

        //Change by using the id of the person who is connected
        try {
            db.associationsDao().delete(new Associations(StoreConnection.connectedUser.getUser_id(), id));
        }catch(android.database.sqlite.SQLiteConstraintException sE) {
            System.out.println("already discarded");
        }

        System.out.println("AFFICHAGE ASSOCIATIONS APRES REMOVE");
        ArrayList<Associations> as2 = (ArrayList<Associations>) db.associationsDao().getAllAssociations();
        for (Associations a : as2) {
            System.out.println("ASSO : "+a.user_id_assoc+"/"+a.event_id_assoc);
        }
        System.out.println("FIN ASSOS APRES REMOVE");
    }

    public void likePost() {
        if(SauvegardeFragmentPostsView.indexView < SauvegardeFragmentPostsView.posts.size())
            SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).setLiked(db);
    }

    public void switchImage() {
        SauvegardeFragmentPostsView.indexView++;
        ImageView imageView = (ImageView) findViewById(R.id.main_card);
        imageView.setImageResource(0);

        this.initPost();
    }

    public void changeDate(View view) {
        Button b = (Button) findViewById(R.id.edit_date);

        this.calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("DAY : "+day);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        picker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String s = i2+"/"+(i1+1)+"/"+i;
                b.setText(s);
            }
        }, year, month, day);

        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        picker.show();
    }

    public void changeDateBirthday(View view) {
        Button b = (Button) findViewById(R.id.edit_date);

        this.calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("DAY : "+day);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        picker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String s = i2+"/"+(i1+1)+"/"+i;
                b.setText(s);
            }
        }, year, month, day);
        picker.show();
    }


    public void confirmAddEvent(View view) throws ParseException {
        System.out.println("ENTRE DANS LA FONCTION");
        /**
         * EVENTS :
         * event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
         * 	name TEXT,
         * 	description TEXT,
         * 	date INTEGER NOT NULL,
         * 	creator INTEGER NOT NULL,
         * 	eventPic TEXT,
         * 	location TEXT
         */

        EditText et_name = (EditText) findViewById(R.id.nameActivity);
        EditText et_desc = (EditText) findViewById(R.id.descriptionEvent);
        EditText et_image = (EditText) findViewById(R.id.image_url);

        Button b = (Button) findViewById(R.id.edit_date);
        String date = b.getText().toString();
        EditText locationEt = (EditText) findViewById(R.id.location);
        String location = locationEt.getText().toString();
        String imageUrl = et_image.getText().toString();

        Date dateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(date);

        //need to modify "date"
        //need to modify "creator"
        //need to modify id
        int id = 500 +(int)(Math.random() * ((5000 - 500) + 1));
        Events events = new Events(id,et_name.getText().toString(), et_desc.getText().toString(), dateFormat.getTime(), StoreConnection.connectedUser.getUser_id(), imageUrl,location);

        db.eventsDao().insert(events);
    }

    public void saveChanges(View view) {
        EditText username, emailadress, phonenumber, birthday;

        String name, address, phone, birth;

        name = ((EditText)findViewById(R.id.firstnameuser)).getText().toString();
        address = ((EditText)findViewById(R.id.emailaddress)).getText().toString();
        phone = ((EditText)findViewById(R.id.phonenumber)).getText().toString();
        birth = ((EditText)findViewById(R.id.ageuser)).getText().toString();

        if(name.equals("") || address.equals("") || phone.equals("") || birth.equals("")) {
            return;
        }

        //no problem :
        StoreConnection.connectedUser.setName(name);
        StoreConnection.connectedUser.setEmail(address);
        StoreConnection.connectedUser.setWhatsapp(phone);

        try {
            StoreConnection.connectedUser.setAge(Integer.parseInt(birth));
        }catch (Exception e){
            System.out.println("Not a number");
        }

        try {
            db.usersDao().update(StoreConnection.connectedUser);
        }catch (Exception e){
            System.out.println("Database error !");
        }

    }


    //Taken from https://stackoverflow.com/questions/5776851/load-image-from-url/37612481#37612481
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}