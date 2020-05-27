package com.administer.example.common_base.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.administer.example.common_base.manager.FragmentManager;
import com.administer.example.common_base.util.LoadingDialogUtils;
import com.trello.navi2.component.support.NaviFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XHD on 2019/04/02
 */

public abstract class BaseFragment<M extends BaseContract.IModel, V extends BaseContract.IView, P extends BasePresenter<M, V>> extends NaviFragment implements BaseContract.IView {

    //管理BaseFragment集合
    public FragmentManager fragmentManager = FragmentManager.getInstance();
    private WeakReference<BaseFragment<M, V, P>> mRefFragmnet;
    private Context mContext;
    private View mContentView;
    private Unbinder bind;
    private P mPresenter;
    public LifecycleProvider<ActivityEvent> mProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager.addFragment(this);//添加Fragment
    }

    @SuppressLint({"NewApi", "MissingSuperCall"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);//填充布局
        bind = ButterKnife.bind(this, mContentView);//绑定View
        mRefFragmnet = new WeakReference<>(this);//弱引用当前对象
        mContext = getContext();
        mProvider = NaviLifecycle.createActivityLifecycleProvider(this);
        mPresenter = createPresenter();//创建Presenter
        if (getFragmentView() != null) {
            mPresenter.attach(mProvider, getMContext(), getFragmentView());//附加视图
        }
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();//设置View
        setUpData();//设置数据
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();//分离视图
        }
        if (bind != null) {
            bind.unbind();//取消绑定
        }
        LoadingDialogUtils.unInit();//注销加载框
        fragmentManager.removeFragment(this);//移除Fragment
        mRefFragmnet.clear();
        super.onDestroy();//防空
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID * * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

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
    protected abstract V getFragmentView();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();


    public View getContentView() {
        return mContentView;
    }

    protected P getPresenter() {
        return mPresenter;
    }

    public Context getMContext() {
        return mContext;
    }

    /*
     *显示加载进度条
     */
    public void showLoading(String title) {
        if (mRefFragmnet.get() != null)
            LoadingDialogUtils.show(mRefFragmnet.get().getActivity(), title);
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
        if (mRefFragmnet.get() != null)
            Toast.makeText(mRefFragmnet.get().mContext, "服务或网络异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

