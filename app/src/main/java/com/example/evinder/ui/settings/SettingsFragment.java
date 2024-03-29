package com.example.evinder.ui.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.Events;
import com.example.evinder.MainActivity;
import com.example.evinder.Post;
import com.example.evinder.R;
import com.example.evinder.SauvegardeFragmentPostLiked;
import com.example.evinder.StoreConnection;
import com.example.evinder.Users;
import com.example.evinder.databinding.FragmentSettingsBinding;
import com.example.evinder.ui.participants.ParticipantsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingsBinding binding;
    private HashMap<Button, Integer> buttonsRemove;
    private HashMap<Button, Integer> buttonsParticipants;
    private LayoutInflater inflater;
    private Button btnBack;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        this.inflater = inflater;
        View root = binding.getRoot();
        return root;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initLayoutSettings();
    }

    private void initLayoutSettings() {
        TextView tv = ((MainActivity)getActivity()).findViewById(R.id.title_settings);
        tv.setText("Events posted");

        //get the posts created by the current user
        int current_id = StoreConnection.connectedUser.getUser_id();
        ArrayList<Events> evs = ((MainActivity)getActivity()).getEventsWithCreatorId(current_id);
        //sort :
        Collections.sort(evs,new Comparator<Events>() {
            @Override
            public int compare(Events s1, Events s2) {
                return Long.compare(s1.getDate(),s2.getDate());
            }
        });

        this.buttonsRemove = new HashMap<>();
        this.buttonsParticipants = new HashMap<>();
        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_posted);


        for(Events e:evs) {
            TextView tvNew = new TextView(((MainActivity)getActivity()).getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.setMargins(30,30,30,30);
            tvNew.setLayoutParams(params);
            tvNew.setGravity(Gravity.LEFT);

            String evName = e.getName();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(e.getDate());
            String description = e.getDescription();

            String text = evName+"\n"+date+"\n\n"+description;
            tvNew.setText(text);
            //tvNew.setBackgroundColor(Color.WHITE);
            tvNew.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
            tvNew.setPadding(20,20,20,20);
            tvNew.setTextSize(22);
            ll.addView(tvNew);

            //We want to link the event id with the associated buttons (remove and participants)
            LinearLayout parent = new LinearLayout(((MainActivity)getActivity()).getApplicationContext());
            parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            parent.setOrientation(LinearLayout.HORIZONTAL);
            parent.setGravity(Gravity.CENTER_HORIZONTAL);

            //size (need to convert from dps to pixel)
            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (140 * scale + 0.5f);

            //participants button
            Button btnParticipants = new Button(((MainActivity)getActivity()).getApplicationContext());
            btnParticipants.setText("Particpants");
            btnParticipants.setTextColor(Color.WHITE);
            btnParticipants.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
            btnParticipants.setBackgroundTintList(((MainActivity)getActivity()).getColorStateList(R.color.cyan));
            LinearLayout.LayoutParams paramsParticipants = new LinearLayout.LayoutParams(pixels, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsParticipants.setMargins(10,10,10,10);
            btnParticipants.setLayoutParams(paramsParticipants);
            btnParticipants.setGravity(Gravity.CENTER_HORIZONTAL);
            btnParticipants.setPadding(30,30,30,30);
            btnParticipants.setOnClickListener(this);

            //remove button
            Button btnShow = new Button(((MainActivity)getActivity()).getApplicationContext());
            btnShow.setText("Remove");
            btnShow.setTextColor(Color.WHITE);
            btnShow.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
            btnShow.setBackgroundTintList(((MainActivity)getActivity()).getColorStateList(R.color.red));
            LinearLayout.LayoutParams paramsRemove = new LinearLayout.LayoutParams(pixels, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsRemove.setMargins(10,10,10,10);
            btnShow.setLayoutParams(paramsRemove);
            btnShow.setGravity(Gravity.CENTER_HORIZONTAL);
            btnShow.setPadding(30,30,30,30);
            btnShow.setOnClickListener(this);

            parent.addView(btnParticipants);
            parent.addView(btnShow);

            ll.addView(parent);

            //links
            this.buttonsRemove.put(btnShow,e.getEvent_id());
            this.buttonsParticipants.put(btnParticipants, e.getEvent_id());
        }
        //Add an empty layout with a margin bottom to ensure that the user can see all the events
        LinearLayout empty = new LinearLayout(((MainActivity)getActivity()).getApplicationContext());
        LinearLayout.LayoutParams paramsEmpty = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsEmpty.setMargins(0,0,0,180);
        empty.setLayoutParams(paramsEmpty);
        empty.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(empty);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void remove(int id) {
        ((MainActivity)getActivity()).removeEvent(id);

        buttonsRemove = null;

        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_posted);
        ll.removeAllViews();

        initLayoutSettings();
    }

    @Override
    public void onClick(View view) {
        for (Map.Entry mapentry : buttonsRemove.entrySet()) {
            if(mapentry.getKey().equals(view)) {
                remove((Integer) mapentry.getValue());
            }
        }

        for(Map.Entry mapentry: buttonsParticipants.entrySet()) {
            if(mapentry.getKey().equals(view)) {
                int idEvent = (Integer)mapentry.getValue();
                ArrayList<Users> participants = ((MainActivity)getActivity()).getParticipantsWithEvents(idEvent);

                this.clearPanel();
                this.initPanelViewParticipants(idEvent);
            }
        }

        if(this.btnBack.equals(view)) {
            this.clearPanel();
            this.initLayoutSettings();
        }
    }

    private void clearPanel() {
        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_posted);
        ll.removeAllViews();
    }

    private void initPanelViewParticipants(int idEvent) {
        String eventName = ((MainActivity)getActivity()).getEventNameFromId(idEvent);
        TextView tv = ((MainActivity)getActivity()).findViewById(R.id.title_settings);
        tv.setText("Participants for "+eventName);
        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_posted);

        ArrayList<Users> participants = ((MainActivity)getActivity()).getParticipantsWithEvents(idEvent);

        this.btnBack = new Button(((MainActivity)getActivity()).getApplicationContext());
        btnBack.setText("Back");
        btnBack.setTextColor(Color.WHITE);
        btnBack.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
        btnBack.setBackgroundTintList(((MainActivity)getActivity()).getColorStateList(R.color.red));
        btnBack.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnBack.setGravity(Gravity.CENTER_HORIZONTAL);
        btnBack.setPadding(30,30,30,30);
        btnBack.setOnClickListener(this);
        ll.addView(btnBack);

        for(Users u:participants) {
            System.out.println(u.getName());

            TextView tvNew = new TextView(((MainActivity) getActivity()).getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30, 30, 30, 30);
            tvNew.setLayoutParams(params);
            tvNew.setGravity(Gravity.LEFT);

            tvNew.setText(u.getName() + "\n" + u.getAge() + "\n" + u.getWhatsapp());
            tvNew.setTextSize(22);
            tvNew.setBackground(((MainActivity) getActivity()).getDrawable(R.drawable.round_corners));
            tvNew.setPadding(20, 20, 20, 20);
            ll.addView(tvNew);
        }
        //Add an empty layout with a margin bottom to ensure that the user can see all the events
        LinearLayout empty = new LinearLayout(((MainActivity)getActivity()).getApplicationContext());
        LinearLayout.LayoutParams paramsEmpty = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsEmpty.setMargins(0,0,0,180);
        empty.setLayoutParams(paramsEmpty);
        empty.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(empty);
    }
}