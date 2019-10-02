/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019 UFRBuild - Marcos Bomfim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.donate;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.splash.SplashViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import com.ufrbuild.mh4x0f.painelufrb.utils.NetworkUtils;

import javax.inject.Inject;

public class DonateActivity extends BaseActivity<DonateViewModel> implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;
    @Inject
    DataManager mDataManager;

    DonateViewModel viewModel;
    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    CommonUtils utils;

    @BindView(R.id.btn_purchase)
    Button mBtnPurchase_1;

    @BindView(R.id.btn_purchase_more)
    Button mBtnPurchase_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mDataManager.getPrefs().getBoolean(getString(R.string.theme_key))) {
            setTheme(R.style.Darktheme);
        }
        else  setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_donate);



        ButterKnife.bind(this);

        // get support action bar mode
        utils.getSupportActionBar(this);

        bp = new BillingProcessor(this, getString(R.string.key_license), this);
        bp.initialize();

        if (bp.isPurchased(getString(R.string.product_id_payment_1))) {
            mBtnPurchase_1.setEnabled(false);
            mBtnPurchase_1.setAlpha(0.4f);
        }
        else if (bp.isPurchased(getString(R.string.product_id_payment_2))) {
            mBtnPurchase_2.setEnabled(false);
            mBtnPurchase_2.setAlpha(0.4f);
        }


        mBtnPurchase_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkConnected(getApplicationContext())){
                    showSnackbar(getString(R.string.no_internet));
                    return;
                }
                boolean isAvailable =  bp.isIabServiceAvailable(getApplicationContext());
                if (!isAvailable) {
                    showSnackbar(getString(R.string.msg_error_services_googleplay));
                    return;
                }
                boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
                if(isOneTimePurchaseSupported) {
                    bp.purchase(DonateActivity.this,getString(R.string.product_id_payment_1));
                }

            }
        });


        mBtnPurchase_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkConnected(getApplicationContext())){
                    showSnackbar(getString(R.string.no_internet));
                    return;
                }
                boolean isAvailable =  bp.isIabServiceAvailable(getApplicationContext());
                if (!isAvailable) {
                    showSnackbar(getString(R.string.msg_error_services_googleplay));
                    return;
                }
                boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
                if(isOneTimePurchaseSupported) {
                    bp.purchase(DonateActivity.this,getString(R.string.product_id_payment_2));
                }

            }
        });

    }

    @Override
    public DonateViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(DonateViewModel.class);
        return viewModel;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        if (productId == getString(R.string.product_id_payment_1)){
            mBtnPurchase_1.setEnabled(false);
            mBtnPurchase_1.setAlpha(0.4f);

        }else if (productId == getString(R.string.product_id_payment_2)){
            mBtnPurchase_2.setEnabled(false);
            mBtnPurchase_2.setAlpha(0.4f);
        }

        showToast(getString((R.string.toast_message_donePayment)));

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
