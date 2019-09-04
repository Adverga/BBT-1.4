package com.example.bbt.Fragment;

import android.app.ProgressDialog;
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
    private static final String ARGS_TIPE = "tipe";
    private static final String ARGS_LIST = "produkList";
    private RecyclerView rvProduk;
    private TextView btnAdd;
    private String tipe;
    private ArrayList<Produk> produkList = new ArrayList<>();
    private DatabaseReference db;
    private produkAdapter adapter;
    private ProgressDialog progressDialog;

    public ProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produk, container, false);
    }
    public static ProdukFragment newInstance(String tipe, ArrayList<Produk> produkList) {
        ProdukFragment fragment = new ProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TIPE,tipe);
        args.putSerializable(ARGS_LIST,produkList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipe = getArguments().getString(ARGS_TIPE);
            this.produkList = (ArrayList<Produk>) getArguments().getSerializable(ARGS_LIST);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProduk = view.findViewById(R.id.rvProduk);
        btnAdd = view.findViewById(R.id.btnAdd);

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Logging in, please wait");
//
//        db = FirebaseDatabase.getInstance().getReference("Produk");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fetchData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        System.out.println("helper : "+produkList);
//
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
//    private void fetchData(DataSnapshot dataSnapshot) {
//        produkList.clear();
//        for (DataSnapshot ds : dataSnapshot.child(tipe).getChildren()) {
//            Produk p = ds.getValue(Produk.class);
//            Log.d("cek lagi", String.valueOf(ds.getKey()));
//            if (p != null) {
//                produkList.add(p);
//                Log.d("cek p listalat", p.getListAlat());
//            }
//        }
//        progressDialog.dismiss();
//        System.out.println("size : " + produkList.size());
//    }
}
