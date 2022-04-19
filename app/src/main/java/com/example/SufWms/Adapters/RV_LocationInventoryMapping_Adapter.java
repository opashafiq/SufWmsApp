package com.example.SufWms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Classes.Location_Inventory_Mapping;
import com.example.SufWms.Interface.onRVClickInterface;
import com.example.SufWms.R;

import java.util.List;

public class RV_LocationInventoryMapping_Adapter extends RecyclerView.Adapter<RV_LocationInventoryMapping_Adapter.UsersViewHolder> {

    Context context;
    List<Location_Inventory_Mapping> ListCurrentData;
    onRVClickInterface onrvClickInterface;

    public RV_LocationInventoryMapping_Adapter(Context context, List<Location_Inventory_Mapping> ListCurrentData, onRVClickInterface onClickInterface) {
        this.ListCurrentData = ListCurrentData;
        this.context = context;
        this.onrvClickInterface=onClickInterface;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_booking_in_update, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        // set the data
        holder.tvRV_Item_LocationId_BookingInUpdate.setText( ListCurrentData.get(position).getLocationDetailsId());
        holder.tvRV_Item_Qty_BookingInUpdate.setText( ListCurrentData.get(position).getQty());
        // Set onRVItemClickInterface so that position can be accessed from main activity



        // implement setONCLickListtener on itemView
        // Initialize onClickInterface so that position can be accessed from activity/fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with user name
                Toast.makeText(context, ListCurrentData.get(position).getQty(), Toast.LENGTH_SHORT).show();
                onrvClickInterface.setClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListCurrentData.size(); // size of the list items
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tvRV_Item_LocationId_BookingInUpdate, tvRV_Item_Qty_BookingInUpdate;

        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tvRV_Item_LocationId_BookingInUpdate = (TextView) itemView.findViewById(R.id.RV_Item_LocationId_BookingInUpdate);
            tvRV_Item_Qty_BookingInUpdate = (TextView) itemView.findViewById(R.id.RV_Item_Qty_BookingInUpdate);

        }
    }
}
