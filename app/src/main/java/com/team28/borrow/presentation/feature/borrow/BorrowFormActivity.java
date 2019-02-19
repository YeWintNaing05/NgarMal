package com.team28.borrow.presentation.feature.borrow;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.history.HistoryActivity;
import com.team28.borrow.presentation.model.BorrowFormModel;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.DialogUtils;
import com.team28.borrow.util.custom_view.MMCheckBox;
import com.team28.borrow.util.custom_view.MMEditText;
import com.team28.borrow.util.custom_view.UnicodeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;

public class BorrowFormActivity extends BaseActivity {


    @Inject
    ViewModelProvider.Factory factory;

    private BorrowFormViewModel borrowFormViewModel;

    @BindView(R.id.edtName)
    MMEditText edtName;

    @BindView(R.id.edtAddress)
    MMEditText edtAddress;

    @BindView(R.id.edtPhone)
    MMEditText edtPhone;

    @BindView(R.id.edtStartBorrowDate)
    MMEditText edtStartBorrowDate;

    @BindView(R.id.edtNumberBorrowDay)
    MMEditText edtNumberBorrowDay;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.spDate)
    AppCompatSpinner spDate;

    @BindView(R.id.edtFee)
    MMEditText edtFee;

    @BindView(R.id.txtInputFee)
    TextInputLayout txtInputFee;

    @BindView(R.id.cbAgree)
    MMCheckBox cbAgree;


    private ProgressDialog dialog;

    public static void start(Context context, ItemModel mItemModel) {
        Intent starter = new Intent(context, BorrowFormActivity.class);
        starter.putExtra("data", mItemModel);
        context.startActivity(starter);
    }

    private int price_per_day;
    private int price_per_week;
    private int price_per_month;
    private String date_type = "";
    private List<String> date = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_borrow_form;
    }

    ItemModel model;

    @Override
    protected void main(Bundle savedInstanceState) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("data"))
            model = getIntent().getParcelableExtra("data");


        if (model != null) {
            date = Arrays.asList(model.date().split(" "));
            price_per_day = model.price_per_day();
            price_per_month = model.price_per_month();
            price_per_week = model.price_per_week();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, date);
        spDate.setAdapter(adapter);

        date_type = date.get(0);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending");
        dialog.setCancelable(false);


        borrowFormViewModel = ViewModelProviders.of(this, factory).get(BorrowFormViewModel.class);


        startSubscription();


    }

    private void startSubscription() {
        add(borrowFormViewModel.getItemStream().subscribe(this::complete));
        add(RxView.clicks(btnSubmit).subscribe(ignored -> {
            dialog.show();

            if (validateInput()) {

                apply(getBorrowData());
            } else {
                dialog.hide();
                Toast.makeText(this, "Pls fill all input", Toast.LENGTH_SHORT).show();
            }
        }));
        add(RxView.touches(edtStartBorrowDate, motionEvent -> motionEvent.getAction() == MotionEvent.ACTION_UP).subscribe(ignored -> showDatePicker()));
        add(RxTextView.textChanges(edtNumberBorrowDay).subscribe(value -> {
            String date = spDate.getSelectedItem().toString();
            int fee = 0;
            if (!value.toString().isEmpty()) {
                switch (date) {
                    case "Day":
                        fee = Integer.parseInt(value.toString()) * price_per_day;
                        break;
                    case "Week":
                        fee = Integer.parseInt(value.toString()) * price_per_week;

                        break;
                    case "Month":
                        fee = Integer.parseInt(value.toString()) * price_per_month;

                        break;
                }
                edtFee.setText(String.valueOf(fee));
                txtInputFee.setVisibility(View.VISIBLE);
            } else {
                txtInputFee.setVisibility(View.GONE);

            }
        }));
        add(RxAdapterView.itemSelections(spDate).subscribe(integer -> {
            int fee = 0;
            if (edtNumberBorrowDay.getText() != null && !edtNumberBorrowDay.getText().toString().equals("")) {
                switch (date.get(integer)) {
                    case "Day":
                        date_type = "Day";
                        fee = Integer.parseInt(edtNumberBorrowDay.getText().toString()) * price_per_day;
                        break;
                    case "Week":
                        fee = Integer.parseInt(edtNumberBorrowDay.getText().toString()) * price_per_week;
                        date_type = "Week";
                        break;
                    case "Month":
                        fee = Integer.parseInt(edtNumberBorrowDay.getText().toString()) * price_per_month;
                        date_type = "Month";

                        break;
                }
                edtFee.setText(String.valueOf(fee));
                txtInputFee.setVisibility(View.VISIBLE);
            }
        }));
    }

    private void complete(String s) {
        dialog.hide();
        showSnack(s, "Complete", v -> {

        }, Snackbar.LENGTH_LONG);

        HistoryActivity.start(this, "borrow");
        finish();
    }

    private BorrowFormModel getBorrowData() {
        return BorrowFormModel.create(UnicodeHelper.getUnicode(edtName.getText().toString()), UnicodeHelper.getUnicode(model.item_name()), UnicodeHelper.getUnicode(model.brand_name()), model.img(), Constants.PENDING, model.item_id(), model.user_id(), FirebaseAuth.getInstance().getUid(), edtAddress.getText().toString()
                , edtPhone.getText().toString(), edtStartBorrowDate.getText().toString(), edtNumberBorrowDay.getText().toString(), edtFee.getText().toString(), date_type);
    }

    private boolean validateInput() {
        return cbAgree.isChecked() && !edtName.getText().toString().isEmpty() && !edtAddress.getText().toString().isEmpty() && !edtPhone.getText().toString().isEmpty() &&
                !edtStartBorrowDate.getText().toString().isEmpty() && !edtNumberBorrowDay.getText().toString().isEmpty() && !edtFee.getText().toString().isEmpty() && !date_type.isEmpty();
    }


    private void apply(BorrowFormModel model) {
        borrowFormViewModel.applyBorrowForm(model);
    }

    private void showDatePicker() {
        DialogUtils.showDatePicker(this, new DialogUtils.onDateSetComplete() {
            @Override
            public void date(String s) {
                edtStartBorrowDate.setText(s);
                edtStartBorrowDate.setError(null);
            }

            @Override
            public void error(String s) {
                edtStartBorrowDate.setError(s);
                Toast.makeText(BorrowFormActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
