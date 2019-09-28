package com.social.chenl.module.search;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cl.library.base.BaseActivity;
import me.cl.library.util.ToolbarUtil;
import com.social.chenl.R;
import com.social.chenl.adapter.IncVideoAdapter;
import com.social.chenl.common.config.Api;
import com.social.chenl.common.okhttp.OkUtil;
import com.social.chenl.common.okhttp.ResultCallback;
import com.social.chenl.common.util.GsonUtil;
import com.social.chenl.common.util.SPUtil;
import com.social.chenl.common.util.Utils;
import com.social.chenl.common.widget.GridItemDecoration;
import com.social.chenl.entity.inc.IncResult;
import com.social.chenl.entity.inc.IncVideo;
import com.social.chenl.module.video.IncVideoPlayActivity;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    SearchView mSearchView;

    private IncVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setMenu(R.menu.search_view_menu, null)
                .setBack()
                .build();
        Menu menu = mToolbar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        initSearchView();
        initRecyclerView();
        initData();
    }

    private void initSearchView() {
        if (checkVideoEnable()) {
            mSearchView.setQueryHint(getString(R.string.hint_search_video));
        }
        // 当展开无输入内容的时候，没有关闭的图标
        mSearchView.onActionViewExpanded();
        // 显示隐藏提交按钮
        mSearchView.setSubmitButtonEnabled(true);
        // 搜索确认图标
        AppCompatImageView searchGo = mSearchView.findViewById(R.id.search_go_btn);
        searchGo.setImageResource(R.drawable.ic_search);

        // 事件
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (checkVideoEnable()) {
                    searchData(query);
                } else {
                    if (checkCode(query)) {
                        setVideoEnable();
                    } else {
                        showToast(R.string.toast_feature_dev);
                    }

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        List<IncVideo> incVideos = new ArrayList<>();
        String videoJson = SPUtil.build().getString(IncVideoPlayActivity.INC_VIDEO);
        if (!TextUtils.isEmpty(videoJson) && checkVideoEnable()) {
            incVideos = GsonUtil.toList(videoJson, IncVideo[].class);
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        GridItemDecoration gridItemDecoration = new GridItemDecoration();
//        gridItemDecoration.setDrawable(Color.parseColor("#f2f2f2"));
        gridItemDecoration.setDecoration(Utils.dp2px(8));
        mRecyclerView.addItemDecoration(gridItemDecoration);

        mAdapter = new IncVideoAdapter(incVideos);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new IncVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View paramView, IncVideo incVideo) {
                gotoVideoPlay(incVideo);
            }
        });
    }

    private void initData() {
        if (checkVideoEnable()) {
            getData();
        }
    }

    private void searchData(String query) {
        OkUtil.get()
                .url(Api.incApi)
                .addUrlParams("wd", query)
                .execute(new ResultCallback<IncResult>() {
                    @Override
                    public void onSuccess(IncResult response) {
                        List<IncVideo> video = response.getVideo();
                        if (video != null && !video.isEmpty()) {
                            setData(video);
                        } else {
                            setError();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        setError();
                    }
                });
    }

    private void getData() {
        OkUtil.get()
                .url(Api.incApi)
                .addUrlParams("h", "24")
                .addUrlParams("t", "4")
                .addUrlParams("pg", "1")
                .execute(new ResultCallback<IncResult>() {
                    @Override
                    public void onSuccess(IncResult response) {
                        List<IncVideo> video = response.getVideo();
                        if (video != null) {
                            setData(video);
                            SPUtil.build().putString(IncVideoPlayActivity.INC_VIDEO, GsonUtil.toJson(video));
                        } else {
                            setError();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        setError();
                    }
                });
    }

    /**
     * 设置Adapter数据
     */
    private void setData(List<IncVideo> list) {
        mAdapter.setData(list);
    }

    /**
     * 设置异常提示
     */
    private void setError() {
        showToast(R.string.toast_search_none);
    }

    private void setVideoEnable() {
        SPUtil.build().putBoolean("video_enable", true);
        mSearchView.setQueryHint(getString(R.string.hint_search_video));
        showToast(R.string.egg_video_start);
    }

    private boolean checkVideoEnable() {
        return SPUtil.build().getBoolean("video_enable", false);
    }

    private boolean checkCode(String query) {
        return Objects.equals(query, getString(R.string.egg_open_code));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void gotoVideoPlay(IncVideo incVideo) {
        Intent intent = new Intent(this, IncVideoPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IncVideoPlayActivity.INC_VIDEO, incVideo);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
