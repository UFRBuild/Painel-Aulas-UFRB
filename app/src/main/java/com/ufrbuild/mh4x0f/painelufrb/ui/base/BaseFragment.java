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
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;


public abstract class BaseFragment<V extends BaseViewModel> extends DaggerFragment {

    private BaseActivity activity;
    private Unbinder unBinder;

    private V viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetupAll();
    }

    public void SetupAll(){
        setUpSnackbar();
        setUpToast();
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = getViewModel();
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    private void setUpSnackbar() {
        viewModel.getSnackbarMessage().observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer snackbarMessage) {
                        if (activity != null)
                            activity.showSnackbar(getString(snackbarMessage));
                    }
                });
    }

    private void setUpToast() {
        viewModel.getToastMessage().observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer toastMessage) {
                        if (activity != null)
                            activity.showToast(getString(toastMessage));
                    }
                });
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public void setActivity(BaseActivity ac){
        activity = ac;
    }

    protected abstract void setUp(View view);
    public abstract boolean onActivityBackPress();

    @Override
    public void onDestroy() {
        if (unBinder != null)
            unBinder.unbind();
        super.onDestroy();
    }
}