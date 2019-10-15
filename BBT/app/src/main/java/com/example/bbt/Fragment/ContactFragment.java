package com.example.bbt.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bbt.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private TextView textView16, textView17, textView18, textView19, textView20, textView21;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        textView16 = (TextView)getView().findViewById(R.id.textView16);
        textView17 = (TextView)getView().findViewById(R.id.textView17);
        textView18 = (TextView)getView().findViewById(R.id.textView18);
        textView19 = (TextView)getView().findViewById(R.id.textView19);
        textView20 = (TextView)getView().findViewById(R.id.textView20);
        textView21 = (TextView)getView().findViewById(R.id.textView21);

        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("http://bbppketindan.bppsdmp.pertanian.go.id");

            }
        });

        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("https://www.youtube.com/channel/UCnTojV2_KfODCoQmdlcRyKQ");

            }
        });

        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("https://www.facebook.com/ketindan.bbpp");

            }
        });

        textView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("https://twitter.com/bbppketindan");

            }
        });

        textView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("https://www.instagram.com/ketindanbbpp/?utm_source=ig_embed");

            }
        });

        textView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect("https://landbouw-mart-ketindan.business.site/");

            }
        });

    }

    private void redirect(String alamat){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(alamat));
        startActivity(browserIntent);
    }

}
