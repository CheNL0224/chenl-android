package com.social.chenl.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.social.chenl.R;
import com.social.chenl.common.util.ContentUtil;
import com.social.chenl.common.util.Utils;
import com.social.chenl.entity.inc.IncVideo;

/**
 * @author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/04/21
 * desc   : 资源采集
 * version: 1.0
 */
public class IncVideoAdapter extends RecyclerView.Adapter<IncVideoAdapter.ViewHolder> {

    private List<IncVideo> mIncVideoList;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View paramView, IncVideo incVideo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public IncVideoAdapter(List<IncVideo> list) {
        this.mIncVideoList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.inc_video_recycle_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindItem(mIncVideoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.mIncVideoList == null ? 0 : mIncVideoList.size();
    }

    public void setData(List<IncVideo> list) {
        mIncVideoList = list;
        notifyDataSetChanged();
    }

    public void updateData(List<IncVideo> list) {
        mIncVideoList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_pic)
        ImageView mVideoPic;
        @BindView(R.id.video_name)
        TextView mVideoName;
        private IncVideo mIncVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int width = (Utils.getScreenWidth() - Utils.dp2px(32)) / 4;
            int height = width * 13 / 9;
            ViewGroup.LayoutParams localLayoutParams = mVideoPic.getLayoutParams();
            localLayoutParams.width = width;
            localLayoutParams.height = height;
            mVideoPic.setLayoutParams(localLayoutParams);

            ViewGroup.LayoutParams layoutParams = mVideoName.getLayoutParams();
            layoutParams.width = width;
            mVideoName.setLayoutParams(layoutParams);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, mIncVideo);
                    }
                }
            });

        }

        public void bindItem(IncVideo incVideo, int position) {
            mIncVideo = incVideo;
            mVideoName.setText(incVideo.getName());
            ContentUtil.loadImage(mVideoPic, incVideo.getPic());
        }
    }

}
