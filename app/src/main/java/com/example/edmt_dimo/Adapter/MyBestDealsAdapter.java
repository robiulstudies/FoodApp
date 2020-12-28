package com.example.edmt_dimo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.bumptech.glide.Glide;
import com.example.edmt_dimo.Model.BestDealModel;
import com.example.edmt_dimo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBestDealsAdapter extends LoopingPagerAdapter<BestDealModel> {

    @BindView(R.id.imag_best_deal)
    ImageView img_best_deal;
    @BindView(R.id.text_best_deal)
    TextView text_best_deal;
    Unbinder unbinder;

    Context context;
    List<BestDealModel> itemList;
    boolean isInfinite;

    public MyBestDealsAdapter(@NotNull Context context, List<BestDealModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
        this.context = context;
        this.itemList = itemList;
        this.isInfinite = isInfinite;
    }


    @NotNull
    @Override
    protected View inflateView(int i, @NotNull ViewGroup viewGroup, int i1) {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deal_item, viewGroup, false);
    }

    @Override
    protected void bindView(@NotNull View view, int i, int i1) {

        unbinder = ButterKnife.bind(this, view);
        text_best_deal.setText(itemList.get(i).getName());


        try {
            if (itemList.get(i).getImage() != null)
                Glide.with(context).load(itemList.get(i).getImage()).into(img_best_deal);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
