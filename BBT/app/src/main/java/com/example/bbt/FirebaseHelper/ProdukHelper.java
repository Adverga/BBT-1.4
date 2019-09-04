package com.example.bbt.FirebaseHelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bbt.Fragment.Produk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProdukHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Produk> produkList = new ArrayList<>();
    String tipe;
    public ProdukHelper(DatabaseReference db, String tipe){
        this.db = db.child("Produk");
        this.tipe = tipe;
    }
    public boolean save(Produk produk, ArrayList<String> listAlat, ArrayList<String> listBahan, ArrayList<String> listLangkah, ArrayList<String> listInfo){
        if (produk == null){
            saved = false;
        }else {
            try {
                String sListKey = db.child("listAlat").push().getKey();
                db.child("listAlat").push().setValue(listAlat);
                produk.setListAlat(sListKey);
                sListKey = db.child("listBahan").push().getKey();
                db.child("listBahan").push().setValue(listBahan);
                produk.setListBahan(sListKey);
                sListKey = db.child("listLangkah").push().getKey();
                db.child("listLangkah").push().setValue(listLangkah);
                produk.setListLangkah(sListKey);
                sListKey = db.child("listInfo").push().getKey();
                db.child("listInfo").push().setValue(listInfo);
                produk.setListInfo(sListKey);
                db.child(tipe).push().setValue(produk);
                saved = true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public ArrayList<Produk> retrieve(){
        return produkList;
    }
}
