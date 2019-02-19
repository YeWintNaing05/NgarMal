package com.team28.borrow.presentation.feature.main.home.sub_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team28.borrow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubHomeFrag extends Fragment {


    public SubHomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_home, container, false);
    }

}
