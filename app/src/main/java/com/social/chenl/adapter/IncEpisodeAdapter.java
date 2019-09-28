package com.social.chenl.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.social.chenl.R;
import com.social.chenl.entity.inc.Episode;

/**
 * @author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/04/21
 * desc   : 视频剧集
 * version: 1.0
 */
public class IncEpisodeAdapter extends RecyclerView.Adapter<IncEpisodeAdapter.ViewHolder> {

    private List<Episode> mList;
    private boolean[] mSelect;
    private int mSelectPosition;
    private boolean sortUp = true;

    private OnItemListener mOnItemListener;

    public interface OnItemListener {
        void onItemClick(View view, Episode episode, int position);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public IncEpisodeAdapter(List<Episode> list) {
        this.mList = list;
        int size = list.size();
        this.mSelect = new boolean[size];
        if (size != 0) {
            this.mSelectPosition = size - 1;
            this.mSelect[mSelectPosition] = false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.inc_episode_recycle_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public boolean havePrevious() {
        if (sortUp) {
            return mSelectPosition != 0;
        } else {
            return mSelectPosition < getItemCount() - 1;
        }
    }

    public boolean haveNext() {
        if (sortUp) {
            return mSelectPosition < getItemCount() - 1;
        } else {
            return mSelectPosition != 0;
        }
    }

    public Episode doPlayPrevious() {
        if (havePrevious()) {
            if (sortUp) {
                doUp();
            } else {
                doDown();
            }
        }
        return mList.get(mSelectPosition);
    }

    public Episode doPlayNext() {
        if (haveNext()) {
            if (sortUp) {
                doDown();
            } else {
                doUp();
            }
        }
        return mList.get(mSelectPosition);
    }

    private void doUp() {
        mSelect[mSelectPosition] = false;
        mSelectPosition--;
        mSelect[mSelectPosition] = true;
        notifyDataSetChanged();
    }

    private void doDown() {
        mSelect[mSelectPosition] = false;
        mSelectPosition++;
        mSelect[mSelectPosition] = true;
        notifyDataSetChanged();
    }

    public void setData(List<Episode> list) {
        this.mList = list;
        int size = list.size();
        this.mSelect = new boolean[size];
        notifyDataSetChanged();
    }

    public void updateData(List<Episode> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void reverse() {
        if (getItemCount() > 0) {
            sortUp = !sortUp;
            mSelect[mSelectPosition] = false;
            mSelectPosition = getItemCount() - 1 - mSelectPosition;
            mSelect[mSelectPosition] = true;
            Collections.reverse(mList);
            notifyDataSetChanged();
        }
    }

    public boolean isSortUp() {
        return sortUp;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.episode_name)
        TextView mEpisodeName;

        private String regex = "^[-\\+]?[\\d]*$";
        private Episode mEpisode;
        private int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemListener != null) {
                        mSelect[mSelectPosition] = false;
                        mSelectPosition = mPosition;
                        mSelect[mPosition] = true;
                        notifyDataSetChanged();
                        mOnItemListener.onItemClick(v, mEpisode, mPosition);
                    }
                }
            });
        }

        void bindItem(Episode episode, int position) {
            mPosition = position;
            mEpisode = episode;
            Pattern pattern = Pattern.compile(regex);
            String writer = episode.getTitle();
            if (pattern.matcher(writer).matches()) {
                writer = String.format("第%s集", writer);
            }
            mEpisodeName.setText(writer);
            mEpisodeName.setSelected(mSelect[mPosition]);
        }
    }
}
