package com.example.evinder.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.MainActivity;
import com.example.evinder.R;
import com.example.evinder.SauvegardeFragmentAdd;
import com.example.evinder.databinding.FragmentAddEventBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFragment extends Fragment {
    private View root;

    private FragmentAddEventBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        this.root = binding.getRoot();

        return root;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //RESTORE FRAGMENT :
        if(SauvegardeFragmentAdd.name != null) {
            EditText et = (EditText) this.root.findViewById(R.id.nameActivity);
            et.setText(SauvegardeFragmentAdd.name);
        }

        if(SauvegardeFragmentAdd.location != null) {
            EditText locationEt = (EditText) this.root.findViewById(R.id.location);
            locationEt.setText(SauvegardeFragmentAdd.location);
        }

        if(SauvegardeFragmentAdd.description != null) {
            EditText descriptionEt = (EditText) this.root.findViewById(R.id.descriptionEvent);
            descriptionEt.setText(SauvegardeFragmentAdd.location);
        }

        if(SauvegardeFragmentAdd.date != null) {
            Button b = (Button) this.root.findViewById(R.id.edit_date);
            b.setText(SauvegardeFragmentAdd.date);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.sauvegarderFragment();
        binding = null;
    }

    private void sauvegarderFragment() {
        EditText et = (EditText) this.root.findViewById(R.id.nameActivity);
        String name = et.getText().toString();

        EditText locationEt = (EditText) this.root.findViewById(R.id.location);
        String location = locationEt.getText().toString();

        EditText descriptionEt = (EditText) this.root.findViewById(R.id.descriptionEvent);
        String description = descriptionEt.getText().toString();

        Button b = (Button) this.root.findViewById(R.id.edit_date);
        String date = b.getText().toString();

        SauvegardeFragmentAdd.name = name;
        SauvegardeFragmentAdd.location = location;
        SauvegardeFragmentAdd.description = description;
        SauvegardeFragmentAdd.date = date;

    }
}