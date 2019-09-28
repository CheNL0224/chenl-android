package com.social.chenl.module.mine;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import com.social.chenl.common.util.ContentUtil;
import com.social.chenl.common.util.ImageUtil;
import com.social.chenl.common.util.SPUtil;
import com.social.chenl.dialog.EditTextDialog;
import com.social.chenl.entity.UserInfo;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

/**
 * 用户资料
 */
public class PersonalInfoActivity extends BaseActivity {

    private static final int PHOTO_REQUEST_CUT = 456;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.person_img)
    ImageView mPersonImg;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.user_signature)
    TextView mUserSignature;

    private String mUserId;
    private String saveName;
    private String mImagePath;

    private LoadingDialog loadingProgress;

    // 用户更新的参数
    private String username;
    private String avatar;
    private Integer sex;
    private String qq;
    private String signature;

    // 是否为更新头像
    private boolean isUpdateAvatar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setTitle(R.string.title_bar_personal_info)
                .setBack()
                .setTitleCenter(R.style.AppTheme_Toolbar_TextAppearance)
                .build();

        loadingProgress = new LoadingDialog(this, R.string.dialog_update_avatar);

        int x = (int) (Math.random() * 5) + 1;
        if (x == 1) {
            MoeToast.makeText(this, R.string.egg_who_is_there);
        }

        mUserId = SPUtil.build().getString(Constants.SP_USER_ID);
        saveName = SPUtil.build().getString(Constants.SP_USER_NAME);
        mPersonName.setText(saveName);
        postUserInfo();
    }

    @OnClick({R.id.person_img, R.id.person_name})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_img:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(PersonalInfoActivity.this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.person_name:
                EditTextDialog editTextDialog = EditTextDialog.newInstance("修改用户名", saveName, 24);
                editTextDialog.show(getSupportFragmentManager(), "edit");
                editTextDialog.setPositiveListener(new EditTextDialog.PositiveListener() {
                    @Override
                    public void Positive(String value) {
                        if (!TextUtils.isEmpty(value) && value.length() > 4 && !saveName.equals(value)) {
                            showToast("暂不支持修改用户名");
                            username = null;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        // 图片选择
        if (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (photos != null) {
                String photo = photos.get(0);
                Uri uri = ImageUtil.getFileUri(this, new File(photo));
                String imagePath = ImageUtil.getImagePath();
                mImagePath = imagePath;
                int size = 240;
                Intent intent = ImageUtil.callSystemCrop(uri, imagePath, size);
                startActivityForResult(intent, PHOTO_REQUEST_CUT);
            }
        }

        // 图片裁剪
        if (requestCode == PHOTO_REQUEST_CUT) {
            ContentUtil.loadAvatar(mPersonImg, mImagePath);
            postUserImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取用户信息
     */
    private void postUserInfo() {
        OkUtil.post()
                .url(Api.userInfo)
                .addParam("id", mUserId)
                .execute(new ResultCallback<Result<UserInfo>>() {

                    @Override
                    public void onSuccess(Result<UserInfo> response) {
                        if ("00000".equals(response.getCode())) {
                            setUserInfo(response.getData());
                        } else {
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        onBackPressed();
                    }
                });
    }

    /**
     * 上传用户头像
     */
    private void postUserImage() {
        File file = new File(mImagePath);
        if (!file.exists()) {
            showUserImageUpdateError();
            return;
        }
        OkUtil.post()
                .url(Api.uploadUserImage)
                .addFile("file", file)
                .execute(new ResultCallback<Result<List<String>>>() {
                    @Override
                    public void onSuccess(Result<List<String>> response) {
                        String code = response.getCode();
                        List<String> photos = response.getData();
                        if (!"00000".equals(code) || photos == null || photos.size() == 0) {
                            showUserImageUpdateError();
                            return;
                        }
                        avatar = photos.get(0);
                        isUpdateAvatar = true;
                        postUpdateUserInfo();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showUserImageUpdateError();
                    }
                });
    }

    /**
     * 更新用户信息
     */
    private void postUpdateUserInfo() {
        OkUtil.post()
                .url(Api.updateUser)
                .addParam("id", mUserId)
                .addParam("username", username)
                .addParam("avatar", avatar)
                .execute(new ResultCallback<Result<UserInfo>>() {

                    @Override
                    public void onSuccess(Result<UserInfo> response) {
                        if ("00000".equals(response.getCode())) {
                            showUserUpdateSuccess();
                            setUserInfo(response.getData());
                        } else {
                            showUserUpdateError();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showUserUpdateError();
                    }
                });
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo(UserInfo userInfo) {
        if (isUpdateAvatar) {
            isUpdateAvatar = false;
            notifyUpdateUserImage();
            File file = new File(mImagePath);
            if (file.exists()) {
                boolean delete = file.delete();
                Log.d(TAG, "setUserInfo: delete file " + delete);
            }
        }

        ContentUtil.loadUserAvatar(mPersonImg, userInfo.getAvatar());

        if (!TextUtils.isEmpty(userInfo.getUsername())) {
            mPersonName.setText(userInfo.getUsername());
        }

        cleanData();
    }

    /**
     * 清除数据
     */
    private void cleanData() {
        avatar = null;
        username = null;
        sex = null;
        qq = null;
        signature = null;
    }

    /**
     * 通知更新用户头像
     */
    private void notifyUpdateUserImage() {
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction(Constants.UPDATE_USER_IMG);
        sendBroadcast(intent);
    }

    /**
     * 提示头像修改失败
     */
    private void showUserImageUpdateError() {
        showToast("更新头像失败");
    }

    /**
     * 提示用户信息更新失败
     */
    private void showUserUpdateSuccess() {
        showToast("更新用户信息成功");
    }

    /**
     * 提示用户信息更新失败
     */
    private void showUserUpdateError() {
        showToast("更新用户信息失败");
    }
}
