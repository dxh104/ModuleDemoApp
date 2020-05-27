package com.administer.example.common_base.base;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.lang.ref.WeakReference;

/**
 * Created by XHD on 2019/06/01
 */
public abstract class BasePresenter<M extends BaseContract.IModel, V extends BaseContract.IView> implements BaseContract.IPresenter<V> {
    private WeakReference<LifecycleProvider<ActivityEvent>> mRefProvider;//弱引用provider
    private WeakReference<Context> mRefContext;//弱引用视图上下文
    private WeakReference<V> mRefView;//弱引用视图接口
    private M mModel;


    /**
     * @param context 视图层上下文
     * @param view    视图层接口
     *                附加视图  activty退出了，某个接口进网络操作，存在对当前上下午引用，存在内存泄漏，使用WeakReference解决。
     */
    public void attach(LifecycleProvider<ActivityEvent> provider, Context context, V view) {
        mRefProvider = new WeakReference<>(provider);
        mRefView = new WeakReference<>(view);
        mRefContext = new WeakReference<>(context);
        mModel = createModel();
    }

    //分离视图
    @CallSuper
    public void detach() {
        if (mRefProvider != null) {
            mRefProvider.clear();
            mRefProvider = null;
        }
        if (mRefView != null) {
            mRefView.clear();
            mRefView = null;
        }
        if (mRefContext != null) {
            mRefContext.clear();
            mRefContext = null;
        }
    }

    //获取provider
    public LifecycleProvider<ActivityEvent> getLifecycleProvider() {
        if (mRefProvider != null) {
            return mRefProvider.get();
        }
        return null;
    }

    //获取视图层上下文
    protected Context getMcontext() {
        if (mRefContext != null) {
            return mRefContext.get();
        }
        return null;
    }

    //创建Model对象
    protected abstract M createModel();

    //获取Model对象
    protected M getModel() {
        return mModel;
    }


    //获取视图层接口
    protected V getView() {
        if (mRefView != null) {
            return mRefView.get();
        }
        return null;
    }


}
