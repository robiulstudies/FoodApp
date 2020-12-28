package com.example.edmt_dimo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.edmt_dimo.Model.PopularCategoryModel;
import com.example.edmt_dimo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyPopularCategoriesAdapter extends RecyclerView.Adapter<MyPopularCategoriesAdapter.MyViewHolder> {

    Context context;
    List<PopularCategoryModel> popularCategoryModels;

    public MyPopularCategoriesAdapter(Context context, List<PopularCategoryModel> popularCategoryModels) {
        this.context = context;
        this.popularCategoryModels = popularCategoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popular_catagories_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_category_name.setText(popularCategoryModels.get(position).getName());


        try {
            if (popularCategoryModels.get(position).getImage()!=null)
            Glide.with(context).load(popularCategoryModels.get(position).getImage()).into(holder.circleImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return popularCategoryModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_category_name;
        CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category_name = itemView.findViewById(R.id.category_name);
            circleImageView = itemView.findViewById(R.id.catagory_image);
        }
    }
}
