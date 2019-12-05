package com.example.familytree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMemberActivity extends AppCompatActivity {
    ArrayList<Member> list = new ArrayList<Member>();

    Calendar calendar;
    String name, father_name, spouse_name;
    int tYear, tMonth, tDay;
    boolean male = true;
    Member father;
    Member spouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Member test1 = new Member("Nguyen Viet Ngoc Quang", true);
        Member test2 = new Member("Nguyen Quang Anh", true);
        Member test3 = new Member("Tran Manh Cong", true);
        Member test4 = new Member("Phung The Hung", true);
        Member test5 = new Member("Nguyen The Vinh", true);
        Member test6 = new Member("Dang Ngoc Diep", false);
        Member test7 = new Member("Tran Thanh Huyen", false);
        Member test8 = new Member("Phan Thi Thuy", false);
        Member test9 = new Member("Nguyen Dieu Linh", false);

        list.add(test1);
        list.add(test2);
        list.add(test3);
        list.add(test4);
        list.add(test5);
        list.add(test6);
        list.add(test7);
        list.add(test8);
        list.add(test9);

        ArrayList<String> list_male = new ArrayList<String>();
        ArrayList<String> list_female = new ArrayList<String>();
        for (Member member : list) {
            if (member.isMale())
                list_male.add(member.getName());
            else list_female.add(member.getName());
        }

//        Intent intent = this.getIntent();
//        list = (ArrayList<Member>) getIntent().getSerializableExtra("list_member");

        // Input Name
        EditText et_add_name = (EditText) findViewById(R.id.et_add_name);

        // Input Birthday
        calendar = Calendar.getInstance();
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        EditText et_add_birthday = (EditText) findViewById(R.id.et_add_birthday);
        et_add_birthday.setText(tDay + " / " + (tMonth + 1) + " / " + tYear);
        Button btn_add_date = (Button) findViewById(R.id.btn_add_date);

        btn_add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_add_birthday.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                        tYear = year;
                        tMonth = month + 1;
                        tDay = dayOfMonth;
                    }
                }, tYear, tMonth - 1, tDay);
                datePickerDialog.show();
            }
        });

        // Select Spouse
        AutoCompleteTextView et_add_spouse = (AutoCompleteTextView) findViewById(R.id.et_add_spouse);
        TextView tv_add_spouse = (TextView) findViewById(R.id.tv_add_spouse);
        ArrayAdapter<String> adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this, android.R.layout.simple_dropdown_item_1line, list_female);
        et_add_spouse.setAdapter(adapter_spouse);

        // Select Gender && Spouse
        RadioGroup radio_add_gender = (RadioGroup) findViewById(R.id.radio_add_gender);
        radio_add_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checked = group.getCheckedRadioButtonId();
                if (checked == R.id.radio_add_male) {
                    male = true;
                    tv_add_spouse.setText("Wife");
                    et_add_spouse.setText(null);
                    ArrayAdapter<String> adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this, android.R.layout.simple_dropdown_item_1line, list_female);
                    et_add_spouse.setAdapter(adapter_spouse);
                } else if (checkedId == R.id.radio_add_female) {
                    male = false;
                    tv_add_spouse.setText("Husband");
                    et_add_spouse.setText(null);
                    ArrayAdapter<String> adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this, android.R.layout.simple_dropdown_item_1line, list_male);
                    et_add_spouse.setAdapter(adapter_spouse);
                }
            }
        });

        // Select Father
        AutoCompleteTextView et_add_father = (AutoCompleteTextView) findViewById(R.id.et_add_father);
        ArrayAdapter<String> adapter_father = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list_male);
        et_add_father.setAdapter(adapter_father);

        // Button Add
        Button btn_add_member = (Button) findViewById(R.id.btn_add_member);
        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Member.convertName(et_add_name.getText().toString().trim());
                father_name = Member.convertName(et_add_father.getText().toString().trim());
                spouse_name = Member.convertName(et_add_spouse.getText().toString().trim());

                if (name.equals("")) {
                    Toast.makeText(AddMemberActivity.this, "You need to input name!", Toast.LENGTH_LONG).show();
                } else if (!father_name.equals("") && father_name.equals(spouse_name)) {
                    Toast.makeText(AddMemberActivity.this, "Father's name can't be the same as spouse's name!", Toast.LENGTH_LONG).show();
                } else {
                    if (!father_name.equals("")) {
                        for (Member member : list) {
                            if (member.getName().equals(father_name)) {
                                father = new Member(member);
                                break;
                            }
                        }
                    }

                    if (!spouse_name.equals("")) {
                        for (Member member : list) {
                            if (member.getName().equals(spouse_name)) {
                                spouse = new Member(member);
                                break;
                            }
                        }
                    }

                    Member member;
                    if (father_name.equals("") && spouse_name.equals("")) {
                        Toast.makeText(AddMemberActivity.this, name, Toast.LENGTH_SHORT).show();
                        member = new Member(name, tDay, tMonth, tYear, male);
                    } else if (father_name.equals("")) {
                        member = new Member(name, tDay, tMonth, tYear, male, spouse);
                        spouse.setSpouse(member);
                    } else if (spouse_name.equals("")) {
                        member = new Member(father, name, tDay, tMonth, tYear, male);
                    } else {
                        member = new Member(name, tDay, tMonth, tYear, male, father, spouse);
                        spouse.setSpouse(member);
                    }
                    list.add(member);

                    Intent intent = new Intent(AddMemberActivity.this, MainActivity.class);
                    intent.putExtra("list_member", list);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Button Cancel
        Button btn_cancel_add = (Button) findViewById(R.id.btn_cancel_add);
        btn_cancel_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}