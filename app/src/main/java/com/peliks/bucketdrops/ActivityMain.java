package com.peliks.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button mBtnAddDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initBackground();

        mBtnAddDrop = (Button) findViewById(R.id.btnAddDrop);
        mBtnAddDrop.setOnClickListener(this);
    }

    private void initBackground() {
        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
        Glide.with(this).load(R.drawable.background).centerCrop().into(ivBackground);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ActivityMain.this, "Button was clicked!", Toast.LENGTH_SHORT).show();
    }
}
