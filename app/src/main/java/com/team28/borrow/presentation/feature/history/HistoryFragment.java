package com.team28.borrow.presentation.feature.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryFragment extends BaseFragment {


    @BindView(R.id.txtBorrowTo)
    MMTextView txtBorrowTo;

    @BindView(R.id.txtBorrowFrom)
    MMTextView txtBorrowFrom;


    public HistoryFragment() {
    }


    public static HistoryFragment newInstance(String type) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (savedInstanceState == null) {
            if (getArguments() != null) {
                if (getArguments().getString("type").equals("owner")) {
                    txtBorrowTo.setTextColor(getResources().getColor(R.color.colorPrimary));
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.flHistoryContainer, SubHistoryPostItemFragment.instantiate(getActivity(), SubHistoryPostItemFragment.class.getName())).commit();
                } else {
                    txtBorrowFrom.setTextColor(getResources().getColor(R.color.colorPrimary));
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.flHistoryContainer, SubHistoryBorrowItemsFragment.instantiate(getActivity(), SubHistoryBorrowItemsFragment.class.getName())).commit();
                }
            }

        }
    }

    @OnClick(R.id.txtBorrowTo)
    void borrowTo() {
        txtBorrowFrom.setTextColor(getResources().getColor(android.R.color.darker_gray));
        txtBorrowTo.setTextColor(getResources().getColor(R.color.colorPrimary));
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.flHistoryContainer, SubHistoryPostItemFragment.instantiate(getActivity(), SubHistoryPostItemFragment.class.getName())).commit();


    }

    @OnClick(R.id.txtBorrowFrom)
    void borrowFrom() {

        txtBorrowFrom.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtBorrowTo.setTextColor(getResources().getColor(android.R.color.darker_gray));

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.flHistoryContainer, SubHistoryBorrowItemsFragment.instantiate(getActivity(), SubHistoryBorrowItemsFragment.class.getName())).commit();


    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_history;
    }
}
