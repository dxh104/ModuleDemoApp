package com.administer.example.common_base.util;

import android.app.Activity;
import android.app.ProgressDialog;

import java.lang.ref.WeakReference;

/**
 * Created by XHD on 2019/03/27
 */
public class LoadingDialogUtils {
    /**
     * 数据访问等待框
     */
    private static ProgressDialog loadingDialog;
    private static WeakReference<Activity> reference;

    private static void init(Activity act, String title) {
        if (loadingDialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>(act);
            loadingDialog = new ProgressDialog(reference.get());
            loadingDialog.setMessage(title + "...");
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    public static void setCancelable(boolean b) {
        if (loadingDialog == null) return;
        loadingDialog.setCancelable(b);
    }

    /**
     * 显示等待框
     */
    public static void show(Activity act, String title) {
        init(act, title);
        loadingDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void dismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    /**
     * 注销加载框，避免发生内存泄露
     */
    public static void unInit() {
        dismiss();
        if (reference!=null&&reference.get() != null)
            reference.clear();
        loadingDialog = null;
        reference = null;
    }
}
