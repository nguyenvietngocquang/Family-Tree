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
    static int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_member);

        Member member = null;
        String notification = null;
        Intent intent = getIntent();
        if (intent.hasExtra("random_member")) {
            list = (ArrayList<Member>) getIntent().getSerializableExtra("random_member");
            check = 1;
        } else {
            notification = getIntent().getExtras().getString("notifi");
            list = (ArrayList<Member>) getIntent().getSerializableExtra("notification_member");
            check = 2;
        }

        if (check == 1) {
            Random random = new Random();
            int pos = random.nextInt(list.size());
            member = list.get(pos);
        } else if (check == 2) {
            for (int i = 0; i < list.size(); i++) {
                member = list.get(i);
                if (notification.contains(member.getName()))
                    break;
            }
        }

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
        if (check == 2)
            iv_random_gender.setImageResource(R.drawable.birthday);
        else {
            if (member.isMale())
                iv_random_gender.setImageResource(R.drawable.male);
            else
                iv_random_gender.setImageResource(R.drawable.female);
        }

        // Set Age
        TextView tv_random_age = (TextView) findViewById(R.id.tv_random_age);
        tv_random_age.setText(member.getAge() + " years old");

        // Set Birthday
        TextView tv_random_birthday = (TextView) findViewById(R.id.tv_random_birthday);
        tv_random_birthday.setText(String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + "/" + String.valueOf(member.getYear()));

        // Button View Tree
        Button btn_random_view = (Button) findViewById(R.id.btn_random_view);
        Member finalMember = member;
        btn_random_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomMemberActivity.this, ViewTreeActivity.class);
                intent.putExtra("view_tree", list);
                intent.putExtra("member", finalMember.getName());
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
