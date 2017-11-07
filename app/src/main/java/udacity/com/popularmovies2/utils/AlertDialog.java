package udacity.com.popularmovies2.utils;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;

public class AlertDialog {

    private static final String LOG_TAG = AlertDialog.class.getSimpleName();

    public static void createAlert(Context context, String title, String msg,
                                   String positiveButtonText, OnClickListener positiveButtonListener,
                                   String negativeButtonText, OnClickListener negativeButtonListener) {

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(title).setCancelable(false).setMessage(msg)
                .setPositiveButton(positiveButtonText, positiveButtonListener);

        if (!TextUtils.isEmpty(negativeButtonText)) {
            alertDialog.setNegativeButton(negativeButtonText,
                    negativeButtonListener);
        }
        alertDialog.show();

    }

    public static void createSingleChoiceItemsAlert(Context context, String title, String[] items,
                                                    OnClickListener itemClickListener) {

        android.support.v7.app.AlertDialog.Builder alertDialog =
                new android.support.v7.app.AlertDialog.Builder(context).setTitle(title)
                        .setSingleChoiceItems(items, -1, itemClickListener);
        alertDialog.show();
    }
}