package com.sheilambadi.android.tictactoe.singleplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sheilambadi.android.tictactoe.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ThreeByThreeActivity extends AppCompatActivity {
    Intent i;
    String playerComp;
    String playerUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //calligraphy library
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_three_by_three);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_32dp);
        i = getIntent();

        playerComp = i.getStringExtra("valueComp");
        playerUser = i.getStringExtra("valueUser");
        Toast.makeText(getApplicationContext(), "Comp: " + playerComp +" User " + playerUser, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
