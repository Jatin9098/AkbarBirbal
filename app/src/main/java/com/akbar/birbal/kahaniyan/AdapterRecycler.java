package com.akbar.birbal.kahaniyan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jatinder.android on 12/14/2017.
 */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {


    private Context ctx;
    public AdapterRecycler(MainActivity ctx) {

        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_story.setText((position+1)+")  "+ctx.getResources().getStringArray(R.array.storiesTitle)[position]);
        holder.tv_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,DisplayScrolling.class);
                intent.putExtra("ID",position);
                ctx.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return ctx.getResources().getStringArray(R.array.storiesTitle).length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_story;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_story = (TextView) itemView.findViewById(R.id.tv_story);
            tv_story.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

        }
    }
}
