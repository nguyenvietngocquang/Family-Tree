package com.example.familytree.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familytree.R;
import com.example.familytree.random_member.RandomMemberActivity;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context context;
    static ArrayList<String> list_notification;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView tv_notification;
        private ItemClickListener itemClickListener;

        NotificationViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv_notification);
            tv_notification = (TextView) itemView.findViewById(R.id.tv_notification);
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

    NotificationAdapter(ArrayList<String> list_notification) {
        this.list_notification = list_notification;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        NotificationViewHolder nvh = new NotificationViewHolder(v);
        return nvh;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        String temp = list_notification.get(position);
        holder.tv_notification.setText(HtmlCompat.fromHtml(temp, HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, RandomMemberActivity.class);
                intent.putExtra("notifi", temp);
                intent.putExtra("notification_member", NotificationActivity.list);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_notification.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
