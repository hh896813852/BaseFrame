package com.qhc.windpower.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by any on 17/3/31.
 */

public class ToastHelper {

    public static void showToast(Context context, String message, boolean longToast) {
//        Toast mToast = new Toast(context);
//        View v = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
//        TextView textView = (TextView) v.findViewById(R.id.message);
//        textView.setText(message);
//        mToast.setDuration(longToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.setView(v);
//        mToast.setText(message);
//        mToast.show();
        Toast.makeText(context, message,
                longToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, false);
    }

}
