package com.example.whatsappdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatsappdesign.R;

import java.util.ArrayList;

public class DAdapter extends RecyclerView.Adapter<DAdapter.ViewHolder> {
    Context context;
    ArrayList<ModelD> modelDArrayList;

    public DAdapter(Context context, ArrayList<ModelD> modelDArrayList) {
        this.context = context;
        this.modelDArrayList = modelDArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelD modelD = modelDArrayList.get(position);
        holder.itemName.setText(modelD.getName());
    }

    @Override
    public int getItemCount() {
        return modelDArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName =(TextView) itemView.findViewById(R.id.mTextView);
        }
    }
}
