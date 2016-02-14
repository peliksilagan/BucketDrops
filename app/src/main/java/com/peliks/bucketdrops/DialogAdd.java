package com.peliks.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.peliks.bucketdrops.beans.Drop;

import io.realm.Realm;

/**
 * Created by Felix on 2/13/2016.
 */
public class DialogAdd extends DialogFragment implements View.OnClickListener {

    public DialogAdd() {
    }

    private Button mBtnAddIt;
    private EditText mEtInput;
    private DatePicker mDatePicker;
    private ImageButton mIbClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnAddIt = (Button) view.findViewById(R.id.btnAddIt);
        mEtInput = (EditText) view.findViewById(R.id.etDrop);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mIbClose = (ImageButton) view.findViewById(R.id.ibCloseAddDialog);
        mIbClose.setOnClickListener(this);
        mBtnAddIt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibCloseAddDialog:
                dismiss();
                break;
            case R.id.btnAddIt:
                addAction();
                dismiss();
                break;
        }
    }

    //TODO process date
    private void addAction() {
        String goal = mEtInput.getText().toString();
        long currentTime = System.currentTimeMillis();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(new Drop(goal, currentTime, 0, false));
        realm.commitTransaction();

        //show all drops
       /* RealmQuery<Drop> dropListQuery = realm.where(Drop.class);
        RealmResults<Drop> dropResult = dropListQuery.findAll();
        for (Drop drop : dropResult) {
            Toast.makeText(getActivity(), "Goal: " + drop.getGoal(), Toast.LENGTH_LONG).show();
        }*/
        realm.close();

    }

    /*private Realm initDatabase() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }*/
}
