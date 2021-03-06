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

package com.avast.android.dialogs.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.avast.android.dialogs.core.BaseDialogBuilder;
import com.avast.android.dialogs.core.BaseDialogFragment;
import com.avast.android.dialogs.iface.IDialogCompletelyDrawnListener;
import com.avast.android.dialogs.iface.INegativeButtonDialogListener;
import com.avast.android.dialogs.iface.INeutralButtonDialogListener;
import com.avast.android.dialogs.iface.IPositiveButtonDialogListener;

import java.util.List;


/**
 * Dialog for displaying simple message, message with title or message with title and two buttons. Implement {@link
 * com.avast.android.dialogs.iface.ISimpleDialogListener} in your Fragment or Activity to rect on positive and negative button clicks. This class can
 * be extended and more parameters can be added in overridden build() method.
 *
 * @author David Vávra (david@inmite.eu)
 */
public class SimpleDialogFragment extends BaseDialogFragment {

    protected final static String ARG_MESSAGE = "message";
    protected final static String ARG_TITLE = "title";
    protected final static String ARG_POSITIVE_BUTTON = "positive_button";
    protected final static String ARG_NEGATIVE_BUTTON = "negative_button";
    protected final static String ARG_NEUTRAL_BUTTON = "neutral_button";

    static Typeface mFontRegular = null;
    static Typeface mFontMedium = null;
    static Bitmap mIcon = null;
    static boolean mFullWidth = false;
    static boolean mFullHeight = false;

    public static SimpleDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new SimpleDialogBuilder(context, fragmentManager, SimpleDialogFragment.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Key method for extending {@link com.avast.android.dialogs.fragment.SimpleDialogFragment}.
     * Children can extend this to add more things to base builder.
     */
    @Override
    protected BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
        final CharSequence title = getArguments().getCharSequence(ARG_TITLE);
            if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        final CharSequence message = getArguments().getCharSequence(ARG_MESSAGE);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }

        final CharSequence positiveButtonText = getArguments().getCharSequence(ARG_POSITIVE_BUTTON);
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                        listener.onPositiveButtonClicked(mRequestCode);
                    }
                    dismiss();
                }
            });
        }

        final CharSequence negativeButtonText = getArguments().getCharSequence(ARG_NEGATIVE_BUTTON);
        if (!TextUtils.isEmpty(negativeButtonText)) {
            builder.setNegativeButton(negativeButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (INegativeButtonDialogListener listener : getNegativeButtonDialogListeners()) {
                        listener.onNegativeButtonClicked(mRequestCode);
                    }
                    dismiss();
                }
            });
        }

        final CharSequence neutralButtonText = getArguments().getCharSequence(ARG_NEUTRAL_BUTTON);
        if (!TextUtils.isEmpty(neutralButtonText)) {
            builder.setNeutralButton(neutralButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (INeutralButtonDialogListener listener : getNeutralButtonDialogListeners()) {
                        listener.onNeutralButtonClicked(mRequestCode);
                    }
                    dismiss();
                }
            });
        }

        final Typeface fontRegular = getFontRegular();
        if (fontRegular != null) {
            builder.setFontRegular(fontRegular);
        }

        final Typeface fontMedium = getFontMedium();
        if (fontMedium != null) {
            builder.setFontMedium(fontMedium);
        }

        final Bitmap icon = getIcon();
        if (icon != null) {
            builder.setIcon(icon);
        }

        final boolean fullWidth = getFullWidth();
        builder.setFullWidth(fullWidth);

        final boolean fullHeight = getFullHeight();
        builder.setFullHeight(fullHeight);

        return builder;
    }

    //
    // Property getters and setters
    //

    public void setTitle(CharSequence title)
    {
        if (getTitleView() != null)
            getTitleView().setText(title);
    }

    public CharSequence getTitle()
    {
        if (getTitleView() != null)
            return getTitleView().getText();

        return null;
    }

    public void setMessage(CharSequence message)
    {
        if (getMessageView() != null)
            getMessageView().setText(message);
    }

    public CharSequence getMessage()
    {
        if (getMessageView() != null)
            return getMessageView().getText();

        return null;
    }

    public CharSequence getPositiveButtonText()
    {
        if (getPositiveBtnView() != null)
            return getPositiveBtnView().getText();

        if (getPositiveStackedBtnView() != null)
            return getPositiveStackedBtnView().getText();

        return null;
    }
    public void setPositiveBtnText(CharSequence message)
    {
        if (getPositiveBtnView() != null)
            getPositiveBtnView().setText(message);

        if (getPositiveStackedBtnView() != null)
            getPositiveStackedBtnView().setText(message);
    }

    public CharSequence getNegativeButtonText()
    {
        if (getNegativeBtnView() != null)
            return getNegativeBtnView().getText();

        if (getNegativeStackedBtnView() != null)
            return getNegativeStackedBtnView().getText();

        return null;
    }
    public void setNegativeBtnText(CharSequence message)
    {
        if (getNegativeBtnView() != null)
            getNegativeBtnView().setText(message);

        if (getNegativeStackedBtnView() != null)
            getNegativeStackedBtnView().setText(message);
    }

    public CharSequence getNeutralButtonText()
    {
        if (getNeutralBtnView() != null)
            return getNeutralBtnView().getText();

        if (getNeutralStackedBtnView() != null)
            return getNeutralStackedBtnView().getText();

        return null;
    }
    public void setNeutralBtnText(CharSequence message)
    {
        if (getNeutralBtnView() != null)
            getNeutralBtnView().setText(message);
    }

    public Typeface getFontRegular() {
        return mFontRegular;
    }

    public Typeface getFontMedium() {
        return mFontMedium;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public boolean getFullWidth() {
        return mFullWidth;
    }

    public boolean getFullHeight() {
        return mFullHeight;
    }

    /**
     * Get positive button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<IPositiveButtonDialogListener> getPositiveButtonDialogListeners() {
        return getDialogListeners(IPositiveButtonDialogListener.class);
    }

    /**
     * Get negative button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<INegativeButtonDialogListener> getNegativeButtonDialogListeners() {
        return getDialogListeners(INegativeButtonDialogListener.class);
    }

    /**
     * Get neutral button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<INeutralButtonDialogListener> getNeutralButtonDialogListeners() {
        return getDialogListeners(INeutralButtonDialogListener.class);
    }

    /**
     * Get dialog drawn listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<IDialogCompletelyDrawnListener> getDialogDrawnListeners() {
        return getDialogListeners(IDialogCompletelyDrawnListener.class);
    }

    //
    // View getters
    //

    public View getContentView() {
        return getDialogView(BaseDialogFragment.CONTENT);
    }

    public TextView getTitleView() {
        return (TextView) getDialogView(BaseDialogFragment.TITLE);
    }

    public TextView getMessageView() {
        return (TextView) getDialogView(BaseDialogFragment.MESSAGE);
    }

    public View getCustomView() {
        return getDialogView(BaseDialogFragment.CUSTOM_VIEW);
    }

    public Button getPositiveBtnView() {
        return (Button) getDialogView(BaseDialogFragment.POSITIVE_BUTTON);
    }

    public Button getNegativeBtnView() {
        return (Button) getDialogView(BaseDialogFragment.NEGATIVE_BUTTON);
    }

    public Button getNeutralBtnView() {
        return (Button) getDialogView(BaseDialogFragment.NEUTRAL_BUTTON);
    }

    public Button getPositiveStackedBtnView() {
        return (Button) getDialogView(BaseDialogFragment.POSITIVE_STCK_BUTTON);
    }

    public Button getNegativeStackedBtnView() {
        return (Button) getDialogView(BaseDialogFragment.NEGATIVE_STCK_BUTTON);
    }

    public Button getNeutralStackedBtnView() {
        return (Button) getDialogView(BaseDialogFragment.NEUTRAL_STCK_BUTTON);
    }

    public static class SimpleDialogBuilder extends BaseDialogBuilder<SimpleDialogBuilder> {

        private CharSequence mTitle;
        private CharSequence mMessage;
        private CharSequence mPositiveButtonText;
        private CharSequence mNegativeButtonText;
        private CharSequence mNeutralButtonText;

        protected SimpleDialogBuilder(Context context, FragmentManager fragmentManager, Class<? extends SimpleDialogFragment> clazz) {
            super(context, fragmentManager, clazz);
            mFontRegular = null;
            mFontMedium = null;
            mIcon = null;
            mFullWidth = false;
            mFullHeight = false;
        }

        @Override
        protected SimpleDialogBuilder self() {
            return this;
        }

        public SimpleDialogBuilder setTitle(int titleResourceId) {
            mTitle = mContext.getString(titleResourceId);
            return this;
        }


        public SimpleDialogBuilder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public SimpleDialogBuilder setMessage(int messageResourceId) {
            mMessage = mContext.getText(messageResourceId);
            return this;
        }

        public SimpleDialogBuilder setFontRegular(Typeface font) {
            mFontRegular = font;
            return this;
        }

        public SimpleDialogBuilder setFontMedium(Typeface font) {
            mFontMedium = font;
            return this;
        }

        public SimpleDialogBuilder setIcon(Bitmap bitmap) {
            mIcon = bitmap;
            return this;
        }

        public SimpleDialogBuilder setFullWidth(boolean set) {
            mFullWidth = set;
            return this;
        }

        public SimpleDialogBuilder setFullHeight(boolean set) {
            mFullHeight = set;
            return this;
        }

        /**
         * Allow to set resource string with HTML formatting and bind %s,%i.
         * This is workaround for https://code.google.com/p/android/issues/detail?id=2923
         */
        public SimpleDialogBuilder setMessage(int resourceId, Object... formatArgs) {
            mMessage = Html.fromHtml(String.format(Html.toHtml(new SpannedString(mContext.getText(resourceId))), formatArgs));
            return this;
        }

        public SimpleDialogBuilder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        public SimpleDialogBuilder setPositiveButtonText(int textResourceId) {
            mPositiveButtonText = mContext.getString(textResourceId);
            return this;
        }

        public SimpleDialogBuilder setPositiveButtonText(CharSequence text) {
            mPositiveButtonText = text;
            return this;
        }

        public SimpleDialogBuilder setNegativeButtonText(int textResourceId) {
            mNegativeButtonText = mContext.getString(textResourceId);
            return this;
        }

        public SimpleDialogBuilder setNegativeButtonText(CharSequence text) {
            mNegativeButtonText = text;
            return this;
        }

        public SimpleDialogBuilder setNeutralButtonText(int textResourceId) {
            mNeutralButtonText = mContext.getString(textResourceId);
            return this;
        }

        public SimpleDialogBuilder setNeutralButtonText(CharSequence text) {
            mNeutralButtonText = text;
            return this;
        }

        public SimpleDialogBuilder onDialogShown(final ViewTreeObserver.OnGlobalLayoutListener l)
        {
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            Bundle args = new Bundle();
            args.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, mMessage);
            args.putCharSequence(SimpleDialogFragment.ARG_TITLE, mTitle);
            args.putCharSequence(SimpleDialogFragment.ARG_POSITIVE_BUTTON, mPositiveButtonText);
            args.putCharSequence(SimpleDialogFragment.ARG_NEGATIVE_BUTTON, mNegativeButtonText);
            args.putCharSequence(SimpleDialogFragment.ARG_NEUTRAL_BUTTON, mNeutralButtonText);

            return args;
        }
    }
}
