package com.example.edmt_dimo.ui.foodlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.edmt_dimo.Common.Common;
import com.example.edmt_dimo.Model.FoodModel;

import java.util.List;

public class FoodListViewModel extends ViewModel {

    private MutableLiveData<List<FoodModel>> mutableLiveDataFoodList;


    public FoodListViewModel() {
    }

    public MutableLiveData<List<FoodModel>> getMutableLiveDataFoodList() {
        if (mutableLiveDataFoodList==null){
            mutableLiveDataFoodList=new MutableLiveData<>();
            mutableLiveDataFoodList.setValue(Common.categorySelected.getFoodModelList());

        }
        return mutableLiveDataFoodList;
    }
}