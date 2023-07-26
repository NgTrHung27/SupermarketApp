package com.example.aeonmart_demo.Adapter;

import static android.graphics.BitmapFactory.decodeFile;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Activity.DetailActivity;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyVH>{
    ArrayList<HomeModel> homeModels;

    public HomeAdapter(ArrayList<HomeModel> homeModels) {
        this.homeModels = homeModels;
    }

    @NonNull
    @Override
    public HomeAdapter.MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_product_cart_view,parent,false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyVH holder, int position) {
        HomeModel homeModel = homeModels.get(position);
        holder.textViewCart.setText(homeModel.getName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference = storageReference.child(homeModel.getImage());

        try{
            File file = File.createTempFile("image", "jpg");
            pathReference.getFile(file)
                    .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                            holder.ProductCartView.setImageBitmap(decodeFile(file.getPath()));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ABC",e.getMessage());
                        }
                    });
        }catch (IOException e)
        {
            throw new RuntimeException();
        }

        holder.ProductCartView.setOnClickListener(view -> {
            //when click send data to details activity
            Intent sendData2Detail = new Intent(holder.ProductCartView.getContext(), DetailActivity.class);
            sendData2Detail.putExtra("category",homeModels.get(position).getCategory());
            sendData2Detail.putExtra("description",homeModels.get(position).getDescription());
            sendData2Detail.putExtra("masp",homeModels.get(position).getMaSp());
            sendData2Detail.putExtra("origin",homeModels.get(position).getOrigin());
            sendData2Detail.putExtra("price",homeModels.get(position).getPrice());
            sendData2Detail.putExtra("rate",homeModels.get(position).getRate());

            //transition animation 2 detail
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity)holder.ProductCartView.getContext(),holder.ProductCartView,
                            "imageMain");
            //sharedElementName is the same as xml file (imageMain)
            holder.ProductCartView.getContext().startActivity(sendData2Detail,optionsCompat.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        ImageView ProductCartView;
        TextView textViewCart;
        public MyVH(@NonNull View itemView) {
            super(itemView);
            ProductCartView = itemView.findViewById(R.id.imageViewProductCart);
            textViewCart = itemView.findViewById(R.id.textviewProductCart);
        }
    }
}
