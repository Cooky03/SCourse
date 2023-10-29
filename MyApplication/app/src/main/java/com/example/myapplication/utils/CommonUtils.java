package com.example.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class CommonUtils {
    /**
     * 显示短信息
     * @param context 上下文
     * @param msg 要显示的信息
     */
    public static void showShortMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长信息
     * @param context 上下文
     * @param msg 要显示的信息
     */
    public static void showLongMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
