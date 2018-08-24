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

import java.util.ArrayList;

/**
 * Adapter is use of binding the row item
 */
public class RowAdapter
        extends RecyclerView.Adapter<RowAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<TitleModel> titleModels;

    public RowAdapter(Activity activity, ArrayList<TitleModel> titleModels) {
        this.activity = activity;
        this.titleModels = titleModels;
    }


    /**
     * @param titleModels updated items
     */
    public void setDataSetChange(ArrayList<TitleModel> titleModels) {

        ArrayList<TitleModel> temp = new ArrayList<>();
        for (TitleModel mTitle : titleModels) {
            if (!(mTitle.getTitle() == null && mTitle.getDescription() == null && mTitle.getImageHref() == null)) {
                temp.add(mTitle);
            }
        }
        this.titleModels = temp;
        this.notifyDataSetChanged();
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

    public ArrayList<TitleModel> getList() {
        return titleModels;
    }

    public TitleModel getItemAt(int position) {
        return titleModels.get(position);
    }

    /**
     * View Holder is hold the UI
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowItemListBinding binding;

        private MyViewHolder(RowItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindView(TitleModel bean) {
            binding.setModel(bean);
            binding.setViewModel(new HomeViewModel(activity));
        }
    }
}
