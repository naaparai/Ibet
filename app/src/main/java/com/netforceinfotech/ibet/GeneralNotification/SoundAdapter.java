package com.netforceinfotech.ibet.GeneralNotification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.dashboard.SoundlistActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 7/26/2016.
 */
public class SoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    SettingHolder viewHolder;
    private static final int SIMPLE_TYPE = 0;
    private static final int IMAGE_TYPE = 1;
    private final LayoutInflater inflater;
    private List<String> itemList;
    private List<String> sounList;
    private Context context;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    ArrayList<Integer> setting_icon = new ArrayList<>();



    public SoundAdapter(Context context, List<String> itemList,ArrayList<Integer> imagelist,List<String> sounList)
    {
        this.itemList = itemList;
        this.context = context;
        this.setting_icon = imagelist;
        this.sounList = sounList;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_sound, parent, false);
        viewHolder = new SettingHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {

        viewHolder.textViewTitle.setText(itemList.get(position));
        viewHolder.image_icon.setImageResource(setting_icon.get(position));
        viewHolder.textViewSound.setText(sounList.get(position));
    }

    private void showMessage(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return 7;
//        return itemList.size();
    }


    public class SettingHolder  extends RecyclerView.ViewHolder  implements View.OnClickListener
    {


        TextView textViewTitle, textViewSound, textViewPros;
        ImageView image_icon;
        MaterialRippleLayout materialRippleLayout;
        View view;


        public SettingHolder(View itemView)
        {
            super(itemView);
            //implementing onClickListener
            itemView.setOnClickListener(this);
            view = itemView;

            materialRippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);

            image_icon = (ImageView)  itemView.findViewById(R.id.setting_list_icon);
            textViewTitle = (TextView) itemView.findViewById(R.id.setting_list_text);
            textViewSound = (TextView) itemView.findViewById(R.id.sound_text);

        }
        @Override
        public void onClick(View v)
        {

                int position  =   getAdapterPosition();
                Intent intent =  new Intent(context, SoundlistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


        }
    }

}

