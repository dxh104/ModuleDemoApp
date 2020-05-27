package com.administer.example.module_user.login;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.administer.example.common_base.base.BaseActivity;
import com.administer.example.module_user.R;
import com.administer.example.module_user.R2;
import com.administer.example.module_user.entity.LoginResult;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;

// 用户模块:用户操作/业务相关界面
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/module_user/LoginActivity")
public class LoginActivity extends BaseActivity<LoginContract.IModel, LoginContract.IView, LoginPresenter> implements LoginContract.IView {
    //swtich需要传常量，@IdRes注解必须为常量,
    // 模块化要用R2的常量(编译整合时view.getId()【R】的值防止会冲突,会重新生成新的值)，注解处理器会用R2的常量,生成获取R中对应常量的方法
    @BindView(R2.id.btnLogin)
    Button btnLogin;
    @BindView(R2.id.btnRegister)
    Button btnRegister;

    @OnClick({R2.id.btnLogin, R2.id.btnRegister})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btnLogin) {
            getPresenter().excuteLoginRequest("abc", "123");
        } else if (i == R.id.btnRegister) {
            ARouter.getInstance().build("/module_user/RegisterActivity").navigation();
        }
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected LoginContract.IView getView() {
        return this;
    }


    @Override
    public void onShowLoginResult(LoginResult result) {
        Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        ARouter.getInstance().build("/module_home/HomeActivity").navigation();
    }

}
