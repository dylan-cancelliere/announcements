package com.example.dhhs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    /*
     * Class to manage RecyclerViewAdapters.  Responsible for storing the name of each RecyclerView
     * as well as defining on click functionality
     */
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<DataStructure> output;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<DataStructure> mOutput) {
        output = mOutput;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(output.get(position).getHeading());

    holder.parentLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Opens the breakout activity when a RecyclerView is clicked on
            Log.d(TAG, "onClick: clicked on " + output.get(position).getHeading());
            Log.e("Tag", "" + position);
            openActivity(position);
            Toast.makeText(mContext, output.get(position).getHeading(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    @Override
    public int getItemCount() {
        return output.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public void openActivity(int position){ Intent intent = new Intent(mContext, BreakoutActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("Heading", output.get(position).getHeading());
        intent.putExtra("Info", output.get(position).getInfo());
        mContext.startActivity(intent);
    }
}