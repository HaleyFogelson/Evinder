package com.example.evinder.ui.notifications;

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
import com.example.evinder.StoreConnection;
import com.example.evinder.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText username, emailadress, phonenumber, birthday;

        username = ((EditText)((MainActivity)getActivity()).findViewById(R.id.firstnameuser));
        emailadress = ((EditText)((MainActivity)getActivity()).findViewById(R.id.emailaddress));
        phonenumber = ((EditText)((MainActivity)getActivity()).findViewById(R.id.phonenumber));
        birthday = ((EditText)((MainActivity)getActivity()).findViewById(R.id.ageuser));

        username.setText(StoreConnection.connectedUser.getName());
        emailadress.setText(StoreConnection.connectedUser.getEmail());
        phonenumber.setText(StoreConnection.connectedUser.getWhatsapp());
        birthday.setText(""+StoreConnection.connectedUser.getAge());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}