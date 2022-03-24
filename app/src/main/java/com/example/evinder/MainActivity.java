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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog date_p;
    private ActivityMainBinding binding;
    private Calendar calendar;
    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings,R.id.navigation_add_event)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        SauvegardeFragmentPostsView.posts.add(new Post("https://media.istockphoto.com/photos/group-of-friends-enjoying-drinks-at-bar-picture-id174951485?k=20&m=174951485&s=612x612&w=0&h=VTE7OgRXtA2D13g_GjZBNzckFKpBF97BjzKmFwHTLV8=",
                "Tomas", 2, 25, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        SauvegardeFragmentPostsView.posts.add(new Post("https://images.pexels.com/photos/8455350/pexels-photo-8455350.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "Paul", 4, 22, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        SauvegardeFragmentPostsView.posts.add(new Post("https://barozziveiga.com/media/pages/projects/museum-of-fine-arts/4029461183-1623917242/12_19-10-mcba-ebv_simon-menges_06_hires.jpg",
                "Lucy", 1, 25, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));

        this.initPost();
        this.initListener();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
            System.out.println("INDICE : " + SauvegardeFragmentPostsView.indexView + "\n");
            System.out.println("TEXT : " + SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getPseudo() + "\n");

            String builder = "";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getPseudo() + ", ";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getAge() + " yo, ";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getDistance() + " km,\n";
            builder += SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).getDateActivity();
            infosOwner.setText(builder);
        }
    }

    public void likePost() {
        if(SauvegardeFragmentPostsView.indexView < SauvegardeFragmentPostsView.posts.size())
            SauvegardeFragmentPostsView.posts.get(SauvegardeFragmentPostsView.indexView).setLiked();
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

    public void addPicture(View view) {
        ImageView iv = (ImageView) findViewById(R.id.image_displayed);

        iv.setVisibility(View.VISIBLE);
        SauvegardeFragmentAdd.image = true;
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