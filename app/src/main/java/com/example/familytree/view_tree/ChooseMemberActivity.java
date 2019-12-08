package com.example.familytree.view_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.familytree.R;
import com.example.familytree.member.Function;
import com.example.familytree.member.Member;

import java.util.ArrayList;

public class ChooseMemberActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_member);

        list = (ArrayList<Member>) getIntent().getSerializableExtra("view_tree");

        ArrayList<String> list_male = new ArrayList<String>();
        for (Member member : list) {
            if (member.isMale())
                list_male.add(member.getName());
        }

        AutoCompleteTextView et_choose = (AutoCompleteTextView) findViewById(R.id.et_choose);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list_male);
        et_choose.setAdapter(adapter);

        // Button Apply
        Button btn_choose_apply = (Button) findViewById(R.id.btn_choose_apply);
        btn_choose_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member = Function.convertName(et_choose.getText().toString().trim());

                if (member.equals("")) {
                    Toast.makeText(ChooseMemberActivity.this, "You need to input name!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(ChooseMemberActivity.this, ViewTreeActivity.class);
                    intent.putExtra("view_tree", list);
                    intent.putExtra("member", member);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Button Cancel
        Button btn_choose_cancel = (Button) findViewById(R.id.btn_choose_cancel);
        btn_choose_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
