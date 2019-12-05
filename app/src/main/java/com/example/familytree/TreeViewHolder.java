package com.example.familytree;

import android.view.View;
import android.widget.TextView;

import de.blox.graphview.ViewHolder;

public class TreeViewHolder extends ViewHolder {
    TextView textView;

    TreeViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }
}