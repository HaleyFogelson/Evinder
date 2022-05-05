package com.example.evinder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.evinder.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.StoreConnection;
import com.example.evinder.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private HomeViewModel vm;
    private FragmentHomeBinding binding;
    private String randomGoodDeed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(HomeViewModel.class);
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