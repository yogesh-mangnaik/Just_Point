package com.anonymous.thedailyprophetproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        View view1 = findViewById(R.id.shop_button);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        View view2 = findViewById(R.id.ad_button);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.vjti.intelligentnews");
                startActivity(launchIntent);
            }
        });

        View view3 = findViewById(R.id.model_button);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
