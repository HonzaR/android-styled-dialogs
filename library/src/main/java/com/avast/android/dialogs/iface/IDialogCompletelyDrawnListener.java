package com.avast.android.dialogs.iface;

/**
 * Implement this interface in Activity or Fragment to get notified when dialog is completely drawn (show).
 *
 * @author Honza Rychnovský (honzar@appsdevteam.com)
 */
public interface IDialogCompletelyDrawnListener {

    public void onDialogCompletelyDrawn(int requestCode);
}
