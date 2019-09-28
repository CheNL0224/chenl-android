package com.social.chenl.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.social.chenl.R;

/**
 * author : Bafs
 * e-mail : bafs.jy@live.com
 * time   : 2018/08/02
 * desc   : 登出Dialog
 * version: 1.0
 */
public class LogoutDialog extends DialogFragment {

    @BindView(R.id.prompt_info)
    TextView mPromptInfo;
    @BindView(R.id.prompt_ok)
    Button mPromptOk;
    @BindView(R.id.prompt_cancel)
    Button mPromptCancel;

    public interface LogoutListener {
        void onLogout();
    }

    private LogoutListener mLogoutListener;

    public void setLogoutListener(LogoutListener listener) {
        mLogoutListener = listener;
    }

    public static LogoutDialog newInstance() {
        Bundle args = new Bundle();
        LogoutDialog fragment = new LogoutDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.mine_logout_dialog, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        String content = "", certain = "", cancel = "";
        int x = (int) (Math.random() * 100) + 1;
        if (x == 100) {
            content = "偶是隐藏内容哦！100次退出才有一次能够看见我呢!";
            certain = "就算你是隐藏人物我也要离开";
            cancel = "lucky,我还要去找到更多的彩蛋";
        } else if (x < 20) {
            content = "o(>﹏<)o不要走!";
            certain = "忍痛离开！";
            cancel = "好啦，好啦，我不走了.";
        } else if (x < 40) {
            content = "你走了就不要再回来，哼！(｀へ´)";
            certain = "走就走！（(￣_,￣ )）";
            cancel = "额！（(⊙﹏⊙)，你停下了脚步）";
        } else if (x < 60) {
            content = "你真的要走么 ╥﹏╥...";
            certain = "(ノへ￣、) 默默离开";
            cancel = "(⊙3⊙) 留下";
        } else if (x < 80) {
            content = "落花有意流水无情！";
            certain = "便做春江都是泪，流不尽，许多愁!(⊙﹏⊙)";
            cancel = "花随水走,水载花流~~o(>_<)o ~~";
        } else if (x < 100) {
            content = "慢慢阳关路，劝君更进一杯酒";
            certain = "举杯邀明月，对影成三人";
            cancel = "不醉不归!";
        }
        mPromptInfo.setText(content);
        mPromptOk.setText(certain);
        mPromptCancel.setText(cancel);
    }


    @OnClick({R.id.prompt_ok, R.id.prompt_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prompt_ok:
                if (mLogoutListener != null) {
                    mLogoutListener.onLogout();
                }
                dismiss();
                break;
            case R.id.prompt_cancel:
                dismiss();
                break;
        }
    }
}