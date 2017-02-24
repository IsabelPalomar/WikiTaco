package com.wikitaco.demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wikitaco.demo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginUserByEmail(View view) {
        Intent i = new Intent(LoginActivity.this, TacosListActivity.class);
        startActivity(i);
    }
}
