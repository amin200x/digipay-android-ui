package com.amin.digipeyui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {
    private List<ScrollItem> list;
    //private Animation slideUp;
    private Animation scaleDown;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ScrollItem> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int thisPosition = position % list.size();
        ScrollItem scrollItem = list.get(thisPosition);
        // slideUp = AnimationUtils.loadAnimation(context, R.anim.anim_textview);
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);

        //holder.textView.setText(scrollItem.getName());

        // holder.textView.setVisibility(scrollItem.getVisibility());
        holder.textView.setText("");
        if (scrollItem.getVisibility() == View.VISIBLE) {
            //  holder.textView.setAnimation(slideUp);
            holder.lnlLayout.setAnimation(scaleDown);
            holder.textView.setText(scrollItem.getName());
        }
        holder.lnlLayout.getLayoutParams().width = scrollItem.getWidth();
        holder.lnlLayout.getLayoutParams().height = scrollItem.getHeight();
        holder.lnlLayout.setAlpha(scrollItem.getAlpha());

       /* holder.lnlLayout.setPadding(scrollItem.getPadding(), scrollItem.getPadding()
                , scrollItem.getPadding(), scrollItem.getPadding());*/


        // holder.lnlLayoutMain.getLayoutParams().width = scrollItem.getWidthMain();
        //  holder.lnlLayoutMain.getLayoutParams().height = scrollItem.getHeightMain();


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
   /*public int getItemCount() {
        return list.size();
    }*/


    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout lnlLayout;
        //LinearLayout lnlLayoutMain;

        public Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvCardName);
            lnlLayout = itemView.findViewById(R.id.lnlItem);
            // lnlLayoutMain = itemView.findViewById(R.id.lnlItemMain);


        }
    }

}
