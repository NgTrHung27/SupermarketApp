package com.example.aeonmart_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> userList;
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(int position);
    }

    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        onDeleteButtonClickListener = listener;
    }
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        UserViewHolder holder = new UserViewHolder(view);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí của ViewHolder trong RecyclerView
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (onDeleteButtonClickListener != null) {
                        onDeleteButtonClickListener.onDeleteButtonClicked(position);
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.txtName.setText( user.getName());
        holder.txtBirth.setText( user.getBirth());
        holder.txtEmail.setText( user.getEmail());
        holder.txtPhone.setText(  user.getPhone());
        holder.txtCccd.setText( user.getCccd());
        holder.txtAddress.setText( user.getAddress());
        holder.txtPassword.setText(user.getPassword());
        holder.txtrole.setText(user.getRole());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtBirth, txtEmail, txtPhone, txtCccd, txtAddress, txtPassword,txtrole;
        public Button btnDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.useritem_txt_name);
            txtBirth = itemView.findViewById(R.id.useritem_txt_birth);
            txtEmail = itemView.findViewById(R.id.useritem_txt_email);
            txtPhone = itemView.findViewById(R.id.useritem_txt_phone);
            txtCccd = itemView.findViewById(R.id.useritem_txt_cccd);
            txtAddress = itemView.findViewById(R.id.useritem_txt_address);
            txtPassword = itemView.findViewById(R.id.useritem_txt_password);
            txtrole = itemView.findViewById(R.id.useritem_txt_role);
            btnDelete = itemView.findViewById(R.id.useritem_btn_delete);
        }
    }


}

