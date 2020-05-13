package com.sparklingapps.cardgamefrog.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sparklingapps.cardgamefrog.utils.ViewDialog;
import com.sparklingapps.cardgamefrog.R;

public class HomeFragment extends Fragment {

    private ImageView iv_multiplayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_multiplayer=view.findViewById(R.id.iv_multiplayer);


    }

    private void createDialog() {

        ViewDialog alert = new ViewDialog();
        alert.showCreateGameDialog(getActivity(),0,null,null);

    }

}
