package com.example.familytree.list_member;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familytree.R;
import com.example.familytree.member.Function;
import com.example.familytree.member.Member;

import java.util.ArrayList;

public class EditMemberActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    String name, father_name, spouse_name;
    int tYear, tMonth, tDay;
    boolean male;
    Member father;
    Member spouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        name = getIntent().getExtras().getString("name");
        list = (ArrayList<Member>) getIntent().getSerializableExtra("list_member");

        Member edit_member = Function.findMember(name, list);
        ArrayList<String> list_male = new ArrayList<String>();
        ArrayList<String> list_female = new ArrayList<String>();
        for (Member member : list) {
            if (member.isMale())
                list_male.add(member.getName());
            else list_female.add(member.getName());
        }
        // Edit Name
        EditText et_edit_name = (EditText) findViewById(R.id.et_edit_name);
        et_edit_name.setText(edit_member.getName());

        // Edit Birthday
        tYear = edit_member.getYear();
        tMonth = edit_member.getMonth();
        tDay = edit_member.getDay();

        EditText et_edit_birthday = (EditText) findViewById(R.id.et_edit_birthday);
        et_edit_birthday.setText(tDay + " / " + tMonth + " / " + tYear);
        Button btn_edit_date = (Button) findViewById(R.id.btn_edit_date);

        btn_edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_edit_birthday.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                        tYear = year;
                        tMonth = month + 1;
                        tDay = dayOfMonth;
                    }
                }, tYear, tMonth - 1, tDay);
                datePickerDialog.show();
            }
        });

        // Select Spouse
        AutoCompleteTextView et_edit_spouse = (AutoCompleteTextView) findViewById(R.id.et_edit_spouse);
        if (edit_member.getSpouse() != null)
            et_edit_spouse.setText(edit_member.getSpouse().getName());
        TextView tv_edit_spouse = (TextView) findViewById(R.id.tv_edit_spouse);
        ArrayAdapter<String> adapter_spouse;
        if (edit_member.isMale()) {
            male = true;
            tv_edit_spouse.setText("Wife");
            adapter_spouse = new ArrayAdapter<String>(EditMemberActivity.this,
                    android.R.layout.simple_dropdown_item_1line, list_female);
        } else {
            male = false;
            tv_edit_spouse.setText("Husband");
            adapter_spouse = new ArrayAdapter<String>(EditMemberActivity.this,
                    android.R.layout.simple_dropdown_item_1line, list_male);
        }
        et_edit_spouse.setAdapter(adapter_spouse);

        // Select Gender && Spouse
        RadioGroup radio_edit_gender = (RadioGroup) findViewById(R.id.radio_edit_gender);
        RadioButton radio_edit_male = (RadioButton) findViewById(R.id.radio_edit_male);
        RadioButton radio_edit_female = (RadioButton) findViewById(R.id.radio_edit_female);
        if (edit_member.isMale()) {
            radio_edit_male.setChecked(true);
            radio_edit_female.setChecked(false);
        } else {
            radio_edit_male.setChecked(false);
            radio_edit_female.setChecked(true);
        }
        radio_edit_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayAdapter<String> adapter_spouse;
                switch (checkedId) {
                    case R.id.radio_edit_male:
                        male = true;
                        tv_edit_spouse.setText("Wife");
                        et_edit_spouse.setText(null);
                        adapter_spouse = new ArrayAdapter<String>(EditMemberActivity.this,
                                android.R.layout.simple_dropdown_item_1line, list_female);
                        et_edit_spouse.setAdapter(adapter_spouse);
                        break;
                    case R.id.radio_edit_female:
                        male = false;
                        tv_edit_spouse.setText("Husband");
                        et_edit_spouse.setText(null);
                        adapter_spouse = new ArrayAdapter<String>(EditMemberActivity.this,
                                android.R.layout.simple_dropdown_item_1line, list_male);
                        et_edit_spouse.setAdapter(adapter_spouse);
                        break;
                }
            }
        });

        // Select Father
        AutoCompleteTextView et_edit_father = (AutoCompleteTextView) findViewById(R.id.et_edit_father);
        if (edit_member.getFather() != null)
            et_edit_father.setText(edit_member.getFather().getName());
        ArrayAdapter<String> adapter_father = new ArrayAdapter<String>(EditMemberActivity.this,
                android.R.layout.simple_dropdown_item_1line, list_male);
        et_edit_father.setAdapter(adapter_father);

        // Button Edit
        Button btn_edit_member = (Button) findViewById(R.id.btn_edit_member);
        btn_edit_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Function.convertName(et_edit_name.getText().toString().trim());
                father_name = Function.convertName(et_edit_father.getText().toString().trim());
                spouse_name = Function.convertName(et_edit_spouse.getText().toString().trim());

                if (name.equals("")) {
                    Toast.makeText(EditMemberActivity.this, "You need to input name!", Toast.LENGTH_LONG).show();
                } else if (!father_name.equals("") && father_name.equals(name)) {
                    Toast.makeText(EditMemberActivity.this, "Father's name can't be the same as member's name!", Toast.LENGTH_LONG).show();
                } else if (!father_name.equals("") && father_name.equals(spouse_name)) {
                    Toast.makeText(EditMemberActivity.this, "Father's name can't be the same as spouse's name!", Toast.LENGTH_LONG).show();
                } else if (!spouse_name.equals("") && spouse_name.equals(name)) {
                    Toast.makeText(EditMemberActivity.this, "Spouse's name can't be the same as member's name!", Toast.LENGTH_LONG).show();
                } else {
                    father = Function.findMember(father_name, list);
                    spouse = Function.findMember(spouse_name, list);

                    if (!edit_member.getName().equals(name))
                        edit_member.setName(name);
                    if (edit_member.getDay() != tDay)
                        edit_member.setDay(tDay);
                    if (edit_member.getMonth() != tMonth)
                        edit_member.setMonth(tMonth);
                    if (edit_member.getYear() != tYear)
                        edit_member.setYear(tYear);
                    if (edit_member.isMale() != male)
                        edit_member.setMale(male);

                    if (edit_member.getFather() == null) {
                        if (!father_name.equals(""))
                            edit_member.setFather(father);
                    } else {
                        if (father_name.equals(""))
                            edit_member.setFather(null);
                        else if (!edit_member.getFather().getName().equals(father_name))
                            edit_member.setFather(father);
                    }

                    if (edit_member.getSpouse() == null) {
                        if (!spouse_name.equals("")) {
                            if (spouse.getSpouse() != null) {
                                Member temp = Function.findMember(spouse.getSpouse().getName(), list);
                                temp.setSpouse(null);
                            }
                            edit_member.setSpouse(spouse);
                            spouse.setSpouse(edit_member);
                        }
                    } else {
                        if (spouse_name.equals("")) {
                            Member temp = Function.findMember(edit_member.getSpouse().getName(), list);
                            temp.setSpouse(null);
                            edit_member.setSpouse(null);
                        } else if (!edit_member.getSpouse().getName().equals(spouse_name)) {
                            Member temp = Function.findMember(edit_member.getSpouse().getName(), list);
                            temp.setSpouse(null);
                            if (spouse.getSpouse() != null) {
                                temp = Function.findMember(spouse.getSpouse().getName(), list);
                                temp.setSpouse(null);
                            }
                            edit_member.setSpouse(spouse);
                            spouse.setSpouse(edit_member);
                        }
                    }

                    Intent intent = new Intent(EditMemberActivity.this, ListMemberActivity.class);
                    intent.putExtra("edit_member", list);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Button Cancel
        Button btn_edit_cancel = (Button) findViewById(R.id.btn_edit_cancel);
        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
