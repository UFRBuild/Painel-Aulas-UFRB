package com.ufrbuild.mh4x0f.painelufrb.utils.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

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