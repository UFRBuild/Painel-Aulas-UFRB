package com.ufrbuild.mh4x0f.painelufrb.utils.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;


public class ToastMessage extends SingleLiveEvent<Integer> {

    public void observe(LifecycleOwner owner, final ToastObserver observer) {
        super.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                if (s != null)
                    return;

                observer.onNewMessage(s);
            }
        });
    }


    public interface ToastObserver {
        void onNewMessage(@StringRes Integer toastMessage);
    }
}