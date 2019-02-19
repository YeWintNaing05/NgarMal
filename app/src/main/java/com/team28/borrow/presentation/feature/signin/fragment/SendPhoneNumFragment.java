package com.team28.borrow.presentation.feature.signin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.feature.signin.event.SendAction;
import com.team28.borrow.util.custom_view.MMEditText;

import butterknife.BindView;


public class SendPhoneNumFragment extends BaseFragment {


    SendAction sendAction;

    @BindView(R.id.edtPhoneNum)
    MMEditText edtPhoneNum;

    @BindView(R.id.edtUserName)
    MMEditText edtUserName;

    @BindView(R.id.btnNext)
    Button btnNext;


    public SendPhoneNumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SendAction) {
            sendAction = (SendAction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SendAction");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startSubscription();

    }

    private void startSubscription() {
        // add(RxTextView.);
        add(RxView.clicks(btnNext).subscribe(ignored -> {
            if (validateInput())
                if (sendAction != null)
                    sendAction.clickNextBtn(edtPhoneNum.getText().toString(), edtUserName.getText().toString());
        }));
    }

    private boolean validateInput() {

        if (edtPhoneNum.getText().toString().isEmpty())
            edtPhoneNum.setError("Pls enter phone number");

        if (edtUserName.getText().toString().isEmpty())
            edtUserName.setError("Pls enter your name");

        return !edtUserName.getText().toString().isEmpty() && !edtPhoneNum.getText().toString().isEmpty();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_send_phone_num;
    }


}
