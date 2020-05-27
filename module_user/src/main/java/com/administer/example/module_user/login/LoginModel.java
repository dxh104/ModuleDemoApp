package com.administer.example.module_user.login;

import android.text.TextUtils;

import com.administer.example.common_base.base.BaseContract;
import com.administer.example.module_user.entity.LoginResult;

/**
 * Created by XHD on 2020/05/26
 */
public class LoginModel implements LoginContract.IModel {

    private String realAccount = "abc";
    private String realPassword = "123";


    @Override
    public void userLogin(String account, String password, OnNetRequest<LoginResult> userLogin) {
        if (TextUtils.equals(account, realAccount) && TextUtils.equals(password, realPassword)) {
            userLogin.onNext(new LoginResult(200, "密码正确"), "onNext----->登录成功");
        } else {
            userLogin.onNext(new LoginResult(400, "密码错误"), "onNext----->登录失败");
        }
        userLogin.onComplete();
    }
}
