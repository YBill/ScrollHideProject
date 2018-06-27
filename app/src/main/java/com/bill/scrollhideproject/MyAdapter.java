package com.bill.scrollhideproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill on 2017/9/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
        for (int i = 0; i < 50; i++) {
            list.add("Hello World! " + i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.textView = (TextView) itemView.findViewById(R.id.textview);
        }

        private void update(final int position) {
            textView.setText(list.get(position));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });
        }

    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
