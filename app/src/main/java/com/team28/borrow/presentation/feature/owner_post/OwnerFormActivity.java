package com.team28.borrow.presentation.feature.owner_post;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.team28.borrow.R;
import com.team28.borrow.presentation.feature.history.HistoryActivity;
import com.team28.borrow.util.Constants;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.rxfirebase.RxFirebaseStorage;
import com.team28.borrow.util.DialogUtils;
import com.team28.borrow.util.PermissionUtils;
import com.team28.borrow.util.Utils;
import com.team28.borrow.util.custom_view.MMCheckBox;
import com.team28.borrow.util.custom_view.MMEditText;
import com.team28.borrow.util.custom_view.MMTextView;
import com.team28.borrow.util.custom_view.UnicodeHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import javax.inject.Inject;

import butterknife.BindView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class OwnerFormActivity extends BaseActivity {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    @BindView(R.id.spType)
    AppCompatSpinner spType;

    @BindView(R.id.extraCarLayout)
    LinearLayout extraCarLayout;

    @BindView(R.id.extraCameraLayout)
    LinearLayout extraCameraLayout;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Inject
    ViewModelProvider.Factory factory;

    private OwnerFormViewModel ownerFormViewModel;


    @BindView(R.id.edtMaterialName)
    EditText edtMaterialName;

    @BindView(R.id.edtMaterialBrandName)
    EditText edtMaterialBrandName;

    @BindView(R.id.edtCarEngine)
    EditText edtCarEngine;

    @BindView(R.id.edtCarSeat)
    EditText edtCarSeat;

    @BindView(R.id.rbGasGroup)
    RadioGroup rbGasGroup;

    @BindView(R.id.rbFocusGroup)
    RadioGroup rbFocusGroup;

    @BindView(R.id.edtCameraZoom)
    EditText edtCameraZoom;

    @BindView(R.id.edtPicture)
    EditText edtPicture;

    @BindView(R.id.imgGallery)
    ImageView imgGallery;

    @BindView(R.id.txtInputPricePerDay)
    TextInputLayout textInputPricePerDay;

    @BindView(R.id.txtInputPricePerWeek)
    TextInputLayout textInputPricePerWeek;

    @BindView(R.id.txtInputPricePerMonth)
    TextInputLayout textInputPricePerMonth;

    @BindView(R.id.edtPricePerDay)
    MMEditText edtPricePerDay;

    @BindView(R.id.edtPricePerWeek)
    MMEditText edtPricePerWeek;

    @BindView(R.id.edtPricePerMonth)
    MMEditText edtPricePerMonth;

    @BindView(R.id.chBorrowByDay)
    MMCheckBox cbBorrowByDay;

    @BindView(R.id.chBorrowByWeek)
    MMCheckBox cbBorrowByWeek;

    @BindView(R.id.chBorrowByMonth)
    MMCheckBox cbBorrowByMonth;

    @BindView(R.id.cbAgree)
    MMCheckBox cbAgree;

    @BindView(R.id.edtDamage)
    MMEditText edtDamage;

    @BindView(R.id.txtChoose)
    MMTextView txtChoose;

    private ItemModel itemModel;

    private String gas_or_oil = "";
    private Integer price_per_day = 0;
    private Integer price_per_week = 0;
    private Integer price_per_month = 0;
    private String focus = "";
    private Uri filePath;
    Bitmap bitmap;

    private ProgressDialog dialog;
    private List<String> data = new ArrayList<>();

    ArrayAdapter<String> adapterType;

    public static void start(Context context) {
        Intent starter = new Intent(context, OwnerFormActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, ItemModel itemModel) {
        Intent starter = new Intent(context, OwnerFormActivity.class);
        starter.putExtra("data", itemModel);
        context.startActivity(starter);
    }

    @Override
    protected void main(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending");
        dialog.setCancelable(false);


        ownerFormViewModel = ViewModelProviders.of(this, factory).get(OwnerFormViewModel.class);


        permissions.add(CAMERA);
        permissions.add(READ_EXTERNAL_STORAGE);


        permissionsToRequest = PermissionUtils.findUnAskedPermissions(permissions, this);


        initUi();

        //data update
        if (getIntent().hasExtra("data")) {
            itemModel = getIntent().getParcelableExtra("data");

            setDataToInput(itemModel);
        }


        startSubscriptions();

    }

    private void setDataToInput(ItemModel data) {


     /*   edtPicture.setText("Shi p thrr pr");
        Glide.with(imgGallery).load(data.img()).into(imgGallery);

        imgGallery.setVisibility(View.VISIBLE);
        edtPicture.setEnabled(false);
*/
        edtDamage.setText(data.damage());


        if (data.category().equals(getString(R.string.car))) {
            extraCarLayout.setVisibility(View.VISIBLE);


            edtCarEngine.setText(data.engine_power());
            edtCarSeat.setText(data.seat_num());

            if (data.gas_or_oil().equals(getString(R.string.gas_car))) {
                rbGasGroup.check(R.id.rbGas);
            } else {
                rbGasGroup.check(R.id.rbOil);
            }
        }

        if (data.category().equals("Camera")) {
            extraCameraLayout.setVisibility(View.VISIBLE);

            edtCameraZoom.setText(data.zoom());

            if (data.focus_condition().equals(getString(R.string.focus_bad))) {
                rbFocusGroup.check(R.id.rbFocusBad);
            } else {
                rbFocusGroup.check(R.id.rbFocusGood);
            }


        }


        if (data.price_per_day() != 0) {
            cbBorrowByDay.setChecked(true);

            edtPricePerDay.setVisibility(View.VISIBLE);
            edtPricePerDay.setText(String.valueOf(data.price_per_day()));
        }


        if (data.price_per_week() != 0) {
            cbBorrowByWeek.setChecked(true);

            edtPricePerWeek.setVisibility(View.VISIBLE);
            edtPricePerWeek.setText(String.valueOf(data.price_per_week()));
        }


        if (data.price_per_month() != 0) {
            cbBorrowByMonth.setChecked(true);

            edtPricePerMonth.setVisibility(View.VISIBLE);
            edtPricePerMonth.setText(String.valueOf(data.price_per_month()));
        }


        edtMaterialName.setText(data.item_name());
        edtMaterialBrandName.setText(data.brand_name());


        spType.setSelection(adapterType.getPosition(data.category()));


    }


    private void initUi() {
        data.add(UnicodeHelper.getText("ကား"));
        data.add(UnicodeHelper.getText("လျှပ်စစ်ပစ္စည်းများ"));
        data.add(UnicodeHelper.getText("ခုံများ"));
        data.add(UnicodeHelper.getText("Camera And Accessories"));
        data.add(UnicodeHelper.getText("ပန်းကန်ခွက်ယောက်များ"));
        data.add("Others");
        adapterType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);


        spType.setAdapter(adapterType);


        if (spType.getSelectedItem().equals(UnicodeHelper.getText(getString(R.string.car))))
            extraCarLayout.setVisibility(View.VISIBLE);


        if (spType.getSelectedItem().equals(getString(R.string.camera)))
            extraCameraLayout.setVisibility(View.VISIBLE);


        if (spType.getSelectedItem().equals(UnicodeHelper.getText(getString(R.string.table)))) {
            edtMaterialBrandName.setVisibility(View.GONE);
            edtMaterialBrandName.setText("");
            edtMaterialName.setHint(UnicodeHelper.getText("ုံပုံစံ (ခေါက်စားပွဲ၊ စားပွဲဝိုင်း ၊ ထိုင်ခုံ စသည်)"));
        }

        if (spType.getSelectedItem().equals(UnicodeHelper.getText(getString(R.string.dining)))) {
            edtMaterialBrandName.setVisibility(View.GONE);
            edtMaterialBrandName.setText("");
            edtMaterialName.setHint(UnicodeHelper.getText("ပစ္စည်းအစုံလိုက် ထည့်ပါ (ပန်းကန်+ဇွန်း+ခက်ရင်း စသည်)"));
            edtPicture.setHint(UnicodeHelper.getText("ငှါးမည့်ပစ္စည်းအစုလိုက် ပုံထည့်ပေးပါ"));
        }

    }

    private void startSubscriptions() {
        add(ownerFormViewModel.getItemStream().subscribe(this::complete));
        add(RxView.clicks(btnSubmit).subscribe(ignored -> {


            if (validateInput()) {
                dialog.show();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                add(RxFirebaseStorage.putBytesWithOnComplete(FirebaseStorage.getInstance().getReference().child("item_image/" + UUID.randomUUID().toString()), data)
                        .subscribe(taskSnapshot -> {

                            if (taskSnapshot.getResult() != null) {
                                String img = taskSnapshot.getResult().toString();

                                if (!getIntent().hasExtra("data")) {
                                    ownerFormViewModel.addPostItem(getItemModel(img));
                                } else {
                                    ownerFormViewModel.updatePostItem(getItemModel(img), itemModel.item_id());
                                }
                            }

                        }));
                /*else {
                     ownerFormViewModel.updatePostItem(getItemModel());
                }*/

            } else {
                Toast.makeText(this, "Pls fill all input ", Toast.LENGTH_SHORT).show();
                dialog.hide();

            }

        }));
        add(RxView.touches(edtPicture, motionEvent -> motionEvent.getAction() == MotionEvent.ACTION_UP).subscribe(ignored -> checkPermission()));
        add(RxCompoundButton.checkedChanges(cbBorrowByDay).subscribe(isChecked -> {
            if (isChecked)
                textInputPricePerDay.setVisibility(View.VISIBLE);
            else {
                textInputPricePerDay.setVisibility(View.GONE);
                edtPricePerDay.setText(String.valueOf(0));
            }
        }));
        add(RxCompoundButton.checkedChanges(cbBorrowByWeek).subscribe(isChecked -> {
            if (isChecked)
                textInputPricePerWeek.setVisibility(View.VISIBLE);
            else {
                textInputPricePerWeek.setVisibility(View.GONE);
                edtPricePerWeek.setText(String.valueOf(0));
            }
        }));
        add(RxCompoundButton.checkedChanges(cbBorrowByMonth).subscribe(isChecked -> {
            if (isChecked)
                textInputPricePerMonth.setVisibility(View.VISIBLE);
            else {
                textInputPricePerMonth.setVisibility(View.GONE);
                edtPricePerMonth.setText(String.valueOf(0));
            }
        }));
        add(RxAdapterView.itemSelections(spType).subscribe(position -> {
            switch (data.get(position)) {
                case "ကား":
                    edtMaterialBrandName.setVisibility(View.VISIBLE);
                    edtMaterialBrandName.setHint("Brand Name");
                    edtMaterialName.setHint(getString(R.string.enter_model_number));
                    extraCarLayout.setVisibility(View.VISIBLE);
                    extraCameraLayout.setVisibility(View.GONE);
                    break;
                case "Camera And Accessories":
                    edtMaterialBrandName.setVisibility(View.VISIBLE);
                    edtMaterialBrandName.setHint("Brand Name");
                    edtMaterialName.setHint(getString(R.string.enter_model_number));
                    extraCameraLayout.setVisibility(View.VISIBLE);
                    extraCarLayout.setVisibility(View.GONE);
                    break;
                case "ခုံများ":
                    edtMaterialBrandName.setVisibility(View.GONE);
                    edtMaterialBrandName.setText("");
                    edtMaterialName.setHint(UnicodeHelper.getText("ခုံပုံစံ (ခေါက်စားပွဲ၊ စားပွဲဝိုင်း ၊ ထိုင်ခုံ စသည်)"));
                    extraCarLayout.setVisibility(View.GONE);
                    extraCameraLayout.setVisibility(View.GONE);
                    break;
                case "ပန်းကန်ခွက်ယောက်များ":
                    edtMaterialBrandName.setVisibility(View.GONE);
                    edtMaterialBrandName.setText("");
                    edtMaterialName.setHint(UnicodeHelper.getText("ပစ္စည်းအစုံလိုက် ထည့်ပါ (ပန်းကန်+ဇွန်း+ခက်ရင်း စသည်)"));
                    extraCarLayout.setVisibility(View.GONE);
                    extraCameraLayout.setVisibility(View.GONE);
                    edtPicture.setHint(UnicodeHelper.getText("ငှါးမည့်ပစ္စည်းအစုလိုက် ပုံထည့်ပေးပါ"));
                    break;
                default:
                    edtPicture.setHint("Picture");
                    edtMaterialBrandName.setVisibility(View.VISIBLE);
                    edtMaterialBrandName.setHint("Brand Name");
                    edtMaterialName.setHint(getString(R.string.enter_model_number));
                    extraCarLayout.setVisibility(View.GONE);
                    extraCameraLayout.setVisibility(View.GONE);
                    break;
            }


        }));
    }

    private boolean validateInput() {


        if (edtMaterialName.getText().toString().isEmpty() || edtMaterialName.getText().toString().equals("")) {
            edtMaterialName.setError("");
        }

        if (spType.getSelectedItem().toString().equals(UnicodeHelper.getText(getString(R.string.car)))) {
            return cbAgree.isChecked() && (!edtCarEngine.getText().toString().isEmpty() && !edtMaterialName.getText().toString().isEmpty() && !edtPicture.getText().toString().isEmpty() && !edtMaterialBrandName.getText().toString().isEmpty()) &&
                    (cbBorrowByDay.isChecked() || cbBorrowByMonth.isChecked() || cbBorrowByWeek.isChecked())
                    ;
        }

        if (spType.getSelectedItem().toString().equals(getString(R.string.camera))) {
            return cbAgree.isChecked() && (!edtCameraZoom.getText().toString().isEmpty() && !edtMaterialName.getText().toString().isEmpty() && !edtPicture.getText().toString().isEmpty() && !edtMaterialBrandName.getText().toString().isEmpty()
            ) && (cbBorrowByDay.isChecked() || cbBorrowByMonth.isChecked() || cbBorrowByWeek.isChecked());

        }

        if (!cbBorrowByDay.isChecked() && !cbBorrowByMonth.isChecked() && !cbBorrowByWeek.isChecked())
            Toast.makeText(this, "select checkbox at least one ", Toast.LENGTH_SHORT).show();


        if (spType.getSelectedItem().toString().equals(UnicodeHelper.getText(getString(R.string.table)))) {
            return cbAgree.isChecked() && !edtMaterialName.getText().toString().isEmpty() && !edtPicture.getText().toString().isEmpty() &&
                    (cbBorrowByDay.isChecked() || cbBorrowByMonth.isChecked() || cbBorrowByWeek.isChecked())
                    ;
        }

        if (spType.getSelectedItem().toString().equals(UnicodeHelper.getText(getString(R.string.dining)))) {
            return cbAgree.isChecked() && !edtMaterialName.getText().toString().isEmpty() && !edtPicture.getText().toString().isEmpty() &&
                    (cbBorrowByDay.isChecked() || cbBorrowByMonth.isChecked() || cbBorrowByWeek.isChecked())
                    ;
        }


        return cbAgree.isChecked() && (!edtMaterialName.getText().toString().isEmpty() && !edtPicture.getText().toString().isEmpty() && !edtMaterialBrandName.getText().toString().isEmpty()) &&
                (cbBorrowByDay.isChecked() || cbBorrowByMonth.isChecked() || cbBorrowByWeek.isChecked())
                ;
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), Constants.ALL_PERMISSOIN_RESULT);
            else
                startActivityForResult(getPickImageChooserIntent(), Constants.REQUEST_GET_SINGLE_FILE);
        }
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (Objects.requireNonNull(intent.getComponent()).getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private void complete(String s) {
        clearData();
        dialog.hide();
        showSnack(s, "Complete", v -> {

        }, Snackbar.LENGTH_LONG);

        HistoryActivity.start(this);
        finish();
    }

    private void clearData() {
        edtDamage.setText("");
        edtPricePerDay.setText("");
        edtPricePerWeek.setText("");
        edtPricePerMonth.setText("");
        edtMaterialName.setText("");
        edtMaterialBrandName.setText("");
        edtCameraZoom.setText("");
        edtCarEngine.setText("");
        edtCarSeat.setText("");
        edtPicture.setText("");
    }

    private ItemModel getItemModel(String img) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        StringBuilder date = new StringBuilder();

        if (spType.getSelectedItem().toString().equals(UnicodeHelper.getText(getString(R.string.car)))) {


            if (rbGasGroup.getCheckedRadioButtonId() == R.id.rbGas) {
                gas_or_oil = getString(R.string.gas_car);
            } else {
                gas_or_oil = getString(R.string.oil_car);
            }
        }

        if (spType.getSelectedItem().toString().equals(getString(R.string.camera))) {
            if (rbFocusGroup.getCheckedRadioButtonId() == R.id.rbFocusGood) {
                focus = getResources().getString(R.string.focus_good);
            } else {
                focus = getResources().getString(R.string.focus_bad);
            }
        }

        if (cbBorrowByDay.isChecked()) {
            date.append("Day ");
        }
        if (cbBorrowByWeek.isChecked()) {
            date.append("Week ");
        }
        if (cbBorrowByMonth.isChecked())
            date.append("Month ");

        if (edtPricePerDay.getText() != null && !edtPricePerDay.getText().toString().isEmpty())
            price_per_day = Integer.valueOf(edtPricePerDay.getText().toString());

        if (edtPricePerWeek.getText() != null && !edtPricePerWeek.getText().toString().isEmpty())
            price_per_week = Integer.valueOf(edtPricePerWeek.getText().toString());

        if (edtPricePerMonth.getText() != null && !edtPricePerMonth.getText().toString().isEmpty())
            price_per_month = Integer.valueOf(edtPricePerMonth.getText().toString());


        return ItemModel.create("", Constants.PENDING,  UnicodeHelper.getUnicode(edtMaterialName.getText().toString()),
                price_per_day, price_per_week, price_per_month,
                FirebaseAuth.getInstance().getUid(),  UnicodeHelper.getUnicode(edtMaterialBrandName.getText().toString()),  UnicodeHelper.getUnicode(edtCarEngine.getText().toString()),  UnicodeHelper.getUnicode(edtCarSeat.getText().toString()), gas_or_oil,  UnicodeHelper.getUnicode(edtCameraZoom.getText().toString())
                , focus, img, UnicodeHelper.getUnicode(spType.getSelectedItem().toString()), date.toString(),  UnicodeHelper.getUnicode(edtDamage.getText().toString()), sharedPreferences.getString(Constants.PREF_KEY_PHONE, ""), sharedPreferences.getString(Constants.PREF_KEY_NAME, ""));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_ownerform;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {


            if (getPickImageResultUri(data) != null) {
                filePath = getPickImageResultUri(data);

                new Thread(() -> {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
                        bitmap = Utils.rotateImageIfRequired(bitmap, filePath);
                        bitmap = Utils.getResizedBitmap(bitmap, 300);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgGallery.post(() -> {
                        imgGallery.setImageBitmap(bitmap);
                        imgGallery.setVisibility(View.VISIBLE);

                    });
                }).start();


                edtPicture.setText(filePath.toString());

            } else {


                bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");


                imgGallery.setImageBitmap(bitmap);
                imgGallery.setVisibility(View.VISIBLE);
                edtPicture.setText(getString(R.string.default_url_path));

            }

        }

    }


    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case Constants.ALL_PERMISSOIN_RESULT:
                for (String perms : permissionsToRequest) {
                    if (PermissionUtils.hasPermission(perms, this)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            DialogUtils.showMessageOKCancel(this, "These permissions are mandatory for the application. Please allow access.",
                                    (dialog, which) -> requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), Constants.ALL_PERMISSOIN_RESULT));
                            return;
                        }
                    }

                } else {
                    startActivityForResult(getPickImageChooserIntent(), Constants.REQUEST_GET_SINGLE_FILE);

                }

                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home)
            onBackPressed();


        return super.onOptionsItemSelected(item);
    }
}
