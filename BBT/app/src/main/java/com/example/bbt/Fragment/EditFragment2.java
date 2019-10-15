package com.example.bbt.Fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.Adapter.LangkahAdapter;
import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.FirebaseHelper.ProdukHelper;
import com.example.bbt.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment2 extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE_REQ = 5;
    private static final int PICK_IMAGEL_REQ = 10;
    private EditText editLangkah, editInfo;
    private RecyclerView rvLangkah, rvInfo;
    private TextView addLangkah, addInfo;
    private ArrayList<String> listAlat, listBahan, listLangkah, listLangkahImg, listInfo, tmpLang, tmpLangImg;
    private ArrayList<Uri> listImageLangkah;
    private LangkahAdapter langkahAdapter;
    private abAdapter infoAdapter;
    private ImageView imageUpload;
    private Button btnSimpan, btnImageLUpload;
    private String tipe, judul;
    private Produk data, datatmp;

    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProdukHelper helper;
    private Uri imageUri;
    private Uri imageLUri;
    private ImageView imageLUpload;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");

    public EditFragment2() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit2, container, false);
    }
    public static EditFragment2 newInstance(String tipe, Uri imageUri, Produk datatmp, ArrayList<String> listAlat, ArrayList<String> listBahan, Produk data) {
        EditFragment2 fragment = new EditFragment2();
        Bundle args = new Bundle();
        args.putString("tipe",tipe);
        if (imageUri!=null) args.putString("image", imageUri.toString());
        else args.putString("image", null);
        args.putStringArrayList("listAlat", listAlat);
        args.putStringArrayList("listBahan", listBahan);
        args.putSerializable("datatmp", datatmp);
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipe = getArguments().getString("tipe");
            if (getArguments().getString("image")!=null)
            this.imageUri = Uri.parse(getArguments().getString("image"));
            else
            this.imageUri = null;
            this.listAlat = getArguments().getStringArrayList("listAlat");
            this.listBahan = getArguments().getStringArrayList("listBahan");
            this.datatmp = (Produk) getArguments().getSerializable("datatmp");
            this.data = (Produk) getArguments().getSerializable("data");
//            Log.d("cek imageUri",imageUri.toString());
            Log.d("cek tipe", tipe);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init
        editLangkah = view.findViewById(R.id.editLangkahe);
        imageLUpload = view.findViewById(R.id.imgUploadLangkahe);
        btnImageLUpload = view.findViewById(R.id.btnUploadLangkahe);
        editInfo = view.findViewById(R.id.editInfoe);
        rvLangkah = view.findViewById(R.id.rvLangkahe);
        rvInfo = view.findViewById(R.id.rvInfoe);
        addLangkah = view.findViewById(R.id.addLangkahe);
        addInfo = view.findViewById(R.id.addInfoe);
        btnSimpan = view.findViewById(R.id.btnSimpane);
        progressBar = view.findViewById(R.id.progressBarAe);
        //setup image
        btnImageLUpload.setOnClickListener(this);

        listLangkah = new ArrayList<>();
        listLangkahImg = new ArrayList<>();
        listInfo = new ArrayList<>();
        tmpLang = new ArrayList<>();
        tmpLangImg = new ArrayList<>();
        setupData();
        langkahAdapter = new LangkahAdapter(getContext(), listLangkahImg, listLangkah, null);
        //setup Adapter
        rvLangkah.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLangkah.setAdapter(langkahAdapter);
        infoAdapter = new abAdapter(getContext(),listInfo);
        rvInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInfo.setAdapter(infoAdapter);

        addLangkah.setOnClickListener(this);
        addInfo.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        helper = new ProdukHelper(databaseReference,tipe);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValidated()){
                    Log.d("cek btnSimpan", "bisa");
//                    simpan();
                }else {
                    Log.d("cek btnSimpan", "tidak");
                }
            }
        });
    }

    private void setupData() {
        final String keyLangkah = data.getListLangkah();
        final String keyLangkahImg = data.getListLangkahImg();
        final String keyInfo = data.getListInfo();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> setLangkah ,setLangkahImg, setInfo;
                setLangkah = (ArrayList<String>) dataSnapshot.child("listLangkah").child(keyLangkah).getValue();
                setLangkahImg = (ArrayList<String>) dataSnapshot.child("listLangkahImg").child(keyLangkahImg).getValue();
                setInfo = (ArrayList<String>) dataSnapshot.child("listInfo").child(keyInfo).getValue();

                listLangkah.clear();
                listLangkahImg.clear();
                listInfo.clear();
                tmpLang.clear();
                tmpLangImg.clear();

                listLangkah.addAll(setLangkah);
                listLangkahImg.addAll(setLangkahImg);
                listInfo.addAll(setInfo);
                tmpLang.addAll(setLangkah);
                tmpLangImg.addAll(setLangkahImg);

                Log.d("cek list langkah", listLangkah.toString());

                langkahAdapter.notifyDataSetChanged();
                infoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean inputValidated() {
        boolean res = true;
        if (listLangkah.isEmpty()){
            res = false;
            editLangkah.setError("This is required");
        }if (listImageLangkah.isEmpty()){
            res = false;
            Toast.makeText(getContext(), "Gambar belum dipilih", Toast.LENGTH_SHORT);
        }if (listInfo.isEmpty()) {
            res = false;
            editInfo.setError("This is required");
        }
        return res;
    }

    @Override
    public void onClick(View view) {
        String tmp;
        switch (view.getId()){
            case R.id.addLangkah :
                tmp = editLangkah.getText().toString();
                listImageLangkah.add(imageLUri);
                listLangkah.add(tmp);
                editLangkah.setText(null);
                imageLUpload.setImageResource(R.drawable.btn_p);
                imageLUri = null;
                langkahAdapter.notifyDataSetChanged();
                break;
            case R.id.addInfo :
                tmp = editInfo.getText().toString();
                listInfo.add(tmp);
                editInfo.setText(null);
                infoAdapter.notifyDataSetChanged();
                break;
            case R.id.btnUploadLangkah :
                openFileChooser();
            default:break;
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGEL_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(getContext()).load(imageUri).into(imageUpload);
            Log.d("cekimageuri",imageUri.toString());
        }if(requestCode == PICK_IMAGEL_REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageLUri = data.getData();
            Glide.with(getContext()).load(imageLUri).into(imageLUpload);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void  simpan(){
        if(listLangkah.equals(tmpLang)){
            helper.update(data, listAlat, listBahan, listLangkah, listLangkahImg, listInfo);
        }else



        if (imageUri != null) {
            Log.d("cek img", imageUri.toString());
            final String[] downloadUrl = new String[1];
            final Produk produk = data;
            judul = data.getJudul();
            for (int i = -1; i < listLangkah.size(); i++){
                if (i==-1){
                    final String imageName = "img-" + judul.concat(" ").toLowerCase() + getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(imageName);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload Gambar berhasil", Toast.LENGTH_SHORT);
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl[0] = uri.toString();
                                    produk.setImage(downloadUrl[0]);
                                    Log.d("cek berhasil", " : image");
//                                    if (helper.save(produk, listAlat, listBahan, listLangkah, listLangkahImg, listInfo)) {
//                                        getActivity().finish();
//                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
                }else {
                    Uri tmpUrl = listImageLangkah.get(i);
                    String imgName = "img-" + judul.concat(" ").toLowerCase() + getFileExtension(tmpUrl)+i;
                    final StorageReference imgRef = storageReference.child(imgName);
                    final int index = i;
                    imgRef.putFile(listImageLangkah.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload Gambar berhasil", Toast.LENGTH_SHORT);
                            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("cek berhasil", " : image "+index);
                                    listLangkahImg.add(uri.toString());
                                    if (index== listLangkah.size()-1){
                                        if(helper.save(produk, listAlat, listBahan, listLangkah, listLangkahImg, listInfo)){
                                            Toast.makeText(getContext(),"konten berhasil diupload", Toast.LENGTH_SHORT);
                                        }
                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
                }
                Log.d("cek iterasi", String.valueOf(i));
            }
        }else {
            Log.d("cek img", imageUri.toString());
            Toast.makeText(getContext(),"gambar utama kosong", Toast.LENGTH_SHORT);
        }
    }
}
