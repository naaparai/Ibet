package com.netforceinfotech.ibet1.live_event_main.expandcurrentgame.detail.stats.table;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netforceinfotech.ibet1.R;

import java.util.List;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SIMPLE_TYPE = 0;
    private static final int IMAGE_TYPE = 1;
    private final LayoutInflater inflater;
    private List<TableData> itemList;
    String home_id, away_id;
    private Context context;

    public TableAdapter(Context context, List<TableData> itemList, String home_id, String away_id) {
        this.itemList = itemList;
        this.context = context;
        this.home_id = home_id;
        this.away_id = away_id;
        inflater = LayoutInflater.from(context);
    }

    /*  @Override
      public int getItemViewType(int position) {
          if (itemList.get(position).image.isEmpty()) {
              return SIMPLE_TYPE;
          } else {
              return IMAGE_TYPE;
          }
      }
  */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_table, parent, false);
        TableHolder viewHolder = new TableHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TableHolder tableHolder = (TableHolder) holder;
        try {
            Glide.with(context).load(itemList.get(position).logo).error(R.drawable.ic_error).into(tableHolder.imageView);
        } catch (Exception ex) {
            Glide.with(context).load(R.drawable.ic_error).into(tableHolder.imageView);
        }
        tableHolder.textViewPoints.setText(itemList.get(position).points);
        tableHolder.textViewPosition.setText(itemList.get(position).position + "");
        tableHolder.textViewGD.setText(itemList.get(position).goalDiff);
        tableHolder.textViewGP.setText(itemList.get(position).overall_played);
        tableHolder.textViewName.setText(itemList.get(position).name);
        if (itemList.get(position).id.equalsIgnoreCase(home_id) || itemList.get(position).id.equalsIgnoreCase(away_id)) {
            tableHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        } else {
            tableHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return itemList.size();
//        return itemList.size();
    }
}