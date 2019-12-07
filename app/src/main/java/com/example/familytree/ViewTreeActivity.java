package com.example.familytree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.ViewHolder;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class ViewTreeActivity extends AppCompatActivity {
    static ArrayList<Member> list = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tree);
        GraphView graphView = findViewById(R.id.graph);

        list = (ArrayList<Member>) getIntent().getSerializableExtra("view_tree");
        String member = getIntent().getStringExtra("member");

        // Example tree
        Graph graph = Function.createGraph(member, list);

        // You can set the graph via the constructor or use the adapter.setGraph(Graph) method
        BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(graph) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
                return new TreeViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                ((TreeViewHolder) viewHolder).tv_node_name.setText(((Member) data).getName());
                ((TreeViewHolder) viewHolder).tv_node_age.setText(String.valueOf(((Member) data).getAge()) + " years old");
            }
        };
        graphView.setAdapter(adapter);

        // Set the algorithm here
        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    class TreeViewHolder extends ViewHolder {
        TextView tv_node_name, tv_node_age;

        TreeViewHolder(View itemView) {
            super(itemView);
            tv_node_name = itemView.findViewById(R.id.tv_node_name);
            tv_node_age = itemView.findViewById(R.id.tv_node_age);
        }
    }
}
