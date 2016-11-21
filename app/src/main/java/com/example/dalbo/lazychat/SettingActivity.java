package com.example.dalbo.lazychat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by dalbo on 11/11/2016.
 */

public class SettingActivity extends BaseActivity {
    Button bSave;
    EditText iNewNick;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Pengaturan");
        Config.prefInit(this);
        iNewNick = (EditText) findViewById(R.id.inewnick);
        bSave = (Button) findViewById(R.id.bsavesetting);
        iNewNick.setText(Config.getNick());
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.setNick(iNewNick.getText().toString());
                SettingActivity.this.finish();
            }
        });
    }
}
