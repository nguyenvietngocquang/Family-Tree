package com.example.familytree.list_member;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familytree.R;
import com.example.familytree.member.Member;

import java.util.ArrayList;
import java.util.Locale;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    Context context;
    static ArrayList<Member> arraylist;
    static ArrayList<Member> list;
    static ArrayList<Member> list_male;
    static ArrayList<Member> list_female;
    static int check = 0;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView memberName;
        TextView memberBirthday;
        ImageView memberGender;
        private ItemClickListener itemClickListener;

        MemberViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv_member);
            memberName = (TextView) itemView.findViewById(R.id.member_name);
            memberBirthday = (TextView) itemView.findViewById(R.id.member_birthday);
            memberGender = (ImageView) itemView.findViewById(R.id.member_gender);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    MemberAdapter(ArrayList<Member> list) {
        this.list = list;
        this.arraylist = new ArrayList<Member>();
        this.list_male = new ArrayList<Member>();
        this.list_female = new ArrayList<Member>();
        this.arraylist.addAll(this.list);
        for (Member member : list) {
            if (member.isMale())
                this.list_male.add(member);
            else this.list_female.add(member);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member, parent, false);
        MemberViewHolder mvh = new MemberViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        Member temp = list.get(position);
        holder.memberName.setText(temp.getName());
        holder.memberBirthday.setText(String.valueOf(temp.getDay()) + "/" + String.valueOf(temp.getMonth()) + "/" + String.valueOf(temp.getYear()));
        if (temp.isMale())
            holder.memberGender.setImageResource(R.drawable.male);
        else
            holder.memberGender.setImageResource(R.drawable.female);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, EditMemberActivity.class);
                intent.putExtra("name", temp.getName());
                intent.putExtra("list_member", arraylist);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ListMemberActivity.list.clear();
        if (charText.length() == 0) {
            switch (check) {
                case 0:
                    ListMemberActivity.list.addAll(arraylist);
                    break;
                case 1:
                    ListMemberActivity.list.addAll(list_male);
                    break;
                case 2:
                    ListMemberActivity.list.addAll(list_female);
                    break;
            }
        } else {
            switch (check) {
                case 0:
                    for (Member member : arraylist) {
                        if (member.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            ListMemberActivity.list.add(member);
                        }
                    }
                    break;
                case 1:
                    for (Member member : list_male) {
                        if (member.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            ListMemberActivity.list.add(member);
                        }
                    }
                    break;
                case 2:
                    for (Member member : list_female) {
                        if (member.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            ListMemberActivity.list.add(member);
                        }
                    }
                    break;
            }
        }
        notifyDataSetChanged();
    }

    public void change(int gender) {
        ListMemberActivity.list.clear();
        switch (gender) {
            case 0:
                ListMemberActivity.list.addAll(arraylist);
                check = 0;
                break;
            case 1:
                ListMemberActivity.list.addAll(list_male);
                check = 1;
                break;
            case 2:
                ListMemberActivity.list.addAll(list_female);
                check = 2;
                break;
        }
        notifyDataSetChanged();
    }
}