package com.social.chenl.module.future;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cl.library.base.BaseActivity;
import me.cl.library.util.ToolbarUtil;
import com.social.chenl.R;
import com.social.chenl.common.config.Api;
import com.social.chenl.common.config.Constants;
import com.social.chenl.common.okhttp.OkUtil;
import com.social.chenl.common.okhttp.ResultCallback;
import com.social.chenl.common.result.Result;
import com.social.chenl.common.result.ResultConstant;
import com.social.chenl.common.util.SPUtil;
import com.social.chenl.dialog.FutureDialog;
import okhttp3.Call;

/**
 * 写给未来
 */
public class FutureActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.future_info)
    AppCompatEditText mFutureInfo;

    private String futureInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_activity);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        futureInfo = SPUtil.build().getString(Constants.SP_FUTURE_INFO);
        if (!TextUtils.isEmpty(futureInfo)) {
            mFutureInfo.setText(futureInfo);
            mFutureInfo.setSelection(futureInfo.length());
        }
    }

    /**
     * 初始化
     */
    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setTitle(R.string.title_bar_future)
                .setBack()
                .setTitleCenter(R.style.AppTheme_Toolbar_TextAppearance)
                .setMenu(R.menu.future_menu, new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_send:
                                if (TextUtils.isEmpty(futureInfo)) {
                                    showToast("没有写下任何给未来的话哟~");
                                } else {
                                    showSendDialog();
                                }
                                break;
                        }
                        return false;
                    }
                })
                .build();

        // 输入监听
        mFutureInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                futureInfo = s.toString();
                // 实时保存
                SPUtil.build().putString(Constants.SP_FUTURE_INFO, futureInfo);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 发送配置Dialog
     */
    private void showSendDialog() {
        String tag = "sendFuture";
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 清除已经存在的，同样的fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);
        // 展示dialog
        FutureDialog futureDialog = FutureDialog.newInstance();
        futureDialog.show(transaction, tag);
        futureDialog.setCancelable(false);
        futureDialog.setOnSendClickListener(new FutureDialog.OnSendClickListener() {
            @Override
            public void onSend(int type, String mail, Integer startTime, Integer endTime) {
                postSaveFuture(type, mail, startTime, endTime);
            }
        });
    }

    /**
     * 提交保存信息
     */
    private void postSaveFuture(int type, String mail, Integer startNum, Integer endNum) {
        OkUtil.post()
                .url(Api.saveFuture)
                .addParam("type", type)
                .addParam("mail", mail)
                .addParam("futureInfo", futureInfo)
                .addParam("startNum", startNum)
                .addParam("endNum", endNum)
                .execute(new ResultCallback<Result>() {
                    @Override
                    public void onSuccess(Result response) {
                        String code = response.getCode();
                        if (ResultConstant.CODE_SUCCESS.equals(code)) {
                            showToast("信件进入时空隧道，等候传达");
                            SPUtil.build().putString(Constants.SP_FUTURE_INFO, null);
                            onBackPressed();
                        } else {
                            showToast("信件偏离预定轨道，请调整重试");
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showToast("信件偏离预定轨道，请调整重试");
                    }
                });
    }
}
