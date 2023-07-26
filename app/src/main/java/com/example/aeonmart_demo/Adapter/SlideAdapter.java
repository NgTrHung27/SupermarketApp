package com.example.aeonmart_demo.Adapter;

import static android.graphics.BitmapFactory.decodeFile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.aeonmart_demo.Model.SlideViewModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlideAdapter  extends SliderViewAdapter<SlideAdapter.MyViewHolder> implements Filterable {

    List<SlideViewModel> slideViewModels = new ArrayList<>();
    Context context;

    public SlideAdapter(Context context) {
        this.context = context;
    }
    public void renewItems(ArrayList<SlideViewModel> slideViewModels){
        this.slideViewModels=slideViewModels;
        notifyDataSetChanged();
    }
    public void deleteItems(int position){
        this.slideViewModels.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public SlideAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent) {
        //inflate layout here for slide items
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlideAdapter.MyViewHolder viewHolder, int position) {
        SlideViewModel slideViewModel = slideViewModels.get(position);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference = storageRef.child(slideViewModel.getPic());
        try{
            File file = File.createTempFile("slider_image", "jpg");
            pathReference.getFile(file)
                    .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                            viewHolder.slider_img.setImageBitmap(decodeFile(file.getPath()));
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
    }

    @Override
    public int getCount() {
        return slideViewModels.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }
    public class MyViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView slider_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            slider_img=itemView.findViewById(R.id.img_thumbnail);
        }
    }
}
