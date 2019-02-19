package com.team28.borrow.di.module;

import com.team28.borrow.presentation.feature.signin.fragment.SendPhoneNumFragment;
import com.team28.borrow.presentation.feature.signin.fragment.VerificationPhoneFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class SigninFragmentBuilder {

    @ContributesAndroidInjector
    abstract SendPhoneNumFragment contributeSendPhoneNumFragment();


    @ContributesAndroidInjector
    abstract VerificationPhoneFragment contributeVerificationPhoneFragment();


}