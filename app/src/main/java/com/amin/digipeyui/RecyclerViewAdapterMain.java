package com.amin.digipeyui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterMain extends RecyclerView.Adapter<RecyclerViewAdapterMain.Holder> {
    private List<Integer> images;
    private List<String> names;
    private Context context;

    public RecyclerViewAdapterMain(Context context, List<Integer> images, List<String> names) {
        this.images = images;
        this.names = names;
        this.context=context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_page, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.textView.setText(names.get(position));
        holder.imageView.setImageResource(images.get(position));
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvOptionName);
            imageView = itemView.findViewById(R.id.imgOption);
            imageView.setOnClickListener(view -> {
                Toast.makeText(context, names.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();

            });

        }
    }
}
