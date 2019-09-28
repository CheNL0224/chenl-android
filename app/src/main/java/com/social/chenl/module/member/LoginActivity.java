package com.social.chenl.module.member;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cl.library.base.BaseActivity;
import me.cl.library.util.ToolbarUtil;
import me.cl.library.view.LoadingDialog;
import me.cl.library.view.MoeToast;
import com.social.chenl.R;
import com.social.chenl.common.config.Api;
import com.social.chenl.common.config.Constants;
import com.social.chenl.common.okhttp.OkUtil;
import com.social.chenl.common.okhttp.ResultCallback;
import com.social.chenl.common.result.Result;
import com.social.chenl.common.result.ResultConstant;
import com.social.chenl.common.util.SPUtil;
import com.social.chenl.entity.UserToken;
import com.social.chenl.module.main.MainActivity;
import okhttp3.Call;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    AppCompatEditText mUsername;
    @BindView(R.id.password)
    AppCompatEditText mPassword;

    private long mExitTime = 0;
    private LoadingDialog loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setTitle(R.string.title_bar_login)
                .setTitleCenter()
                .build();

        loginProgress = new LoadingDialog(this, R.string.dialog_loading_login);

        int x = (int) (Math.random() * 6) + 1;
        if (x == 5) MoeToast.makeText(this, R.string.egg_from_where);

        String saveName = SPUtil.build().getString(Constants.SP_USER_NAME);
        mUsername.setText(saveName);
        mUsername.setSelection(saveName.length());
    }

    public void login(View view) {
        String username = Objects.requireNonNull(mUsername.getText()).toString().trim();
        String password = Objects.requireNonNull(mPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showToast(R.string.toast_login_null);
            return;
        }
        postLogin(username, password);
    }

    // 登录请求
    private void postLogin(final String userName, String userPwd) {
        OkUtil.post()
                .url(Api.userLogin)
                .addParam("username", userName)
                .addParam("password", userPwd)
                .setProgressDialog(loginProgress)
                .execute(new ResultCallback<Result<UserToken>>() {
                    @Override
                    public void onSuccess(Result<UserToken> response) {
                        String code = response.getCode();
                        switch (code) {
                            case ResultConstant.CODE_SUCCESS:
                                UserToken user = response.getData();
                                SPUtil.build().putBoolean(Constants.SP_BEEN_LOGIN, true);
                                SPUtil.build().putString(Constants.SP_USER_ID, user.getId());
                                SPUtil.build().putString(Constants.SP_USER_NAME, user.getUsername());
                                SPUtil.build().putString(Api.X_APP_TOKEN, user.getToken());
                                OkUtil.newInstance().addCommonHeader(Api.X_APP_TOKEN, user.getToken());
                                goHome();
                                break;
                            default:
                                showToast(R.string.toast_pwd_error);
                                break;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showToast(R.string.toast_login_error);
                    }
                });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                MoeToast.makeText(this, R.string.toast_again_exit);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void updatePwd(View view) {
        Intent intent = new Intent(this, ResetPwdActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.PASSED_UNREAD_NUM, 0);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loginProgress.isShowing()) {
            loginProgress.dismiss();
        }
        super.onDestroy();
    }
}
