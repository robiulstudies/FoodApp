package com.example.edmt_dimo.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.edmt_dimo.Callback.IBestDealCallbackListener;
import com.example.edmt_dimo.Callback.ICatagoryCallbackListener;
import com.example.edmt_dimo.Model.BestDealModel;
import com.example.edmt_dimo.Model.CatagoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public  class HomeViewModel extends ViewModel implements IBestDealCallbackListener {
    private MutableLiveData<List<BestDealModel>> bestDealListMutable;
    private MutableLiveData<String> messageError=new MutableLiveData<>();
    private IBestDealCallbackListener bestDealCallbackListener;


    public HomeViewModel() {
        bestDealCallbackListener=this;

    }

    public MutableLiveData<List<BestDealModel>> getBestDealListMutable() {
        if (bestDealListMutable==null){
            bestDealListMutable=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadDealList();
        }

        return bestDealListMutable;
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    private void loadDealList() {
        final List<BestDealModel> besttempList = new ArrayList<>();
        DatabaseReference dealRef = FirebaseDatabase.getInstance().getReference("BestDeals");
        dealRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    BestDealModel data=snapshot.getValue(BestDealModel.class);
                    besttempList.add(0,data);
                }

                bestDealCallbackListener.onBestDealLoadSuccess(besttempList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                bestDealCallbackListener.onBestDealLoadFailed(error.getMessage());

            }
        });

    }


    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealListMutable.setValue(bestDealModels);

    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue(message);

    }
}