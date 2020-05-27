package com.administer.example.module_user.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.administer.example.module_user.R;
import com.alibaba.android.arouter.facade.annotation.Route;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/module_user/RegisterActivity")
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
