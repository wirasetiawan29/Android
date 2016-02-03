package com.wirasetiawan.viewpagerarrowindicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wira on 2/3/16.
 */
public class Fragment extends android.support.v4.app.Fragment {


    private static final String KEY_MESSAGE = "message";

    public Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.textview_in_fragment);
        String msg = getArguments().getString(KEY_MESSAGE);
        textView.setText(msg);

        return rootView;
    }

    public static android.support.v4.app.Fragment newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, message);
        Fragment fragment = new Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

