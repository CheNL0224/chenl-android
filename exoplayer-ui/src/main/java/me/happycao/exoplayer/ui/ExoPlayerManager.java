package me.happycao.exoplayer.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.WindowManager;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * @author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/03/31
 * desc   : ExoPlayerManager
 * version: 1.0
 */
public class ExoPlayerManager {

    private Activity activity;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultExtractorsFactory extractorsFactory;

    private String url = "";
    private boolean inErrorState;
    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;

    public ExoPlayerManager(Activity activity) {
        this.activity = activity;
    }

    public void setPlayerView(SimpleExoPlayerView playerView) {
        this.playerView = playerView;
    }

    public void setUrl(String url) {
        this.url = url;
        this.resumeWindow = C.INDEX_UNSET;
        initializePlayer();
    }

    private void initializePlayer() {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            Context context = playerView.getContext();
            // 创建带宽对象
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // 根据当前宽带来创建选择磁道工厂对象
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            // 传入工厂对象，以便创建选择磁道对象
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            // 根据选择磁道创建播放器对象
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.addListener(eventListener);
            // 将player和view绑定
            playerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
            // 定义数据源工厂对象
            mediaDataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, context.getPackageName()));
            // 创建Extractor工厂对象，用于提取多媒体文件
            extractorsFactory = new DefaultExtractorsFactory();
        }
        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(resumeWindow, resumePosition);
        }
        MediaSource mediaSource = buildMediaSource(url);
        player.prepare(mediaSource, !haveResumePosition, false);
        inErrorState = false;
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    public void startPlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public void onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    public void onResume() {
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    public void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    public void onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void onDestroy() {
        this.url = "";
        clearResumePosition();
    }

    private MediaSource buildMediaSource(String url) {
        Uri uri = Uri.parse(url);
        if (url.endsWith(".m3u8")) {
            return new HlsMediaSource(uri, mediaDataSourceFactory, null, null);
        } else {
            return new ExtractorMediaSource(uri, mediaDataSourceFactory, extractorsFactory, null, null);
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = Math.max(0, player.getContentPosition());
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    private Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playWhenReady) {
                // 常亮
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                // 清除常亮
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (playbackState == Player.STATE_ENDED) {
                shouldAutoPlay = false;
                player.setPlayWhenReady(shouldAutoPlay);
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            inErrorState = true;
            if (isBehindLiveWindow(error)) {
                clearResumePosition();
                initializePlayer();
            } else {
                updateResumePosition();
            }
        }

        @Override
        public void onPositionDiscontinuity() {
            if (inErrorState) {
                updateResumePosition();
            }
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    };

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    // 弃用
    @Deprecated
    public static Builder init(SimpleExoPlayerView playerView) {
        return new Builder(playerView);
    }

    public static class Builder {

        private SimpleExoPlayerView playerView;
        private SimpleExoPlayer player;
        private DefaultTrackSelector trackSelector;
        private DataSource.Factory mediaDataSourceFactory;
        private DefaultExtractorsFactory extractorsFactory;

        public Builder(SimpleExoPlayerView playerView) {
            this.playerView = playerView;
            Context context = playerView.getContext();
            // 创建带宽对象
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // 根据当前宽带来创建选择磁道工厂对象
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            // 传入工厂对象，以便创建选择磁道对象
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            // 根据选择磁道创建播放器对象
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            // 将player和view绑定
            this.playerView.setPlayer(player);
            // 定义数据源工厂对象
            mediaDataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, context.getPackageName()));
            // 创建Extractor工厂对象，用于提取多媒体文件
            extractorsFactory = new DefaultExtractorsFactory();
        }

        public Builder setUrl(String url) {
            // 添加数据源到播放器中
            player.prepare(getMediaSource(url));
            return this;
        }

        public void start() {
            player.setPlayWhenReady(false);
        }

        private MediaSource getMediaSource(String url) {
            Uri uri = Uri.parse(url);
            if (url.endsWith(".m3u8")) {
                return new HlsMediaSource(uri, mediaDataSourceFactory, null, null);
            } else {
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, extractorsFactory, null, null);
            }
        }
    }
}
