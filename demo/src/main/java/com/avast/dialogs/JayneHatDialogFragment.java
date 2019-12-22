/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.avast.dialogs;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.avast.android.dialogs.core.BaseDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IPositiveButtonDialogListener;

/**
 * Sample implementation of custom dialog by extending {@link SimpleDialogFragment}.
 *
 * @author David Vávra (david@inmite.eu)
 */
public class JayneHatDialogFragment extends SimpleDialogFragment {

    public static String TAG = "jayne";

    public static void show(FragmentActivity activity) {
        new JayneHatDialogFragment().show(activity.getSupportFragmentManager(), TAG);
    }

    @Override
    public int getTheme() {
        return R.style.JayneHatDialogTheme;
    }

    @Override
    public BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
        builder.setTitle("Jayne's hat");
        builder.setMessage("A man walks down the street in that hat, people know he's not afraid of anything.");
        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.view_jayne_hat, null));
        builder.setPositiveButton("I want one", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                    listener.onPositiveButtonClicked(mRequestCode);
                }
                dismiss();
            }
        });
        builder.setNegativeButton("I want", null);
        builder.setNeutralButton("I", null);
        builder.setFullWidth(true);
        builder.setFullHeight(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setMessage("new message");
                setTitle("new title");
                setPositiveBtnText("new positive");
                setNegativeBtnText("new negative");
                setNeutralBtnText("new neutral");
            }
        }, 2500);

        return builder;
    }
}
