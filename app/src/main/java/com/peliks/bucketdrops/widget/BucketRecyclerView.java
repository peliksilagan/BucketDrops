package com.peliks.bucketdrops.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.peliks.bucketdrops.extras.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felix on 2/14/2016.
 */
public class BucketRecyclerView extends RecyclerView {

    private List<View> mNonEmptyViews = Collections.emptyList();
    private List<View> mEmptyViews = Collections.emptyList();

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        mAdapterDataObserver.onChanged();
    }

    private void toggleViews() {
        if(getAdapter() != null && !mEmptyViews.isEmpty() && !mNonEmptyViews.isEmpty()) {
            if(getAdapter().getItemCount() == 0) {
                Util.showViews(mEmptyViews);    //show all the empty views
                setVisibility(View.GONE);   //hide recycler view
                Util.hideViews(mNonEmptyViews); //hide all the non empty views
            } else {
                Util.showViews(mNonEmptyViews); //show all non empty views
                setVisibility(View.VISIBLE);    //show recycler view
                Util.hideViews(mEmptyViews);    //hide all empty views
            }
        }
    }

    public void hideIfEmpty(View ...views) {
        mNonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View ...emptyViews) {
        mEmptyViews = Arrays.asList(emptyViews);
    }
}
