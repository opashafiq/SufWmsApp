package com.example.SufWms.Forms.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Adapters.RV_LocationInventoryMapping_Adapter;
import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Classes.BookingMasterIn;
import com.example.SufWms.Classes.Location_Inventory_Mapping;
import com.example.SufWms.Classes.ProjectVariables;
import com.example.SufWms.Interface.onRVClickInterface;
import com.example.SufWms.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingInUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingInUpdateFragment extends Fragment {

    private ProgressDialog pDialog;
    private String TAG="Booking In Update";

    //RV Inventory Mapping
    RecyclerView rvLocationInventoryMapping;
    ArrayList<Location_Inventory_Mapping> listLocation_Inventory_Mapping = new ArrayList<>();
    RV_LocationInventoryMapping_Adapter rvAdapterLocationInventoryMapping;

    // RV OnClickInterface For Mapping RV
    private onRVClickInterface onrvclickInterface;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingInUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingInUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingInUpdateFragment newInstance(String param1, String param2) {
        BookingInUpdateFragment fragment = new BookingInUpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_booking_in_update, container, false);


        return v;

    }

    private void loadMasterInfo(View v){
        ((TextView)v.findViewById(R.id.tvBookingIDCommon)).setText(ProjectVariables.bookingIn.getBookingId());
        ((TextView)v.findViewById(R.id.tvSKUCommon)).setText(ProjectVariables.bookingIn.getSKU());
        ((TextView)v.findViewById(R.id.tvASINCommon)).setText(ProjectVariables.bookingIn.getASIN());
    }

    private void initObjects(View v){
        //Set up onRVOnclickInterface for Master
        onrvclickInterface = new onRVClickInterface() {
            @Override
            public void setClick(int position) {
                Toast.makeText(getActivity().getApplicationContext(),"Position is: "+position,Toast.LENGTH_LONG).show();
            }
        };

        //Booking Location Inventory Mapping RV
        rvLocationInventoryMapping = (RecyclerView) v.findViewById(R.id.recycleLocationInventoryMapping);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvLocationInventoryMapping.setLayoutManager(layoutManager);
        rvAdapterLocationInventoryMapping = new RV_LocationInventoryMapping_Adapter(this.getContext(),listLocation_Inventory_Mapping,onrvclickInterface);
        rvLocationInventoryMapping.setAdapter(rvAdapterLocationInventoryMapping);
    }

    private void initObjectListener(View v){
        ImageButton btnShowScanner = (ImageButton)v.findViewById(R.id.btnScan_BookingInUpdate);
        btnShowScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageButton btnAddItem = (ImageButton)v.findViewById(R.id.btnAdd_BookingInUpdate);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToRV(view);
            }
        });
    }

    private void addItemToRV(View v){

    }

    private void calculateAndGenerateRVData(View v){

    }

    private void loadLocationInventoryMappingRV(View v){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Location_Inventory_Mapping>> call = service.doGetLocationInventoryMapping(Rec_id);
        call.enqueue(new Callback<List<Location_Inventory_Mapping>>() {
            @Override
            public void onResponse(Call<List<Location_Inventory_Mapping>> call, Response<List<Location_Inventory_Mapping>> response) {
                pDialog.dismiss();
                setBookingMasterIn(response.body());
            }

            @Override
            public void onFailure(Call<List<Location_Inventory_Mapping>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}