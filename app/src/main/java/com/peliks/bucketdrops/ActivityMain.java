package com.peliks.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peliks.bucketdrops.adapters.AdapterDrops;
import com.peliks.bucketdrops.adapters.AddDropListener;
import com.peliks.bucketdrops.adapters.CompleteListener;
import com.peliks.bucketdrops.adapters.Divider;
import com.peliks.bucketdrops.adapters.MarkDropListener;
import com.peliks.bucketdrops.adapters.SimpleTouchCallback;
import com.peliks.bucketdrops.beans.Drop;
import com.peliks.bucketdrops.widget.BucketRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ActivityMain extends AppCompatActivity {

    private static final String TAG = "BucketDrops";
    private Toolbar mToolbar;
    private Button mBtnAddDrop;
    private BucketRecyclerView mRvDrops;
    private View mViewEmpty;
    private Realm mRealm;
    private RealmResults<Drop> mDropsResult;
    private AdapterDrops mAdapterDrops;
    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog();
        }
    };
    private RealmChangeListener mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapterDrops.update(mDropsResult);
        }
    };
    private AddDropListener mAddDropListener = new AddDropListener() {
        @Override
        public void add() {
            showAddDialog();
        }
    };
    private MarkDropListener mMarkDropListener = new MarkDropListener() {
        @Override
        public void onMark(int position) {
            showMarkDialog(position);
        }
    };
    private CompleteListener mCompleteListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
            mAdapterDrops.markComplete(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewEmpty = findViewById(R.id.empty_drops);
        mBtnAddDrop = (Button) findViewById(R.id.btnAddDrop);
        mBtnAddDrop.setOnClickListener(mBtnAddListener);

        mDropsResult = mRealm.where(Drop.class).findAllAsync();
        mRvDrops = (BucketRecyclerView) findViewById(R.id.rvDrops);
        mRvDrops.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        mRvDrops.hideIfEmpty(mToolbar);
        mRvDrops.showIfEmpty(mViewEmpty);
        mAdapterDrops = new AdapterDrops(this, mRealm, mDropsResult, mAddDropListener, mMarkDropListener);
        mRvDrops.setAdapter(mAdapterDrops);
        SimpleTouchCallback simpleTouchCallback = new SimpleTouchCallback(mAdapterDrops);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRvDrops);

        initBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                toast("Action Add");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDropsResult.addChangeListener(mRealmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDropsResult.removeChangeListener(mRealmChangeListener);
    }

    private void initBackground() {
        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
        Glide.with(this).load(R.drawable.background).centerCrop().into(ivBackground);
    }

    private void showAddDialog() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    private void showMarkDialog(int position) {
        DialogMark dialog = new DialogMark();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        dialog.setArguments(bundle);
        dialog.setCompleteListener(mCompleteListener);
        dialog.show(getSupportFragmentManager(), "Mark");
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
