package com.example.edmt_dimo.Model;

import java.util.List;

public class CatagoryModel {
    private String name_id,name,image;
    List<FoodModel> foodModelList;

    public CatagoryModel() {
    }

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<FoodModel> getFoodModelList() {
        return foodModelList;
    }

    public void setFoodModelList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }
}
