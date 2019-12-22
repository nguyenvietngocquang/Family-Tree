package com.example.familytree.random_member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familytree.R;
import com.example.familytree.member.Member;
import com.example.familytree.view_tree.ViewTreeActivity;

import java.util.ArrayList;
import java.util.Random;

public class RandomMemberActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_member);

        list = (ArrayList<Member>) getIntent().getSerializableExtra("random_member");
        Random random = new Random();
        int pos = random.nextInt(list.size());
        Member member = list.get(pos);

        // Set Name
        TextView tv_random_name = (TextView) findViewById(R.id.tv_random_name);
        String[] temp = member.getName().split(" ");
        if (temp.length <= 3) {
            tv_random_name.setText(member.getName());
        } else {
            String name = new String();
            for (int i = 0; i < temp.length - 3; i++) {
                name += temp[i] + " ";
            }
            name += temp[temp.length - 3] + "\n" + temp[temp.length - 2] + " " + temp[temp.length - 1];
            tv_random_name.setText(name);
        }

        // Set Gender
        ImageView iv_random_gender = (ImageView) findViewById(R.id.iv_random_gender);
        if (member.isMale())
            iv_random_gender.setImageResource(R.drawable.male);
        else
            iv_random_gender.setImageResource(R.drawable.female);

        // Set Age
        TextView tv_random_age = (TextView) findViewById(R.id.tv_random_age);
        tv_random_age.setText(member.getAge() + " years old");

        // Set Birthday
        TextView tv_random_birthday = (TextView) findViewById(R.id.tv_random_birthday);
        tv_random_birthday.setText(String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + "/" + String.valueOf(member.getYear()));

        // Button View Tree
        Button btn_random_view = (Button) findViewById(R.id.btn_random_view);
        btn_random_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomMemberActivity.this, ViewTreeActivity.class);
                intent.putExtra("view_tree", list);
                intent.putExtra("member", member.getName());
                startActivity(intent);
            }
        });

        // Button Cancel
        Button btn_random_cancel = (Button) findViewById(R.id.btn_random_cancel);
        btn_random_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
