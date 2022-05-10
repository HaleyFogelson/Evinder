package com.example.evinder.ui.dashboard;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.MainActivity;
import com.example.evinder.Post;
import com.example.evinder.R;
import com.example.evinder.SauvegardeFragmentPostLiked;
import com.example.evinder.StoreConnection;
import com.example.evinder.databinding.FragmentDashboardBinding;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private FragmentDashboardBinding binding;
    private HashMap<Button, Integer> buttonsRemove;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLayoutDashboard();
    }

    private void initLayoutDashboard() {
        this.buttonsRemove = new HashMap<>();
        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_swiped);
        for(Post p : SauvegardeFragmentPostLiked.postsILiked){
            System.out.println(p.getId());
            TextView tvNew = new TextView(((MainActivity)getActivity()).getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30,30,30,30);
            tvNew.setLayoutParams(params);
            tvNew.setGravity(Gravity.LEFT);

            String pseudo = ((MainActivity)getActivity()).getUsersWithId(p.getCreator()).getName();
            int age = p.getAge();
            String location = p.getLocation();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = p.getDateActivity();
            date = formatter.format(Long.parseLong(date));
            String texte = p.getTextActivity();

            tvNew.setText(pseudo+", "+age+" yo, "+location+"\n"+date+"\n"+texte);
            tvNew.setTextSize(22);
            tvNew.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
            tvNew.setPadding(20,20,20,20);
            ll.addView(tvNew);

            Button btnShow = new Button(((MainActivity)getActivity()).getApplicationContext());
            btnShow.setText("Remove");
            btnShow.setTextColor(Color.WHITE);
            btnShow.setBackground(((MainActivity)getActivity()).getDrawable(R.drawable.round_corners));
            btnShow.setBackgroundTintList(((MainActivity)getActivity()).getColorStateList(R.color.red));
            btnShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnShow.setGravity(Gravity.CENTER_HORIZONTAL);
            btnShow.setPadding(30,30,30,30);
            btnShow.setOnClickListener(this);
            ll.addView(btnShow);

            buttonsRemove.put(btnShow, p.getId());
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
        for(int i=0; i<SauvegardeFragmentPostLiked.postsILiked.size();i++) {
            if(SauvegardeFragmentPostLiked.postsILiked.get(i).getId() == id) {
                SauvegardeFragmentPostLiked.postsILiked.remove(i);
            }
        }
        ((MainActivity)getActivity()).removeAssociation(id);

        buttonsRemove = null;

        LinearLayout ll = ((MainActivity)getActivity()).findViewById(R.id.layout_swiped);
        ll.removeAllViews();

        initLayoutDashboard();
    }

    @Override
    public void onClick(View view) {
        for (Map.Entry mapentry : buttonsRemove.entrySet()) {
            if(mapentry.getKey().equals(view)) {
                remove((Integer) mapentry.getValue());
            }
        }
    }
}