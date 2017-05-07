package com.example.hong_inseon.projectlouvre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItemView extends Activity {
    TextView txtname;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        name = i.getStringExtra("name");

        txtname = (TextView) findViewById(R.id.names);
        txtname.setText(name);
    }
}
