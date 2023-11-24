package com.example.amigosdavizinhanca.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amigosdavizinhanca.R;
import com.example.amigosdavizinhanca.model.DataModel;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view, int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postText;
        public ViewHolder(View itemView) {
            super(itemView);
            postText = itemView.findViewById(R.id.postText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick (View view){
                    if(longClickListener != null){
                        return longClickListener.onItemLongClick(view,getAdapterPosition());

                    }
                    return false;
            }
        });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(
                R.layout.activity_create_post,
                parent,false
        );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post p =  DataModel.getInstance().getPost(position);
        holder.postText.setText(p.getText());

    }

    @Override
    public int getItemCount() {
        return DataModel.getInstance().getPostsSize();
    }

}
