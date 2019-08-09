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

package com.ufrbuild.mh4x0f.painelufrb.ui.base;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import com.ufrbuild.mh4x0f.painelufrb.utils.NetworkUtils;

import butterknife.Unbinder;


public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {


    NetworkUtils networkUtils;

    private ProgressDialog progressDialog;
    private Unbinder unBinder;

    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void SetupAll(){
        setUpViewModel();
        setUpSnackbar();
        setUpToast();
        setUpProgressDialog();
    }

    private void setUpViewModel() {
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
    }

    private void setUpSnackbar() {
        viewModel.getSnackbarMessage().observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer snackbarMessage) {
                        showSnackbar(getString(snackbarMessage));
                    }
                });
    }

    private void setUpToast() {
        viewModel.getToastMessage().observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer toastMessage) {
                        showToast(getString(toastMessage));
                    }
                });
    }

    private void setUpProgressDialog() {
        viewModel.getProgressDialogStatus().observe(this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean status) {
                        if (status)
                            BaseActivity.this.showLoading();
                        else BaseActivity.this.hideLoading();
                    }
                });
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

//    @Override
//    public void onFragmentAttached() {
//
//    }
//
//    @Override
//    public void onFragmentDetached(String tag) {
//
//    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isNetworkConnected() {
        if (networkUtils.isNetworkConnected(this))
            return true;
        else {
            showSnackbar(getString(R.string.no_internet));
            return false;
        }
    }

    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    protected void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG);

        View view = snackbar.getView();

        TextView snackTV = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        snackTV.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));

        snackbar.show();
    }

    protected void showToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    protected abstract void setUp();

    @Override
    protected void onDestroy() {
        if (unBinder != null)
            unBinder.unbind();
        super.onDestroy();
    }
}

