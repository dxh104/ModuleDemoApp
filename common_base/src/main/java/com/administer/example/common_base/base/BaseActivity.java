package com.administer.example.common_base.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.administer.example.common_base.manager.ActivityManager;
import com.administer.example.common_base.util.LoadingDialogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity 基类，封装了一些通用的功能
 */

public abstract class BaseActivity<M extends BaseContract.IModel, V extends BaseContract.IView, P extends BasePresenter<M, V>> extends RxAppCompatActivity implements BaseContract.IView {

    //管理所有Activity
    public ActivityManager activityManager = ActivityManager.getInstance();
    // 当前类的弱引用对象
    private WeakReference<BaseActivity<M, V, P>> mRefActivity;
    public Context mContext;
    public LifecycleProvider<ActivityEvent> mProvider;
    private Unbinder bind;
    private P mPresenter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        mRefActivity = new WeakReference<>(this);
        //每启动一个Activity，将添加到集合中(销毁记得移除该Activity)
        activityManager.addActivity(this);//添加Activity
        mContext = this;
        mProvider = this;
        //设置布局
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setContentLayoutRes());
        bind = ButterKnife.bind(this);//绑定ButterKnife
        mPresenter = createPresenter();//创建Presenter
        if (getView() != null) {
            mPresenter.attach(mProvider, this, getView());//附加视图

        }
        //开始进行初始化
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        //hideNavigationBar();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();//分离视图
        }
        bind.unbind();//解除绑定
        LoadingDialogUtils.unInit();//注销加载框
        activityManager.removeActivity(this);//移除Activity
        mRefActivity.clear();
        super.onDestroy();//防空
    }

    /**
     * 设置布局资源
     *
     * @return
     */
    protected abstract @LayoutRes
    int setContentLayoutRes();

    /**
     * 初始化控件，交给子类实现
     */
    protected abstract void initView();

    /**
     * 初始化数据，交给子类实现
     */
    protected abstract void initData();

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 获取View
     *
     * @return
     */
    protected abstract V getView();

    /*
     *显示加载进度条
     */
    public void showLoading(String title) {
        if (mRefActivity.get() != null)
            LoadingDialogUtils.show(mRefActivity.get(), title);
    }

    /*
     *关闭加载进度条
     */
    public void dismissLoading() {
        LoadingDialogUtils.dismiss();
    }

    /**
     * 通用请求失败回调
     *
     * @param e
     */
    public void requestOnError(Throwable e) {
        Log.i("---------", "服务或网络异常" + e.getMessage());
        if (mRefActivity.get() != null)
            Toast.makeText(mRefActivity.get(), "服务或网络异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * finish Activity，提供给xml布局控件的onclick使用
     *
     * @param view
     */
    public void finishPage(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
     *设置全屏
     */
    public void setFullScreen() {
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE;//0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 设置透明状态栏（API21，5.0之后才能用）
     *
     * @param color       通知栏颜色，完全透明填 Color.TRANSPARENT 即可
     * @param isLightMode 是否为亮色模式（黑色字体，需要6.0 以后支持，否则显示无效）
     */
    protected void requestTranslucentStatusBar(int color, boolean isLightMode) {
        //大于5.0才设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //大于6.0 并且是亮色模式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightMode) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            getWindow().setStatusBarColor(color);
        }
    }


}