package com.administer.example.moduledemoapp;

import android.os.Bundle;
import android.widget.Button;

import com.administer.example.common_base.base.BaseActivity;
import com.administer.example.common_base.base.BaseContract;
import com.administer.example.common_base.base.BasePresenter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/moduledemoapp/SplashActivity")
public class SplashActivity extends BaseActivity {


    @BindView(R.id.btnOpenLoginPage)
    Button btnOpenLoginPage;

    @OnClick(R.id.btnOpenLoginPage)
    public void onViewClicked() {
        ARouter.getInstance().build("/module_user/LoginActivity").navigation();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected BaseContract.IView getView() {
        return null;
    }


}
