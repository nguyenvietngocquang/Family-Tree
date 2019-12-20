package com.example.familytree.add_member;

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

import com.example.familytree.member.Function;
import com.example.familytree.MainActivity;
import com.example.familytree.member.Member;
import com.example.familytree.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMemberActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

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

        list = (ArrayList<Member>) getIntent().getSerializableExtra("add_member");

        ArrayList<String> list_male = new ArrayList<String>();
        ArrayList<String> list_female = new ArrayList<String>();
        for (Member member : list) {
            if (member.isMale())
                list_male.add(member.getName());
            else list_female.add(member.getName());
        }

        // Input Name
        EditText et_add_name = (EditText) findViewById(R.id.et_add_name);

        // Input Birthday
        calendar = Calendar.getInstance();
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH) + 1;
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        EditText et_add_day = (EditText) findViewById(R.id.et_add_day);
        EditText et_add_month = (EditText) findViewById(R.id.et_add_month);
        EditText et_add_year = (EditText) findViewById(R.id.et_add_year);
        et_add_day.setText(String.valueOf(tDay));
        et_add_month.setText(String.valueOf(tMonth));
        et_add_year.setText(String.valueOf(tYear));

        Button btn_add_set = (Button) findViewById(R.id.btn_add_set);
        btn_add_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tDay = dayOfMonth;
                        tMonth = month + 1;
                        tYear = year;
                        et_add_day.setText(String.valueOf(tDay));
                        et_add_month.setText(String.valueOf(tMonth));
                        et_add_year.setText(String.valueOf(tYear));
                    }
                }, tYear, tMonth - 1, tDay);
                datePickerDialog.show();
            }
        });

        // Select Spouse
        AutoCompleteTextView et_add_spouse = (AutoCompleteTextView) findViewById(R.id.et_add_spouse);
        TextView tv_add_spouse = (TextView) findViewById(R.id.tv_add_spouse);
        ArrayAdapter<String> adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this,
                android.R.layout.simple_dropdown_item_1line, list_female);
        et_add_spouse.setAdapter(adapter_spouse);

        // Select Gender && Spouse
        RadioGroup radio_add_gender = (RadioGroup) findViewById(R.id.radio_add_gender);
        radio_add_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayAdapter<String> adapter_spouse;
                switch (checkedId) {
                    case R.id.radio_add_male:
                        male = true;
                        tv_add_spouse.setText("Wife");
                        et_add_spouse.setText(null);
                        adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this,
                                android.R.layout.simple_dropdown_item_1line, list_female);
                        et_add_spouse.setAdapter(adapter_spouse);
                        break;
                    case R.id.radio_add_female:
                        male = false;
                        tv_add_spouse.setText("Husband");
                        et_add_spouse.setText(null);
                        adapter_spouse = new ArrayAdapter<String>(AddMemberActivity.this,
                                android.R.layout.simple_dropdown_item_1line, list_male);
                        et_add_spouse.setAdapter(adapter_spouse);
                        break;
                }
            }
        });

        // Select Father
        AutoCompleteTextView et_add_father = (AutoCompleteTextView) findViewById(R.id.et_add_father);
        ArrayAdapter<String> adapter_father = new ArrayAdapter<String>(AddMemberActivity.this,
                android.R.layout.simple_dropdown_item_1line, list_male);
        et_add_father.setAdapter(adapter_father);

        // Button Add
        Button btn_add_member = (Button) findViewById(R.id.btn_add_member);
        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Function.convertName(et_add_name.getText().toString().trim());
                father_name = Function.convertName(et_add_father.getText().toString().trim());
                spouse_name = Function.convertName(et_add_spouse.getText().toString().trim());
                String day = et_add_day.getText().toString().trim();
                String month = et_add_month.getText().toString().trim();
                String year = et_add_year.getText().toString().trim();

                if (name.equals("")) {
                    Toast.makeText(AddMemberActivity.this, "You need to input name!", Toast.LENGTH_LONG).show();
                } else if (Function.contains(name, list)) {
                    Toast.makeText(AddMemberActivity.this, "This member already exists!", Toast.LENGTH_LONG).show();
                } else if (day.equals("") || month.equals("") || year.equals("")) {
                    Toast.makeText(AddMemberActivity.this, "You need to input birthday!", Toast.LENGTH_LONG).show();
                } else if (!father_name.equals("") && father_name.equals(spouse_name)) {
                    Toast.makeText(AddMemberActivity.this, "Father's name can't be the same as spouse's name!", Toast.LENGTH_LONG).show();
                } else if (!father_name.equals("") && !Function.contains(father_name, list)) {
                    Toast.makeText(AddMemberActivity.this, "Father doesn't exist!", Toast.LENGTH_LONG).show();
                } else if (!spouse_name.equals("") && !Function.contains(spouse_name, list)) {
                    Toast.makeText(AddMemberActivity.this, "Spouse doesn't exist!", Toast.LENGTH_LONG).show();
                } else {
                    father = Function.findMember(father_name, list);
                    spouse = Function.findMember(spouse_name, list);
                    tDay = Integer.valueOf(day);
                    tMonth = Integer.valueOf(month);
                    tYear = Integer.valueOf(year);

                    Member member;
                    if (father_name.equals("") && spouse_name.equals("")) {
                        member = new Member(name, tDay, tMonth, tYear, male);
                    } else if (father_name.equals("")) {
                        member = new Member(spouse, name, tDay, tMonth, tYear, male);
                        if (spouse.getSpouse() != null) {
                            Member temp = Function.findMember(spouse.getSpouse().getName(), list);
                            temp.setSpouse(null);
                        }
                        spouse.setSpouse(member);
                    } else if (spouse_name.equals("")) {
                        member = new Member(name, tDay, tMonth, tYear, male, father);
                    } else {
                        member = new Member(name, tDay, tMonth, tYear, male, father, spouse);
                        if (spouse.getSpouse() != null) {
                            Member temp = Function.findMember(spouse.getSpouse().getName(), list);
                            temp.setSpouse(null);
                        }
                        spouse.setSpouse(member);
                    }
                    list.add(member);

                    Intent intent = new Intent(AddMemberActivity.this, MainActivity.class);
                    intent.putExtra("add_member", list);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Button Cancel
        Button btn_add_cancel = (Button) findViewById(R.id.btn_add_cancel);
        btn_add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
