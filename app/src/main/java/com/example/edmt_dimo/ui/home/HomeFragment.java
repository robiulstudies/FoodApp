package com.example.edmt_dimo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.edmt_dimo.Adapter.MyBestDealsAdapter;
import com.example.edmt_dimo.Adapter.MyPopularCategoriesAdapter;
import com.example.edmt_dimo.EventBus.CategoryClick;
import com.example.edmt_dimo.HomeActivity;
import com.example.edmt_dimo.Model.PopularCategoryModel;
import com.example.edmt_dimo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {
    List<PopularCategoryModel> list;
    MyPopularCategoriesAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    HomeViewModel homeViewModel;
    Unbinder unbinder;

   // LoopingViewPager loopingViewPager;
    LayoutAnimationController layoutAnimationController;

    @BindView(R.id.viewpager)
    LoopingViewPager loopingViewPager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel
                = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("MostPopular");
        recyclerView = view.findViewById(R.id.recycler_poplar);
        init();
       // loopingViewPager = view.findViewById(R.id.viewpager);
        progressBar = view.findViewById(R.id.progressBar);

        homeViewModel.getBestDealListMutable().observe(getActivity(),bestDealModelList -> {
            MyBestDealsAdapter myBestDealsAdapter = new MyBestDealsAdapter(getActivity(), bestDealModelList, true);
            loopingViewPager.setAdapter(myBestDealsAdapter);
        });







        return view;
    }

    private void init() {


        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PopularCategoryModel data = snapshot.getValue(PopularCategoryModel.class);
                    list.add(0, data);
                }
                adapter = new MyPopularCategoriesAdapter(getContext(), list);

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutAnimation(layoutAnimationController);


    }

    @Override
    public void onResume() {
        super.onResume();
        loopingViewPager.resumeAutoScroll();
    }
    @Override
    public void onPause() {
        loopingViewPager.pauseAutoScroll();
        super.onPause();
    }


}