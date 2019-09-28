package com.social.chenl.module.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cl.library.base.BaseActivity;
import com.social.chenl.R;
import com.social.chenl.common.config.Api;
import com.social.chenl.common.config.Constants;
import com.social.chenl.common.okhttp.OkUtil;
import com.social.chenl.common.okhttp.ResultCallback;
import com.social.chenl.common.result.Result;
import com.social.chenl.common.result.ResultConstant;
import com.social.chenl.common.util.SPUtil;
import com.social.chenl.common.util.Utils;
import com.social.chenl.entity.AppVersion;
import okhttp3.Call;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private FeedFragment mFeedFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;

    private String mExit = "MM";
    private long mExitTime = 0;

    private TextView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initFragment();
        initBottomNavigation();

        initBadge();

        if (isCheckUpdate()) {
            checkNewVersion();
        }
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = HomeFragment.newInstance("home");
        mFeedFragment = FeedFragment.newInstance("home");
        mMessageFragment = new MessageFragment();
        mMineFragment = new MineFragment();
        switchFragment(mHomeFragment);
    }

    //底部导航
    private void initBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(mHomeFragment);
                        return true;
                    case R.id.navigation_camera:
                        switchFragment(mFeedFragment);
                        return true;
                    case R.id.navigation_interactive:
                        switchFragment(mMessageFragment);
                        return true;
                    case R.id.navigation_mine:
                        switchFragment(mMineFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void initBadge() {
        Intent intent = getIntent();
        if (intent == null) return;
        Integer num = intent.getIntExtra(Constants.PASSED_UNREAD_NUM, 0);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigation.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);
        View badge = LayoutInflater.from(this).inflate(R.layout.main_menu_badge, menuView, false);
        itemView.addView(badge);
        badgeView = badge.findViewById(R.id.tv_msg_count);
        if (num > 0) {
            Constants.isRead = false;
            visibleBadge();
        } else {
            goneBadge();
        }
    }

    public void goneBadge(){
        if (badgeView != null) {
            badgeView.setVisibility(View.GONE);
        }
    }

    public void visibleBadge(){
        if (badgeView != null) {
            badgeView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constants.ACTIVITY_PUBLISH:
                int id = data.getIntExtra(Constants.GO_INDEX, R.id.navigation_home);
                // 非导航本身事件，手动切换
                mBottomNavigation.setSelectedItemId(id);
                break;
            case Constants.ACTIVITY_PERSONAL:
                mMineFragment.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }

    private Fragment currentFragment;

    /**
     * 切换Fragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (!targetFragment.isAdded()) {
            // 首次currentFragment为null
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment_container, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showMoeToast("(ಥ _ ಥ)你难道要再按一次离开我么");
                mExitTime = System.currentTimeMillis();
            } else {
                int x = (int) (Math.random() * 10) + 1;
                if ("MM".equals(mExit)) {
                    if (x == 10) {
                        showMoeToast("恭喜你找到隐藏的偶，Game over!");
                        finish();
                    } else {
                        showMoeToast("你果然想要离开我(＠￣ー￣＠)");
                    }
                    mExitTime = System.currentTimeMillis();
                    mExit = "mm";
                } else if ("mm".equals(mExit)) {
                    mExit = "MM";
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // 是否提示更新
    private boolean isCheckUpdate() {
        int updateFlag = SPUtil.build().getInt(Constants.SP_UPDATE_FLAG);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.SIMPLIFIED_CHINESE);
        int dateInt = Integer.parseInt(sdf.format(new Date()));
        return (dateInt + 1) != updateFlag;
    }

    // 保存updateFlag
    private void saveUpdateFlag(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.SIMPLIFIED_CHINESE);
        int dateInt = Integer.parseInt(sdf.format(new Date()));
        SPUtil.build().putInt(Constants.SP_UPDATE_FLAG, (dateInt + 1));
    }

    // 检查新版本
    private void checkNewVersion() {
        OkUtil.post()
                .url(Api.latestVersion)
                .execute(new ResultCallback<Result<AppVersion>>() {
                    @Override
                    public void onSuccess(Result<AppVersion> response) {
                        String code = response.getCode();
                        AppVersion data = response.getData();
                        if (ResultConstant.CODE_SUCCESS.equals(code) && data != null) {
                            int versionCode = Utils.getAppVersionCode(MainActivity.this);
                            if (versionCode < data.getVersionCode()) {
                                showUpdate(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    // 展示更新弹窗
    private void showUpdate(final AppVersion appVersion) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("发现新版本");
        mDialog.setMessage(appVersion.getUpdateInfo());
        if (appVersion.getUpdateFlag() != 2) {
            mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveUpdateFlag();
                }
            });
        }
        mDialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoDownload(appVersion.getApkUrl());
            }
        }).setCancelable(false).create().show();
    }

    // 调起浏览器下载
    private void gotoDownload(String url){
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
