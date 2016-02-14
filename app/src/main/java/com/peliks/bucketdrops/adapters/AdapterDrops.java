package com.peliks.bucketdrops.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peliks.bucketdrops.R;
import com.peliks.bucketdrops.beans.Drop;
import com.peliks.bucketdrops.extras.Util;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Felix on 2/14/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    static final int ITEM = 0;
    static final int FOOTER = 1;
    private LayoutInflater mInflater;
    private RealmResults<Drop> mItems;
    private Realm mRealm;
    private AddDropListener mAddDropListener;
    private MarkDropListener mMarkDropListener;

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> items) {
        mInflater = LayoutInflater.from(context);
        update(items);
        mRealm = realm;
    }

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> items, AddDropListener addDropListener, MarkDropListener markDropListener) {
        mInflater = LayoutInflater.from(context);
        update(items);
        mRealm = realm;
        mAddDropListener = addDropListener;
        mMarkDropListener = markDropListener;
    }

    public void update(RealmResults<Drop> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (mItems == null || position < mItems.size()) ? ITEM : FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view, mAddDropListener);
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            return new DropHolder(view, mMarkDropListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mItems.get(position);
            dropHolder.mTvGoal.setText(drop.getGoal());
            dropHolder.setBackground(drop.isCompleted());
        }
    }

    @Override
    public int getItemCount() {
        return (mItems == null || mItems.isEmpty()) ? 0 : mItems.size() + 1;
    }

    @Override
    public void onSwipe(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public void markComplete(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }
    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvGoal;
        private TextView mTvWhen;
        private MarkDropListener mMarkDropListener;
        private Context mContext;
        private View mItemView;

        public DropHolder(View itemView, MarkDropListener markDropListener) {
            super(itemView);
            mItemView = itemView;
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mTvGoal = (TextView) itemView.findViewById(R.id.tvRowGoal);
            mTvWhen = (TextView) itemView.findViewById(R.id.tvRowWhen);
            mMarkDropListener = markDropListener;
        }

        @Override
        public void onClick(View v) {
            mMarkDropListener.onMark(getAdapterPosition());
        }

        public void setBackground(boolean completed) {
            Drawable drawable;
            if (completed) {
                drawable = ContextCompat.getDrawable(mContext, R.color.bg_row_drop_complete);
            } else {
                drawable = ContextCompat.getDrawable(mContext, R.drawable.bg_row_drop);
            }
            Util.setBackground(mItemView, drawable);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mBtnAdd;
        private AddDropListener mAddDropListener;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnFooter);
            mBtnAdd.setOnClickListener(this);
        }

        public FooterHolder(View itemView, AddDropListener addDropListener) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnFooter);
            mBtnAdd.setOnClickListener(this);
            mAddDropListener = addDropListener;
        }

        @Override
        public void onClick(View v) {
            mAddDropListener.add();
        }
    }

}
