package com.example.edmt_dimo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.edmt_dimo.Callback.IRecyclerClickListeaner;
import com.example.edmt_dimo.Common.Common;
import com.example.edmt_dimo.EventBus.CategoryClick;
import com.example.edmt_dimo.Model.CatagoryModel;
import com.example.edmt_dimo.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCatagoryItemAdapter extends RecyclerView.Adapter<MyCatagoryItemAdapter.MyViewHolder> {
    Context context;
    List<CatagoryModel> catagoryModelList;

    public MyCatagoryItemAdapter(Context context, List<CatagoryModel> catagoryModelList) {
        this.context = context;
        this.catagoryModelList = catagoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_catagory_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_catagory.setText(new StringBuilder(catagoryModelList.get(position).getName()));


        try {
            if (catagoryModelList.get(position).getImage() != null)
                Glide.with(context).load(catagoryModelList.get(position).getImage()).into(holder.catagory_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setListeaner(new IRecyclerClickListeaner() {
            @Override
            public void onItemClickListeaner(View view, int pos) {
                Common.categorySelected=catagoryModelList.get(pos);
                EventBus.getDefault().postSticky(new CategoryClick(true,catagoryModelList.get(pos)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return catagoryModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;

        @BindView(R.id.text_catagory_t)
        TextView text_catagory;
        @BindView(R.id.img_catagory)
        ImageView catagory_image;

        IRecyclerClickListeaner listeaner;

        public void setListeaner(IRecyclerClickListeaner listeaner) {
            this.listeaner = listeaner;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listeaner.onItemClickListeaner(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (catagoryModelList.size() == 1) {
            return 0;

        } else {
            if (catagoryModelList.size() % 2 == 0) {
                return 0;

            } else {
                return (position > 1 && position == catagoryModelList.size() - 1) ? 1 : 0;
            }
        }

    }
}
