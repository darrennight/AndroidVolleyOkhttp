package com.breadtrip.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.breadtrip.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.ui.view
 * @Description: 抽象4层基本布局
 * @date 15/8/19 下午7:58
 */
public class BaseFrameLayout extends FrameLayout {

    /**
     * 注解方式int to enum
     */
    @IntDef({STATE_LOADING, STATE_EMPTY, STATE_DATA, STATE_ERROR, STATE_ALL_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    /**
     * 加载状态
     */
    public static final int STATE_LOADING = 0;
    /**
     * 空数据状态
     */
    public static final int STATE_EMPTY = 1;
    /**
     * 数据状态
     */
    public static final int STATE_DATA = 2;
    /**
     * 错误状态
     */
    public static final int STATE_ERROR = 3;
    /**
     * 空白状态
     */
    public static final int STATE_ALL_EMPTY = 4;

    protected Context mContext;

    /**
     * 当前状态
     */
    @State
    protected int mCurrentState = STATE_ALL_EMPTY;

    private ViewStub mVsLoading, mVsEmpty, mVsError, mVsData;

    protected View mLoadingView, mEmptyView, mDataView, mErrorView;

    private OnStateChangeListener mOnStateChangeListener;

    private int mLoadingLayoutId, mEmptyLayoutId, mErrorLayoutId, mDataLayoutId;

    public BaseFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void readAttribute(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseFrameLayout);
        try {
            mLoadingLayoutId = a.getResourceId(R.styleable.BaseFrameLayout_loadingLayout, 0);
            mDataLayoutId = a.getResourceId(R.styleable.BaseFrameLayout_dataLayout, 0);
            mEmptyLayoutId = a.getResourceId(R.styleable.BaseFrameLayout_emptyLayout, 0);
            mErrorLayoutId = a.getResourceId(R.styleable.BaseFrameLayout_errorLayout, 0);
        } finally {
            a.recycle();
        }
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        readAttribute(context,attrs);
        initViews();
        initListeners();
    }

    /**
     * 设置数据布局id, <b>在显示布局前设置有效</b>
     *
     * @param dataLayoutId 数据布局id
     */
    public void setDataLayoutResId(int dataLayoutId) {
        this.mDataLayoutId = dataLayoutId;
    }

    /**
     * 设置空界面id， <b>在显示布局前设置有效</b>
     *
     * @param emptyLayoutId 空界面布局id
     */
    public void setEmptyLayoutResId(int emptyLayoutId) {
        this.mEmptyLayoutId = emptyLayoutId;
    }

    /**
     * 设置错误界面id，<b>在显示布局前设置有效</b>
     *
     * @param errorLayoutId 错误界面布局id
     */
    public void setErrorLayoutResId(int errorLayoutId) {
        this.mErrorLayoutId = errorLayoutId;
    }

    /**
     * 设置加载界面id,<b>在显示布局前设置有效</b>
     *
     * @param loadingLayoutId 加载界面id
     */
    public void setLoadingLayoutResId(int loadingLayoutId) {
        this.mLoadingLayoutId = loadingLayoutId;
    }

    /**
     * 基本布局，子view可以重写
     *
     * @return 根布局res id
     */
    protected int getRootLayoutResId() {
        return R.layout.view_abs_layout;
    }

    /**
     * 获得当前状态
     *
     * @return 当前状态
     */
    @State
    public int getCurrentState() {
        return mCurrentState;
    }

    /**
     * 显示状态的布局
     *
     * @param state 状态
     */
    public void showState(@State int state) {
        showStateWithMsg(state, null);
    }

    /**
     * 显示状态的布局
     *
     * @param state 状态
     * @param msg   消息
     */
    public void showStateWithMsg(@State int state, String msg) {
        if (state == mCurrentState) {
            if (TextUtils.isEmpty(msg)) {
                // 同种状态，没有文案，不进行view切换
                return;
            }
        }

        @State int oldState = mCurrentState;
        this.mCurrentState = state;
        checkAndInitCurrentView(state, msg);
        showCurrentView(state);
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChange(oldState, mCurrentState);
        }
    }

    private void checkAndInitCurrentView(@State int state, String msg) {
        switch (state) {
            case STATE_LOADING:
                if (mLoadingView == null) {
                    if (mLoadingLayoutId != 0) {
                        mVsLoading.setLayoutResource(mLoadingLayoutId);
                    }
                    mLoadingView = mVsLoading.inflate();
                }
                prepareLoadingView(mLoadingView, msg);
                break;
            case STATE_DATA:
                if (mDataView == null) {
                    if (mDataLayoutId != 0) {
                        mVsData.setLayoutResource(mDataLayoutId);
                    } else {
                        // 显示数据的布局是必须的，如果没有抛出异常
                        throw new RuntimeException(this.getClass().getSimpleName() + " need data layout id. ");
                    }
                    mDataView = mVsData.inflate();
                }
                prepareDataView(mDataView, msg);
                break;
            case STATE_EMPTY:
                if (mEmptyView == null) {
                    if (mEmptyLayoutId != 0) {
                        mVsEmpty.setLayoutResource(mEmptyLayoutId);
                    }
                    mEmptyView = mVsEmpty.inflate();
                }
                prepareEmptyView(mEmptyView, msg);
                break;
            case STATE_ERROR:
                if (mErrorView == null) {
                    if (mErrorLayoutId != 0) {
                        mVsError.setLayoutResource(mErrorLayoutId);
                    }
                    mErrorView = mVsError.inflate();
                }
                prepareErrorView(mErrorView, msg);
                break;
            case STATE_ALL_EMPTY:// 全空和默认一样，不需要break
            default:
                break;
        }
    }

    /**
     * 准备loading view
     *
     * @param rootView 根布局
     * @param msg      消息
     */
    protected void prepareLoadingView(View rootView, @Nullable String msg) {

    }

    /**
     * 准备loading view
     *
     * @param rootView 根布局
     * @param msg      消息
     */
    protected void prepareDataView(View rootView, @Nullable String msg) {

    }

    /**
     * 准备loading view
     *
     * @param rootView 根布局
     * @param msg      消息
     */
    protected void prepareErrorView(View rootView, @Nullable String msg) {

    }

    /**
     * 准备loading view
     *
     * @param rootView 根布局
     * @param msg      消息
     */
    protected void prepareEmptyView(View rootView, @Nullable String msg) {

    }

    /**
     * 显示当前布局的view
     *
     * @param state 状态
     */
    private void showCurrentView(@State int state) {
        showView(mLoadingView, state == STATE_LOADING);
        showView(mEmptyView, state == STATE_EMPTY);
        showView(mErrorView, state == STATE_ERROR);
        showView(mDataView, state == STATE_DATA);
    }

    /**
     * 显示布局
     *
     * @param view   布局
     * @param isShow 是否显示
     */
    private void showView(View view, boolean isShow) {
        if (view != null) {
            int visibility = isShow ? View.VISIBLE : View.GONE;
            view.setVisibility(visibility);
        }
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(getRootLayoutResId(), this, true);

        mVsLoading = (ViewStub) findViewById(R.id.vs_loading);
        mVsEmpty = (ViewStub) findViewById(R.id.vs_empty);
        mVsData = (ViewStub) findViewById(R.id.vs_data);
        mVsError = (ViewStub) findViewById(R.id.vs_error);
    }

    private void initListeners() {

    }

    public OnStateChangeListener getOnStateChangeListener() {
        return mOnStateChangeListener;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }

    public interface OnStateChangeListener {
        void onStateChange(@State int lastState, @State int currentState);
    }
}
