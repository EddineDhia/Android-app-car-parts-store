package com.example.carstore;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carstore.databinding.ActivityAdminBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;

    private  String id,title,description,price;

    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
       // EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

       binding.addProduct.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
                title=binding.title.getText().toString();
                description=binding.description.getText().toString();
               price=binding.price.getText().toString();
               addProduct();
           }
       });

       binding.image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(Intent.ACTION_GET_CONTENT);
               intent.setType("image/*");
               startActivityForResult(intent,100);

           }
       });

       binding.uploadPic.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view)
           {
               uploadImage();
           }
       });


       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    private void uploadImage() {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("products/"+id+".png");
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        FirebaseFirestore.getInstance()
                                                .collection("products")
                                                .document(id)
                                                .update("image", uri.toString());
                                        Toast.makeText(AdminActivity.this,"Image Upload Done ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }


    private void addProduct() {
        id= UUID.randomUUID().toString();
        ProductModel productModel = new ProductModel(id,title,description,price,null,true);
        FirebaseFirestore.getInstance()
                .collection("products")
                .document(id)
                .set(productModel);
        Toast.makeText(this,"Product Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==100){
            uri=data.getData();

            binding.image.setImageURI(uri);
        }
    }
}