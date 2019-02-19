package com.team28.borrow.presentation.feature.signin.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.custom.Pinview;
import com.team28.borrow.presentation.feature.main.MainActivity;
import com.team28.borrow.presentation.feature.signin.ContactViewModel;
import com.team28.borrow.presentation.model.ContactModel;
import com.team28.borrow.rxfirebase.RxFirebaseAuth;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;


public class VerificationPhoneFragment extends BaseFragment {


    @Inject
    ViewModelProvider.Factory factory;

    ContactViewModel viewModel;

    @BindView(R.id.pvCode)
    Pinview pvCode;

    @BindView(R.id.btnVerify)
    Button btnVerify;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private String mVerificationId;

    private ProgressDialog dialog;

    //firebase auth object
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    String name = "", mobile = "";


    public static VerificationPhoneFragment newInstance(Context context, String string, String name) {
        VerificationPhoneFragment phoneFragment = new VerificationPhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", string);
        bundle.putString("name", name);
        phoneFragment.setArguments(bundle);
        return phoneFragment;
    }


    public VerificationPhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    pvCode.setValue(code);
                    //verifying the code
                    verifyVerificationCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                mResendToken = forceResendingToken;
            }
        };

        Bundle bundle = getArguments();

        if (bundle != null) {
            mobile = bundle.getString("phone");
            name = bundle.getString("name");
        }


        sendVerificationCode(mobile);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.verifying));

        viewModel = ViewModelProviders.of(this, factory).get(ContactViewModel.class);


        startSubscription();

    }

    private void startSubscription() {
        add(RxView.clicks(btnVerify).subscribe(ignored -> verify()));
        add(viewModel.addContactStream().subscribe(this::changeNext));
    }

    private void changeNext(String s) {
        this.showSnack(s, "Dismis", null, Snackbar.LENGTH_LONG);
        MainActivity.start(getContext());
    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_verification_phone;
    }


    void verify() {
        String code = pvCode.getValue();
        if (code.isEmpty() || code.length() < 6) {
            showSnack("Enter valid code", "Error", null, Snackbar.LENGTH_SHORT);
            pvCode.setFocusable(true);
            return;
        }

        //verifying the code entered manually
        verifyVerificationCode(code);
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+95" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


    private void verifyVerificationCode(String otp) {
        dialog.show();
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
      /*  add(RxFirebaseAuth.signInWithCredential(mAuth, credential).subscribe(authResult -> {
            if (authResult != null) {
                viewModel.addContact(new ContactModel(name, mobile), authResult.getUser().getUid());
            }

        }, throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()));
*/
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = task.getResult().getUser();

                        viewModel.addContact(new ContactModel(name, mobile), user.getUid());

                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(getContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
