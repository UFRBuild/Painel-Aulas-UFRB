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

package com.ufrbuild.mh4x0f.painelufrb.utils.interactors;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

public class ProgressDialogStatus extends SingleLiveEvent<Boolean> {

    public void observe(LifecycleOwner owner, final ProgressDialogObserver observer) {
        super.observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                if (s != null)
                    return;

                observer.onChanged(s);
            }
        });
    }


    public interface ProgressDialogObserver {
        /**
         * Called when Progress Dialog's state is changed.
         * @param status The Progress Dialog's active state, non-null.
         */

        void onChanged(Boolean status);
    }
}