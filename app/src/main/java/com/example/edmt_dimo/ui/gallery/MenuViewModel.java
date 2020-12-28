package com.example.edmt_dimo.ui.gallery;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class MenuViewModel extends ViewModel implements ICatagoryCallbackListener {
    private MutableLiveData<List<CatagoryModel>> catagoryListMutable;
    private MutableLiveData<String> messageError=new MutableLiveData<>();
    private ICatagoryCallbackListener catagoryCallbackListener;

   public MenuViewModel() {
        catagoryCallbackListener = this;
    }

    public MutableLiveData<List<CatagoryModel>> getCatagoryListMutable() {
        if (catagoryListMutable==null){
            catagoryListMutable=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadDealList();
        }
        return catagoryListMutable;
    }

    private void loadDealList() {
        final List<CatagoryModel> catagoryModels = new ArrayList<>();
        DatabaseReference dealRef = FirebaseDatabase.getInstance().getReference("Category");
        dealRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    CatagoryModel data=snapshot.getValue(CatagoryModel.class);
                    data.setName_id(snapshot.getKey());
                    catagoryModels.add(data);
                }

                catagoryCallbackListener.onCatagoryLoadSuccess(catagoryModels);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                catagoryCallbackListener.onCatagoryLoadFailed(error.getMessage());

            }
        });

    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onCatagoryLoadSuccess(List<CatagoryModel> catagoryModelList) {
       catagoryListMutable.setValue(catagoryModelList);

    }

    @Override
    public void onCatagoryLoadFailed(String message) {
       messageError.setValue(message);

    }

}