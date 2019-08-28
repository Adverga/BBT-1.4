package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.FirebaseHelper.ProdukHelper;
import com.example.bbt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private EditText editJudul, editAlat, editBahan, editLangkah, editInfo;
    private RecyclerView rvAlat, rvBahan, rvLangkah, rvInfo;
    private TextView addAlat, addBahan, idTipe, addLangkah, addInfo;
    private List<String> listAlat, listBahan, listLangkah, listInfo;
    private abAdapter alatAdapter, bahanAdapter, langkahAdapter, infoAdapter;
    private Button btnSimpan;
    private String tipe;
    private Produk data;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProdukHelper helper;

    public AddFragment(String tipe, Produk data) {
        this.tipe = tipe;
        this.data = data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idTipe = view.findViewById(R.id.idTipe);
        editJudul = view.findViewById(R.id.editJudul);
        editAlat = view.findViewById(R.id.editAlat);
        editBahan = view.findViewById(R.id.editBahan);
        editLangkah = view.findViewById(R.id.editLangkah);
        editInfo = view.findViewById(R.id.editInfo);
        rvAlat = view.findViewById(R.id.rvAlat);
        rvBahan = view.findViewById(R.id.rvBahan);
        rvLangkah = view.findViewById(R.id.rvLangkah);
        rvInfo = view.findViewById(R.id.rvInfo);
        addAlat = view.findViewById(R.id.addAlat);
        addBahan = view.findViewById(R.id.addBahan);
        addLangkah = view.findViewById(R.id.addLangkah);
        addInfo = view.findViewById(R.id.addInfo);
        btnSimpan = view.findViewById(R.id.btnSimpan);

        idTipe.setText(tipe);

        if (data!=null){
            setupData();
        }else {
            listAlat = new ArrayList<>();
            listBahan = new ArrayList<>();
            listLangkah = new ArrayList<>();
            listInfo = new ArrayList<>();
        }

        setupAdapter(listAlat, rvAlat, alatAdapter);
        setupAdapter(listBahan, rvBahan, bahanAdapter);
        setupAdapter(listLangkah, rvLangkah, langkahAdapter);
        setupAdapter(listInfo, rvInfo, infoAdapter);

        addAlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = editAlat.getText().toString();
                listAlat.add(tmp);
                editAlat.setText(null);
                alatAdapter.notifyDataSetChanged();
            }
        });
        addBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = editBahan.getText().toString();
                listBahan.add(tmp);
                editBahan.setText(null);
                bahanAdapter.notifyDataSetChanged();
            }
        });
        addLangkah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = editLangkah.getText().toString();
                listLangkah.add(tmp);
                editLangkah.setText(null);
                langkahAdapter.notifyDataSetChanged();
            }
        });
        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = editInfo.getText().toString();
                listInfo.add(tmp);
                editLangkah.setText(null);
                infoAdapter.notifyDataSetChanged();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void setupData() {
        editJudul.setText(data.getJudul());
        listAlat = data.getListAlat();
        listBahan = data.getListBahan();
        listLangkah = data.getListLangkah();
        listInfo = data.getListInfo();
    }

    private void simpan() {
        String judul = editJudul.getText().toString();
        Produk produk = new Produk(judul, listAlat, listBahan, listLangkah, listInfo, 10);
        if (inputValidated()){
            if (helper.save(produk)){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fmContainer, new HomeFragment())
                        .commit();

            }
        }
    }

    private boolean inputValidated() {
        boolean res = true;
        if (editJudul.getText().toString().isEmpty()){
            res = false;
            editJudul.setError("This is required");
        }if (listAlat.isEmpty()){
            res = false;
            editAlat.setError("This is required");
        }if (listBahan.isEmpty()){
            res = false;
            editBahan.setError("This is required");
        }if (listLangkah.isEmpty()){
            res = false;
            editLangkah.setError("This is required");
        }if (listInfo.isEmpty()){
            res = false;
            editInfo.setError("This is required");
        }
        return res;
    }

    private void setupAdapter(List<String> list, RecyclerView rv, abAdapter adapter) {
        adapter = new abAdapter(getContext(), list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }
}
