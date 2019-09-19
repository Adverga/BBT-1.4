package com.example.bbt.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbt.Activity.ViewProduk;
import com.example.bbt.Adapter.produkAdapter;
import com.example.bbt.AddActivity;
import com.example.bbt.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukFragment extends Fragment {
    private static final String ARGS_TIPE = "tipe";
    private RecyclerView rvProduk;
    private TextView btnAdd;
    private String tipe;
    private ArrayList<Produk> produkList = new ArrayList<>();
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");
    private produkAdapter adapter;
    private ProgressDialog progressDialog;
    private boolean mod;

    public ProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produk, container, false);
    }
    public static ProdukFragment newInstance(String tipe, boolean mod) {
        ProdukFragment fragment = new ProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TIPE,tipe);
        args.putBoolean("mod",mod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipe = getArguments().getString(ARGS_TIPE);
            this.mod = getArguments().getBoolean("mod");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProduk = view.findViewById(R.id.rvProduk);
        btnAdd = view.findViewById(R.id.btnAdd);

        if (mod){
            btnAdd.setVisibility(View.VISIBLE);
        }else {
            btnAdd.setVisibility(View.GONE);
        }
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new produkAdapter(getContext(), produkList, tipe, mod, new ItemClickListener() {
            @Override
            public void onItemClick(Produk produk, int pos) {
                Intent intent = new Intent(getActivity(), ViewProduk.class);
                intent.putExtra("produk",produk);
                intent.putExtra("judul",produkList.get(pos).getJudul());
                intent.putExtra("image",produkList.get(pos).getImage());
                intent.putExtra("alat", produkList.get(pos).getListAlat());
                intent.putExtra("bahan",produkList.get(pos).getListBahan());
                intent.putExtra("langkah",produkList.get(pos).getListLangkah());
                intent.putExtra("info", produkList.get(pos).getListInfo());
                intent.putExtra("mod",mod);
                startActivity(intent);
            }

            @Override
            public void onDeleteData(Produk produk, int pos) {
                if(db!=null){
                    db.child(tipe).child(produk.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "success delete", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        rvProduk.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddActivity.class);
                i.putExtra("tipe", tipe);
                startActivity(i);
//                getActivity().getSupportFrargmentManager().beginTransaction()
//                        .replace(R.id.fmContainer, new AddFragment2(tipe, null))
//                        .commit();
            }
        });
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Produk> set = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child(tipe).getChildren()){
                    Produk pd = ds.getValue(Produk.class);
                    set.add(pd);
                }
                produkList.clear();
                produkList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
