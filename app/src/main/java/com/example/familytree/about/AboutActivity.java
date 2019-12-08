package com.example.familytree.about;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.familytree.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Button Cancel
        Button btn_about_cancel = (Button) findViewById(R.id.btn_about_cancel);
        btn_about_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Link Facebook
        TextView tv_facebook = (TextView) findViewById(R.id.tv_facebook);
        tv_facebook.setMovementMethod(LinkMovementMethod.getInstance());

        // Link GitHub
        TextView tv_github = (TextView) findViewById(R.id.tv_github);
        tv_github.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
