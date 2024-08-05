package com.example.md18_and102_asm.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.md18_and102_asm.R;

public class GioiThieuFragment extends Fragment {

    public GioiThieuFragment() {

    }

    public static GioiThieuFragment newInstance(String param1, String param2) {
        GioiThieuFragment fragment = new GioiThieuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        return view;
    }
}