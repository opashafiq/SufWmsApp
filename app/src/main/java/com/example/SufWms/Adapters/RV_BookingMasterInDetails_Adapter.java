package com.example.SufWms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Classes.BookingIn;
import com.example.SufWms.Interface.onRVClickInterface;
import com.example.SufWms.R;

import java.util.List;

public class RV_BookingMasterInDetails_Adapter extends RecyclerView.Adapter<RV_BookingMasterInDetails_Adapter.UsersViewHolder> {

    Context context;
    List<BookingIn> ListCurrentData;
    onRVClickInterface onrvClickInterface;

    public RV_BookingMasterInDetails_Adapter(Context context, List<BookingIn> ListCurrentData,onRVClickInterface onClickInterface) {
        this.ListCurrentData = ListCurrentData;
        this.context = context;
        this.onrvClickInterface=onClickInterface;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_booking_in_details, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        // set the data
        holder.tvRV_Item_SKU_BookingInDet.setText( ListCurrentData.get(position).getSKU());
        holder.tvRV_Item_ASIN_BookingInDet.setText( ListCurrentData.get(position).getASIN());
        holder.tvRV_Item_Qty_BookingInDet.setText( ListCurrentData.get(position).getQty());

        // Initialize onClickInterface so that position can be accessed from activity/fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with user name
                Toast.makeText(context, ListCurrentData.get(position).getBookingId(), Toast.LENGTH_SHORT).show();
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
        TextView tvRV_Item_SKU_BookingInDet, tvRV_Item_ASIN_BookingInDet,tvRV_Item_Qty_BookingInDet;

        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tvRV_Item_SKU_BookingInDet = (TextView) itemView.findViewById(R.id.RV_Item_SKU_BookingInDet);
            tvRV_Item_ASIN_BookingInDet = (TextView) itemView.findViewById(R.id.RV_Item_ASIN_BookingInDet);
            tvRV_Item_Qty_BookingInDet = (TextView) itemView.findViewById(R.id.RV_Item_Qty_BookingInDet);
        }
    }
}
