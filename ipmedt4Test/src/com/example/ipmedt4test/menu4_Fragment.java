package com.example.ipmedt4test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class menu4_Fragment extends Fragment {
	View rootview;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootview = inflater.inflate(R.layout.menu4_layout,container,false);
		return rootview;
	}
}
