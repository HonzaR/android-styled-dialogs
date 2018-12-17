package com.avast.android.dialogs.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avast.android.dialogs.R;
import com.avast.android.dialogs.core.BaseDialogBuilder;
import com.avast.android.dialogs.core.BaseDialogFragment;

/**
 * Simple progress dialog that shows indeterminate progress bar together with message and dialog title (optional).<br/>
 * <p>
 * To show the dialog, start with {@link #createBuilder(android.content.Context, android.support.v4.app.FragmentManager)}.
 * </p>
 * <p>
 * Dialog can be cancelable - to listen to cancellation, activity or target fragment must implement {@link com.avast.android.dialogs.iface.ISimpleDialogCancelListener}
 * </p>
 *
 * @author Tomas Vondracek
 */
public class ProgressDialogFragment extends BaseDialogFragment {

    protected final static String ARG_MESSAGE = "message";
    protected final static String ARG_TITLE = "title";

    static Typeface mFontRegular = null;
    static Typeface mFontMedium = null;
    static Bitmap mIcon = null;

    public static ProgressDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new ProgressDialogBuilder(context, fragmentManager);
    }

    @Override
    protected Builder build(Builder builder) {
        final LayoutInflater inflater = builder.getLayoutInflater();
        final View view = inflater.inflate(R.layout.sdl_progress, null, false);
        final TextView tvMessage = (TextView) view.findViewById(R.id.sdl_message);

        if (mFontRegular != null) {
            builder.setFontRegular(mFontRegular);
            tvMessage.setTypeface(mFontRegular);
        }
        if (mFontMedium != null) {
            builder.setFontMedium(mFontMedium);
            tvMessage.setTypeface(mFontMedium);
        }

        if (mIcon != null) {
            builder.setIcon(mIcon);
        }

        tvMessage.setText(getArguments().getCharSequence(ARG_MESSAGE));

        builder.setView(view);

        builder.setTitle(getArguments().getCharSequence(ARG_TITLE));

        return builder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) {
            throw new IllegalArgumentException("use ProgressDialogBuilder to construct this dialog");
        }
    }

    //
    // View getters
    //

    public ViewGroup getContentView() {
        return (ViewGroup) getDialogView(BaseDialogFragment.CUSTOM_VIEW);
    }

    public TextView getTitleView() {
        return (TextView) getDialogView(BaseDialogFragment.TITLE);
    }

    public TextView getMessageView() {
        TextView msg = null;
        if (getContentView() != null) {
            msg = (TextView) getContentView().findViewById(R.id.sdl_message);
        }
        return msg;
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

    public static class ProgressDialogBuilder extends BaseDialogBuilder<ProgressDialogBuilder> {

        private CharSequence mTitle;
        private CharSequence mMessage;

        protected ProgressDialogBuilder(Context context, FragmentManager fragmentManager) {
            super(context, fragmentManager, ProgressDialogFragment.class);
            mFontRegular = null;
            mFontMedium = null;
            mIcon = null;
        }

        @Override
        protected ProgressDialogBuilder self() {
            return this;
        }

        public ProgressDialogBuilder setTitle(int titleResourceId) {
            mTitle = mContext.getString(titleResourceId);
            return this;
        }


        public ProgressDialogBuilder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public ProgressDialogBuilder setMessage(int messageResourceId) {
            mMessage = mContext.getString(messageResourceId);
            return this;
        }

        public ProgressDialogBuilder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        public ProgressDialogBuilder setFontRegular(Typeface font) {
            mFontRegular = font;
            return this;
        }

        public ProgressDialogBuilder setFontMedium(Typeface font) {
            mFontMedium = font;
            return this;
        }

        public ProgressDialogBuilder setIcon(Bitmap icon) {
            mIcon = icon;
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            Bundle args = new Bundle();
            args.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, mMessage);
            args.putCharSequence(SimpleDialogFragment.ARG_TITLE, mTitle);

            return args;
        }
    }
}
