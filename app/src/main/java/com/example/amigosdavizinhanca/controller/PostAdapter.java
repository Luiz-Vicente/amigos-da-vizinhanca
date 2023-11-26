package com.example.amigosdavizinhanca.controller;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amigosdavizinhanca.R;
import com.example.amigosdavizinhanca.model.UserDataBase;

import com.bumptech.glide.Glide;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.TextViewHolder> {
    private List<Post> itemList;
    public PostAdapter(List<Post> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        UserDataBase userDataBase = new UserDataBase(holder.itemView.getContext());
        
        Post post = itemList.get(position);

        User user = userDataBase.getUserById(post.getCreatorId());

        // Formatação da data
        Date date = new Date(post.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);

        // Validações para garantir que algo seja mostrado
        if (user != null) {
            holder.textUsername.setText(user.getName());
        } else {
            holder.textUsername.setText(R.string.this_account_was_deleted);
        }

        if(post.getText().length() > 1){
            holder.textDescription.setText(post.getText());
        } else {
            holder.textDescription.setText("...");
        }

        if(formattedDate != null) {
            holder.textDate.setText(formattedDate);
        } else {
            holder.textDate.setText("00:00 - 00/00/0000");
        }

        if (!post.getUri().equals("")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.horizontalDivider.setVisibility(View.VISIBLE);

            //usando o Glide para renderizar a imagem
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(post.getUri()))
                    .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
            holder.horizontalDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder para manter os elementos da interface do usuário
    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textDescription;
        ImageView imageView;
        View horizontalDivider;
        TextView textDate;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.usernameView);
            textDescription = itemView.findViewById(R.id.descriptionView);
            imageView = itemView.findViewById(R.id.itemImageView);
            horizontalDivider = itemView.findViewById(R.id.itemDivider);
            textDate = itemView.findViewById(R.id.dateView);
        }
    }
}
