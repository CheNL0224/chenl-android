package com.social.chenl.module.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.ControlListener;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cl.library.base.BaseActivity;
import me.cl.library.recycle.ItemAnimator;
import me.cl.library.recycle.ItemDecoration;
import me.cl.library.util.ToolbarUtil;
import com.social.chenl.R;
import com.social.chenl.adapter.IncEpisodeAdapter;
import com.social.chenl.common.util.ContentUtil;
import com.social.chenl.entity.inc.Episode;
import com.social.chenl.entity.inc.IncVideo;
import com.social.chenl.entity.inc.VideoSource;
import me.happycao.exoplayer.ui.ExoPlayerManager;

/**
 * author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/04/21
 * desc   : 视频播放
 * version: 1.0
 */
public class IncVideoPlayActivity extends BaseActivity {

    public static final String INC_VIDEO = "inc_video";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.button_bar)
    RelativeLayout mButtonBar;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.player_view)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.video_pic)
    ImageView mVideoPic;
    @BindView(R.id.video_name)
    TextView mVideoName;
    @BindView(R.id.video_type)
    TextView mVideoType;
    @BindView(R.id.video_label)
    TextView mVideoLabel;
    @BindView(R.id.video_desc)
    TextView mVideoDesc;
    @BindView(R.id.video_selection)
    TextView mVideoSelection;
    @BindView(R.id.video_sort)
    TextView mVideoSort;
    @BindView(R.id.source_recycler_view)
    RecyclerView mSourceRecyclerView;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindString(R.string.hint_video_sort)
    String sortStr;

    private IncEpisodeAdapter mIncEpisodeAdapter;
    private ExoPlayerManager mExoPlayerManager;
    private String videoName = "";
    private String videoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setBack()
                .build();

        initRecyclerView();
        initPlayView();
        switchTitle();

        Intent intent = getIntent();
        IncVideo incVideo = (IncVideo) intent.getSerializableExtra(INC_VIDEO);
        if (incVideo != null) {
            setVideoInfo(incVideo);
        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ItemDecoration itemDecoration = new ItemDecoration(ItemDecoration.HORIZONTAL, 10, Color.parseColor("#ffffff"));
        itemDecoration.setGoneLast(true);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(4);
        // 剧集
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mSourceRecyclerView.setLayoutManager(flowLayoutManager);
        mSourceRecyclerView.setItemAnimator(new ItemAnimator());
        mSourceRecyclerView.addItemDecoration(spaceItemDecoration);

        mIncEpisodeAdapter = new IncEpisodeAdapter(new ArrayList<>());
        mSourceRecyclerView.setAdapter(mIncEpisodeAdapter);
        mIncEpisodeAdapter.setOnItemListener(new IncEpisodeAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, Episode episode, int position) {
                loadPlay(episode);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.video_desc, R.id.video_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_desc:

                break;
            case R.id.video_sort:
                mIncEpisodeAdapter.reverse();
                if (mIncEpisodeAdapter.isSortUp()) {
                    mVideoSort.setText(String.format(sortStr, "↑"));
                } else {
                    mVideoSort.setText(String.format(sortStr, "↓"));
                }
                break;
            default:
                break;
        }
    }

    private void initPlayView() {
        mExoPlayerManager = new ExoPlayerManager(this);
        mExoPlayerManager.setPlayerView(mExoPlayerView);
        mExoPlayerView.setControlListener(new ControlListener() {
            @Override
            public void onPrevious() {
                doPlayPrevious();
            }

            @Override
            public void onNext() {
                doPlayNext();
            }
        });
        mExoPlayerView.setControllerVisibilityListener(new PlaybackControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                switchTitleVisibility(visibility);
            }
        });
    }

    /**
     * 设置视频信息
     */
    private void setVideoInfo(IncVideo incVideo) {
        mVideoSort.setText(String.format(sortStr, "↑"));
        videoName = incVideo.getName();
        mTitleName.setText(videoName);
        mVideoName.setText(videoName);
        mExoPlayerView.setTitle(videoName);
        mVideoType.setText(incVideo.getType());
        mVideoLabel.setText(incVideo.getLang());
        mVideoDesc.setText(incVideo.getDes());
        ContentUtil.loadImage(mVideoPic, incVideo.getPic());

        // 多资源处理，当前仅支持m3u8
        List<VideoSource> source = incVideo.getSource();
        if (source != null) {
            for (VideoSource videoSource : source) {
                String title = videoSource.getFlag();
                if (title.endsWith("m3u8")) {
                    List<Episode> episode = videoSource.getEpisode();
                    mIncEpisodeAdapter.setData(episode);
                }
            }
        }
    }

    /**
     * 加载播放
     */
    private void loadPlay(Episode episode) {
        videoUrl = episode.getUrl();
        mExoPlayerManager.setUrl(videoUrl);
        mExoPlayerManager.startPlayer();
        mExoPlayerView.setTitle(videoName + episode.getTitle());
        showToast("正在播放" + episode.getTitle());
    }

    @Deprecated
    private void setPlay(Episode episode) {
        ExoPlayerManager.init(mExoPlayerView)
                .setUrl(episode.getUrl())
                .start();
    }

    /**
     * 标题切换
     */
    private void switchTitle() {
        mButtonBar.setAlpha(0);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int h = appBarLayout.getTotalScrollRange();
                int offset = Math.abs(verticalOffset);
                if (h == offset) return;

                int bbr = offset - 50 < 0 ? 0 : offset;
                mButtonBar.setAlpha(1f * bbr / h);
            }
        });
    }

    private void doPlayPrevious() {
        if (mIncEpisodeAdapter.havePrevious()) {
            Episode episode = mIncEpisodeAdapter.doPlayPrevious();
            loadPlay(episode);
        } else {
            showToast("没有上集了");
        }
    }

    private void doPlayNext() {
        if (mIncEpisodeAdapter.haveNext()) {
            Episode episode = mIncEpisodeAdapter.doPlayNext();
            loadPlay(episode);
        } else {
            showToast("没有下集了");
        }
    }

    /**
     * 显示隐藏状态栏
     */
    private void switchTitleVisibility(int visibility) {
        switch (visibility) {
            case View.GONE:
                mToolbar.setVisibility(View.GONE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case View.VISIBLE:
                mToolbar.setVisibility(View.VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isFullscreen()) {
                    outFullscreen();
                } else {
                    onBackPressed();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 判断是否是全屏
     */
    public boolean isFullscreen() {
        return mExoPlayerView.isFullscreen();
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void outFullscreen() {
        mExoPlayerView.outFullscreen();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mExoPlayerManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mExoPlayerManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mExoPlayerManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExoPlayerManager.onStop();
    }

    @Override
    protected void onDestroy() {
        mExoPlayerManager.onDestroy();
        super.onDestroy();
    }
}
