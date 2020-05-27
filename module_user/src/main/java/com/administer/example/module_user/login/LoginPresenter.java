package com.administer.example.module_user.login;


import android.util.Log;

import com.administer.example.common_base.base.BaseContract;
import com.administer.example.common_base.base.BasePresenter;
import com.administer.example.module_user.entity.LoginResult;

/**
 * Created by XHD on 2020/05/26
 */
public class LoginPresenter extends BasePresenter<LoginContract.IModel, LoginContract.IView> implements LoginContract.IPresenter {
    @Override
    protected LoginContract.IModel createModel() {
        return new LoginModel();
    }

    @Override
    public void excuteLoginRequest(String account, String password) {
        getModel().userLogin(account, password, new BaseContract.IModel.OnNetRequest<LoginResult>() {
            @Override
            public void onNext(LoginResult result, String message) {
                getView().onShowLoginResult(result);
                Log.i("---------->", "onNext: ");
            }

            @Override
            public void onError(String message) {
                Log.i("---------->", "onError: " + message);
            }

            @Override
            public void onComplete() {
                Log.i("---------->", "onComplete: ");
            }
        });
    }
}
