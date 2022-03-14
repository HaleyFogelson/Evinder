package com.example.evinder;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.evinder.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog date_p;
    private ActivityMainBinding binding;
    private Calendar calendar;
    private DatePickerDialog picker;
    private TextView infosOwner, infosText;

    private ArrayList<Post> urls = new ArrayList<>();
    private int currentIndice;

    private int touchCount;

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

        this.infosOwner = (TextView) findViewById(R.id.infosOwnerPost);
        this.infosText = (TextView) findViewById(R.id.infosTextPost);

        //init
        this.currentIndice = 0;
        this.touchCount = 0;

        this.urls.add(new Post("https://media.istockphoto.com/photos/group-of-friends-enjoying-drinks-at-bar-picture-id174951485?k=20&m=174951485&s=612x612&w=0&h=VTE7OgRXtA2D13g_GjZBNzckFKpBF97BjzKmFwHTLV8=",
                "Tomas", 2, 25, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        this.urls.add(new Post("https://images.pexels.com/photos/8455350/pexels-photo-8455350.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "Paul", 4, 22, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));
        this.urls.add(new Post("https://barozziveiga.com/media/pages/projects/museum-of-fine-arts/4029461183-1623917242/12_19-10-mcba-ebv_simon-menges_06_hires.jpg",
                "Lucy", 1, 25, "15/02 15h", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id sapien congue, ultricies velit a, mollis orci. Nunc turpis turpis."));

        this.initPost();
        this.initListener();
    }

    private void initListener() {
        ImageView imageView = (ImageView) findViewById(R.id.main_card);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                Context context = getApplicationContext();
                CharSequence text = null;
                Toast toast;

                if(x<225) {
                    text="Dislike";
                }
                else {
                    if (x>775) {
                        text="Like";
                        likePost();
                    }
                }
                int duration = Toast.LENGTH_SHORT;

                if(x<225 || x>775) {
                    if(getTouchCount()==0) {
                        switchImage();
                        if(text!=null) {
                            toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                }

                return true;
            }
        });
    }

    public int getTouchCount() {
        return this.touchCount;
    }

    private void initPost() {
        if(this.currentIndice < this.urls.size()) {
            new DownloadImageTask((ImageView) findViewById(R.id.main_card)).execute(this.urls.get(this.currentIndice).getUrl());
            this.infosText.setText(this.urls.get(this.currentIndice).getTextActivity());

            String builder = "";
            builder+=this.urls.get(this.currentIndice).getPseudo()+", ";
            builder+=this.urls.get(this.currentIndice).getAge()+" yo, ";
            builder+=this.urls.get(this.currentIndice).getDistance()+" km,\n";
            builder+=this.urls.get(this.currentIndice).getDateActivity();
            this.infosOwner.setText(builder);
        }
    }

    public void likePost() {
        if(this.currentIndice < this.urls.size())
            this.urls.get(this.currentIndice).setLiked();
    }

    public void switchImage() {
        this.touchCount++;
        this.currentIndice++;
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

    public void addPicture(View view) {
        ImageView iv = (ImageView) findViewById(R.id.image_displayed);

        iv.setVisibility(View.VISIBLE);
    }

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