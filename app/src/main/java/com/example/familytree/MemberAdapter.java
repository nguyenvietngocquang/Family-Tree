package com.example.familytree;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    Context context;
    static ArrayList<Member> arraylist;
    static ArrayList<Member> list;

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
        this.arraylist.addAll(this.list);
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
                intent.putExtra("position", position);
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
            ListMemberActivity.list.addAll(arraylist);
        } else {
            for (Member member : arraylist) {
                if (member.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ListMemberActivity.list.add(member);
                }
            }
        }
        notifyDataSetChanged();
    }
}