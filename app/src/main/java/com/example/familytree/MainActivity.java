package com.example.familytree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.familytree.about.AboutActivity;
import com.example.familytree.add_member.AddMemberActivity;
import com.example.familytree.list_member.ListMemberActivity;
import com.example.familytree.member.Function;
import com.example.familytree.member.Member;
import com.example.familytree.view_tree.ChooseMemberActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.hasExtra("add_member")) {
            list = (ArrayList<Member>) getIntent().getSerializableExtra("add_member");
            Toast.makeText(MainActivity.this, "Add member successfully!", Toast.LENGTH_LONG).show();
        } else if (intent.hasExtra("list_member")) {
            list = (ArrayList<Member>) getIntent().getSerializableExtra("list_member");
        } else {
            list = Function.getData(this);
        }
        Collections.sort(list);

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                intent.putExtra("add_member", list);
                startActivity(intent);
            }
        });

        Button btn_list = (Button) findViewById(R.id.btn_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListMemberActivity.class);
                intent.putExtra("list_member", list);
                startActivity(intent);
            }
        });

        Button btn_tree = (Button) findViewById(R.id.btn_tree);
        btn_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseMemberActivity.class);
                intent.putExtra("view_tree", list);
                startActivity(intent);
            }
        });

        Button btn_about = (Button) findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Function.saveData(this, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}