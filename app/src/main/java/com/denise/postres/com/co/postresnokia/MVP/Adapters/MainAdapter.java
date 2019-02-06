package com.denise.postres.com.co.postresnokia.MVP.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denise.postres.com.co.postresnokia.Models.PostresResponse;
import com.denise.postres.com.co.postresnokia.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
private final OnItemClickListener listener;
private List<PostresResponse> data;
private Context context;

public MainAdapter(Context context, List<PostresResponse> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
        }


@Override
public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dessert, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
        }


@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.textView1.setText(data.get(position).getName());
        holder.textView2.setText(data.get(position).getType());
        if(data.get(position).getBatters() !=null) {
        holder.textView3.setText("Batidos: " + data.get(position).getBatters().getBatter().size());
        }
        if(data.get(position).getTopping() !=null) {
        holder.textView4.setText("Capas: " + data.get(position).getTopping().size());
        }

        }


@Override
public int getItemCount() {
        return data.size();
        }


public interface OnItemClickListener {
    void onClick(PostresResponse Item);
}

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView1, textView2, textView3, textView4;

    public ViewHolder(View itemView) {
        super(itemView);
        textView1 = itemView.findViewById(R.id.textView1);
        textView2 = itemView.findViewById(R.id.textView2);
        textView3 = itemView.findViewById(R.id.textView3);
        textView4 = itemView.findViewById(R.id.textView4);

    }


    public void click(final PostresResponse cityListData, final OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(cityListData);
            }
        });
    }
}


}
