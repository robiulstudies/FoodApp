package com.example.edmt_dimo.Callback;

import com.example.edmt_dimo.Model.BestDealModel;
import com.example.edmt_dimo.Model.CatagoryModel;

import java.util.List;

public interface ICatagoryCallbackListener {

    void onCatagoryLoadSuccess(List<CatagoryModel> catagoryModelList);
    void onCatagoryLoadFailed(String message);


}
