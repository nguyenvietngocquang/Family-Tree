package com.example.familytree.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.familytree.MainActivity;
import com.example.familytree.R;
import com.example.familytree.member.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    Calendar calendar;
    int year, month, day;

    RecyclerView rv;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        list = (ArrayList<Member>) getIntent().getSerializableExtra("notification");

        // Create list of notification
        ArrayList<String> list_notification = new ArrayList<String>();
        ArrayList<String> list0 = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        ArrayList<String> list3 = new ArrayList<String>();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate today = LocalDate.of(year, month, day);

        for (Member member : list) {
            LocalDate birthday = LocalDate.of(year, member.getMonth(), member.getDay());
            long count = ChronoUnit.DAYS.between(today, birthday);
            if (count < 0) {
                birthday = LocalDate.of(year + 1, member.getMonth(), member.getDay());
                count = ChronoUnit.DAYS.between(today, birthday);
            }
            if (count == 0) {
                list0.add("<b>Today</b> is <b><font color=\"#CC0000\">" + member.getName() + "</font></b>'s birthday. " +
                        "<b>(" + String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + ")</b>");
            } else if (count == 1) {
                list1.add("<b>Tomorrow</b> is <b><font color=\"#CC0000\">" + member.getName() + "</font></b>'s birthday. " +
                        "<b>(" + String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + ")</b>");
            } else if (count == 2) {
                list2.add("<b>2 days</b> left until <b><font color=\"#CC0000\">" + member.getName() + "</font></b>'s birthday. " +
                        "<b>(" + String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + ")</b>");
            } else if (count == 3) {
                list3.add("<b>3 days</b> left until <b><font color=\"#CC0000\">" + member.getName() + "</font></b>'s birthday. " +
                        "<b>(" + String.valueOf(member.getDay()) + "/" + String.valueOf(member.getMonth()) + ")</b>");
            }
        }
        list_notification.addAll(list0);
        list_notification.addAll(list1);
        list_notification.addAll(list2);
        list_notification.addAll(list3);


        rv = (RecyclerView) findViewById(R.id.rv_notification);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new NotificationAdapter(list_notification);
        adapter.onAttachedToRecyclerView(rv);
        rv.setAdapter(adapter);

        // Button Cancel
        Button btn_notification_cancel = (Button) findViewById(R.id.btn_notification_cancel);
        btn_notification_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
