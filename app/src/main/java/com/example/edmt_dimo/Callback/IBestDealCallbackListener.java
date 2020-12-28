package com.example.edmt_dimo.Callback;

import com.example.edmt_dimo.Model.BestDealModel;
import com.example.edmt_dimo.Model.PopularCategoryModel;

import java.util.List;

public interface IBestDealCallbackListener {

    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
