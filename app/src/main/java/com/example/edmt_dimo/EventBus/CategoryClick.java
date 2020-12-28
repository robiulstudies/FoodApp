package com.example.edmt_dimo.EventBus;

import com.example.edmt_dimo.Model.CatagoryModel;

public class CategoryClick {
    private boolean success;
    private CatagoryModel catagoryModel;

    public CategoryClick(boolean success, CatagoryModel catagoryModel) {
        this.success = success;
        this.catagoryModel = catagoryModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CatagoryModel getCatagoryModel() {
        return catagoryModel;
    }

    public void setCatagoryModel(CatagoryModel catagoryModel) {
        this.catagoryModel = catagoryModel;
    }
}
