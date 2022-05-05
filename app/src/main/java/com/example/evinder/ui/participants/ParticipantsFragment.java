package com.example.evinder.ui.participants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.MainActivity;
import com.example.evinder.StoreConnection;
import com.example.evinder.databinding.FragmentParticipantsBinding;

public class ParticipantsFragment extends Fragment {
    private ParticipantsViewModel vm;
    private FragmentParticipantsBinding binding;
    private String randomGoodDeed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParticipantsViewModel homeViewModel =
                new ViewModelProvider(this).get(ParticipantsViewModel.class);

        View root = binding.getRoot();


        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ParticipantsViewModel.class);
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity)getActivity()).initPost();
        ((MainActivity)getActivity()).initListener();

        System.out.println("CONNECTED AS : "+ StoreConnection.connectedUser);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("DESTROYED !");
        binding = null;
    }
}
