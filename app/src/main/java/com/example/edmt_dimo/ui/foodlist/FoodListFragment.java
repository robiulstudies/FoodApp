package com.example.edmt_dimo.ui.foodlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edmt_dimo.Adapter.MyFoodListAdapter;
import com.example.edmt_dimo.Common.Common;
import com.example.edmt_dimo.Model.FoodModel;
import com.example.edmt_dimo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListFragment extends Fragment {
    private FoodListViewModel slideshowViewModel;
    private Unbinder unbinder;
    LayoutAnimationController layoutAnimationController;
    MyFoodListAdapter adapter;

    ProgressBar progressBar;
    @BindView(R.id.footList_recyclerview)
    RecyclerView foodList_recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(FoodListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_foodlist, container, false);
        progressBar=root.findViewById(R.id.progressBar_id);
        unbinder= ButterKnife.bind(this,root);
        intview();

        progressBar.setVisibility(View.VISIBLE);
        slideshowViewModel.getMutableLiveDataFoodList().observe(getActivity(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {
                foodModels=new ArrayList<>();
                adapter=new MyFoodListAdapter(getContext(),foodModels);
                foodList_recyclerView.setAdapter(adapter);
                foodList_recyclerView.setLayoutAnimation(layoutAnimationController);
                progressBar.setVisibility(View.GONE);
            }
        });
        return root;
    }

    private void intview() {
        foodList_recyclerView.setHasFixedSize(true);
        foodList_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);


    }
}