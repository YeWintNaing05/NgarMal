package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowItemsRecyclerAdapter extends BaseRecyclerViewAdapter<ItemModel, HomeItemViewHolder> implements Filterable {

    private List<ItemModel> itemModelListFileter = Collections.emptyList();
    private List<ItemModel> itemList = new ArrayList<>();

    public void setItemList(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemLayoutResource() {
        return R.layout.item_list_all;
    }

    @Override
    public HomeItemViewHolder getViewHolder(View view) {
        return new HomeItemViewHolder(view);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();


                if (charString.isEmpty()) {
                    itemModelListFileter = itemList;
                } else {
                    List<ItemModel> filteredList = new ArrayList<>();
                    for (ItemModel row : itemList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.item_name().toLowerCase().contains(charString.toLowerCase()) || row.brand_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    itemModelListFileter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemModelListFileter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemModelListFileter = (ArrayList<ItemModel>) filterResults.values;
                setModels(itemModelListFileter);
                notifyDataSetChanged();
            }
        };

    }
}
