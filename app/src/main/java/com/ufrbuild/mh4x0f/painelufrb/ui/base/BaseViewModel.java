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
import android.arch.lifecycle.ViewModel;
import android.support.annotation.StringRes;
import com.ufrbuild.mh4x0f.painelufrb.App;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.utils.NetworkUtils;
import com.ufrbuild.mh4x0f.painelufrb.utils.interactors.ProgressDialogStatus;
import com.ufrbuild.mh4x0f.painelufrb.utils.interactors.SnackbarMessage;
import com.ufrbuild.mh4x0f.painelufrb.utils.interactors.ToastMessage;


public abstract class BaseViewModel extends ViewModel {

    private NetworkUtils networkUtils;

    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final ToastMessage toastMessage = new ToastMessage();
    private final ProgressDialogStatus progressDialogStatus = new ProgressDialogStatus();

    private DataManager dataManager;


    public BaseViewModel(DataManager dataManager,
                         NetworkUtils networkUtils) {
        this.dataManager = dataManager;
        this.networkUtils = networkUtils;
    }

    protected BaseViewModel() {
    }


    public DataManager getDataManager() {
        return dataManager;
    }


    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public ToastMessage getToastMessage() {
        return toastMessage;
    }

    public ProgressDialogStatus getProgressDialogStatus() {
        return progressDialogStatus;
    }

    public void showSnackbarMessage(@StringRes Integer message) {
        snackbarMessage.setValue(message);
    }

    public void showToastMessage(@StringRes Integer message) {
        toastMessage.setValue(message);
    }

    public boolean isInternet() {
        if (networkUtils != null && networkUtils.isNetworkConnected(App.getInstance().getApplicationContext()))
            return true;

        showSnackbarMessage(R.string.no_internet);
        return false;
    }

    public void showLoading() {
        progressDialogStatus.setValue(true);
    }

    public void hideLoading() {
        progressDialogStatus.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
