package com.example.familytree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMemberActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    static ArrayList<Member> list = new ArrayList<Member>();

    SearchView searchView;
    RecyclerView rv;
    MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        Intent intent = getIntent();
        if (intent.hasExtra("edit_member")) {
            list = (ArrayList<Member>) getIntent().getSerializableExtra("edit_member");
            Toast.makeText(ListMemberActivity.this, "Edit member successfully!", Toast.LENGTH_LONG).show();
        } else if (intent.hasExtra("list_member")) {
            list = (ArrayList<Member>) getIntent().getSerializableExtra("list_member");
        }

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new MemberAdapter(list);
        adapter.onAttachedToRecyclerView(rv);
        rv.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);

        ImageButton btn_list_cancel = (ImageButton) findViewById(R.id.btn_list_cancel);
        btn_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMemberActivity.this, MainActivity.class);
                intent.putExtra("list_member", MemberAdapter.arraylist);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        adapter.filter(text);
        return false;
    }
}
