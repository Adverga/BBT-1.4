package com.example.bbt.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bbt.Adapter.produkAdapter;
import com.example.bbt.FirebaseHelper.ProdukHelper;
import com.example.bbt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukFragment extends Fragment {
    private RecyclerView rvProduk;
    private TextView btnAdd;
    private String tipe;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Produk> produkList;
    private ArrayList<String> listKeys, listData;
    private DatabaseReference db;
    private ProdukHelper helper;
    private produkAdapter adapter;

    public ProdukFragment(String tipe) {
        this.tipe = tipe;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProduk = view.findViewById(R.id.rvProduk);
        btnAdd = view.findViewById(R.id.btnAdd);

        db = FirebaseDatabase.getInstance().getReference(tipe);
        helper = new ProdukHelper(db, tipe);
        System.out.println("helper : "+helper.retrieve());
//        Log.d("cek list",produkList.get(0).getJudul());

        produkList = helper.retrieve();
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new produkAdapter(getActivity(),getContext(),produkList,tipe);
        rvProduk.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fmContainer, new AddFragment(tipe, null))
                        .commit();
            }
        });
    }
}
