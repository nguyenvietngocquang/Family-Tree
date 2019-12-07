package com.example.familytree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Member> list = new ArrayList<Member>();
    Member test1 = new Member("Nguyễn Viết Ngọc Quang", 19, 9, 1998, true);
    Member test2 = new Member("Nguyễn Văn Hoan", 9, 4, 1998, true, test1);
    Member test3 = new Member("Trần Mạnh Công", 28, 7, 1998, true, test2);
    Member test4 = new Member("Phùng Thế Hùng", 18, 6, 1998, true, test1);
    Member test5 = new Member("Nguyễn Thế Vinh", 26, 12, 1998, true, test2);
    Member test6 = new Member("Đặng Ngọc Diệp", 12, 2, 1998, false);
    Member test7 = new Member("Trần Thanh Huyền", 6, 12, 1998, false);
    Member test8 = new Member("Phan Thị Thùy", 19, 8, 1998, false);
    Member test9 = new Member("Nguyễn Diệu Linh", 3, 7, 1998, false);
    Member test10 = new Member("Nguyễn Quang Anh", 23, 10, 1998, true, test5);
    Member test11 = new Member("Dương Hải Nguyên", 26, 12, 1998, true, test1);
    Member test12 = new Member("Bùi Đình Khánh Duy", 25, 6, 1998, true, test11);
    Member test13 = new Member("Nguyễn Đức Anh", 26, 8, 1998, true, test10);
    Member test14 = new Member("Phan Quốc Toản", 25, 11, 1998, true, test5);

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
            list.add(test1);
            list.add(test2);
            list.add(test3);
            list.add(test4);
            list.add(test5);
            list.add(test6);
            list.add(test7);
            list.add(test8);
            list.add(test9);
            list.add(test10);
            list.add(test11);
            list.add(test12);
            list.add(test13);
        }

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
}