package com.administer.example.module_user.login;


import com.administer.example.common_base.base.BaseContract;
import com.administer.example.module_user.entity.LoginResult;

/**
 * Created by XHD on 2020/05/26
 */
public interface LoginContract {
    interface IModel extends BaseContract.IModel {
        void userLogin(String account, String password, OnNetRequest<LoginResult> userLogin);//模拟用户登录(请求接口)
    }

    //登录结果回调
    interface IView extends BaseContract.IView {
        void onShowLoginResult(LoginResult result);//展示登录结果
    }

    interface IPresenter extends BaseContract.IPresenter<IView> {
        void excuteLoginRequest(String account, String password);//执行登录请求
    }


}
