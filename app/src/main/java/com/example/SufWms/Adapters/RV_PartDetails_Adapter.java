package com.example.SufWms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Models.PartDetails;
import com.example.SufWms.R;

import java.util.List;

public class RV_PartDetails_Adapter extends RecyclerView.Adapter<RV_PartDetails_Adapter.UsersViewHolder> {

    Context context;
    List<PartDetails> ListCurrentData;

    public RV_PartDetails_Adapter(Context context, List<PartDetails> ListCurrentData) {
        this.ListCurrentData = ListCurrentData;
        this.context = context;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(context).inflate(R.layout.part_details_list_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.part_details_list_item,parent, false);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        // set the data
        holder.tvPartID.setText( ListCurrentData.get(position).getPartId());
        holder.tvPartsName.setText( ListCurrentData.get(position).getPartsName());
        // implement setONCLickListtener on itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with user name
                Toast.makeText(context, ListCurrentData.get(position).getPartsName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListCurrentData.size(); // size of the list items
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tvPartID, tvPartsName;

        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tvPartID = (TextView) itemView.findViewById(R.id.tvPartID);
            tvPartsName = (TextView) itemView.findViewById(R.id.tvPartsName);
        }
    }
}
