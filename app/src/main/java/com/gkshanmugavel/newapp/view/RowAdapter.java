package com.gkshanmugavel.newapp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gkshanmugavel.newapp.R;
import com.gkshanmugavel.newapp.databinding.RowItemListBinding;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.view_model.HomeViewModel;

import java.util.List;

public class RowAdapter
        extends RecyclerView.Adapter<RowAdapter.MyViewHolder> {

    Activity activity;
    List<TitleModel> titleModels;

    public RowAdapter(Activity activity, List<TitleModel> titleModels) {
        this.activity = activity;
        this.titleModels = titleModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_item_list, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.bindView(titleModels.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return titleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowItemListBinding binding;

        public MyViewHolder(RowItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(TitleModel bean) {
            binding.setModel(bean);
            binding.setViewModel(new HomeViewModel(activity));
        }

    }
}
