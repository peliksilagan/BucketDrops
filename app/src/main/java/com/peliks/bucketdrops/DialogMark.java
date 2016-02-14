package com.peliks.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.peliks.bucketdrops.adapters.CompleteListener;

/**
 * Created by Felix on 2/14/2016.
 */
public class DialogMark extends DialogFragment {

    private ImageButton mBtnClose;
    private Button mBtnCompleted;
    private CompleteListener mCompleteListener;
    private View.OnClickListener mBtnOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCompleted:
                    markAsComplete();
                    break;
            }
            dismiss();
        }
    };

    private void markAsComplete() {
        Bundle arguments = getArguments();
        if(mCompleteListener != null && arguments != null) {
            mCompleteListener.onComplete(arguments.getInt("POSITION"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btnClose);
        mBtnCompleted = (Button) view.findViewById(R.id.btnCompleted);
        mBtnClose.setOnClickListener(mBtnOnClickListener);
        mBtnCompleted.setOnClickListener(mBtnOnClickListener);
    }

    public void setCompleteListener(CompleteListener completeListener) {
        mCompleteListener = completeListener;
    }
}
