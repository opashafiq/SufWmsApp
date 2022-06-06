package com.example.SufWms.Forms.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Adapters.RV_LocationInventoryMapping_Adapter;
import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Classes.BookingIn;
import com.example.SufWms.Classes.BookingInUpdate;
import com.example.SufWms.Classes.BookingMasterIn;
import com.example.SufWms.Classes.Location_Inventory_Mapping;
import com.example.SufWms.Classes.ProjectVariables;
import com.example.SufWms.Forms.ScannerActivity;
import com.example.SufWms.Interface.onRVClickInterface;
import com.example.SufWms.Models.InsertionMessage;
import com.example.SufWms.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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

        loadMasterInfo(v);
        initObjects(v);
        initObjectListener(v);
        loadDummyData(v);

        return v;

    }

    private void loadDummyData(View v){
        ProjectVariables.locationDetails.setBarcodeNo("LB0001");
        ProjectVariables.locationDetails.setDetails("Location Det 1");
        ProjectVariables.locationDetails.setId("5");
        ProjectVariables.locationDetails.setIsAvailable("1");
        ProjectVariables.locationDetails.setLast_update("01-04-2022");
        ProjectVariables.locationDetails.setLast_user("edwin");
        ProjectVariables.locationDetails.setLocationId("1");
        ((EditText)v.findViewById(R.id.txtLocationDetails_BookingInUpdate)).setText(ProjectVariables.locationDetails.getDetails());
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

        pDialog = new ProgressDialog(this.getContext());
        pDialog.setTitle("Please wait..");
    }

    private void initObjectListener(View v){
        ImageButton btnShowScanner = (ImageButton)v.findViewById(R.id.btnScan_BookingInUpdate);
        btnShowScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getActivity().getApplicationContext(), ScannerActivity.class);
                startActivityForResult(_intent, 1);
            }
        });

        ImageButton btnAddItem = (ImageButton)v.findViewById(R.id.btnAdd_BookingInUpdate);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToRV(v);
            }
        });

        Button btnSave = (Button)v.findViewById(R.id.btnSave_BookingInUpdate);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showMessage("Size is: " + listLocation_Inventory_Mapping.size());
                saveData(v);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {//Returned from insert call

            if(resultCode == RESULT_OK){
                //Update List
            }
            if (resultCode == RESULT_CANCELED) {
                showMessage("Returned From activity");
                if(ProjectVariables.locationDetails==null){
                    ((EditText)getView().findViewById(R.id.txtLocationDetails_BookingInUpdate)).setText("");
                }else{
                    ((EditText)getView().findViewById(R.id.txtLocationDetails_BookingInUpdate)).setText(ProjectVariables.locationDetails.getDetails());
                }

            }
        }
    }//onActivityResult


    private void addItemToRV(View v){
        if(!validateDataBeforeAddingToRV(v)){
            return;
        }
        GenerateRVData(v);
    }

    private boolean validateDataBeforeAddingToRV(View v){
        String _qty= ((EditText)v.findViewById(R.id.txtQty_BookingInUpdate)).getText().toString();
        int _qtyTot=0;
        //Check whether there is any data in location details
        if(((EditText)v.findViewById(R.id.txtLocationDetails_BookingInUpdate)).getText().toString().equals("")){
            showMessage("Please scan a location");
            return false;
        }
        //Check whether there is any data Quantity
        if(_qty.equals("") || _qty.equals("0")){
            showMessage("Please Provide Qty");
            return false;
        }
        //Check whether Qty is greater than the booking qty
        if(Integer.parseInt(_qty) > Integer.parseInt(ProjectVariables.bookingIn.getQty())){
            showMessage("Quantity is larger than the booking quantity");
            return false;
        }

        //Check whether sum of Qty is greater than the booking qty
        for (Location_Inventory_Mapping lim:listLocation_Inventory_Mapping) {
            _qtyTot+= Integer.parseInt(lim.getQty());
        }
        if((Integer.parseInt(_qty)+_qtyTot)>Integer.parseInt(ProjectVariables.bookingIn.getQty())){
            showMessage("Total Quantity is greater than the booking quantity");
            return false;
        }

        return true;
    }

    private boolean validateBeforeSave(View v){
        //Check whether total qty greater than the booking qty
        int _qtyTot=0;
        String _SmissingQty= ((EditText)v.findViewById(R.id.txtMissingQty_BookingInUpdate)).getText().toString();
        String _SdamageQty= ((EditText)v.findViewById(R.id.txtDamageQty_BookingInUpdate)).getText().toString();

        int _missingQty = _SmissingQty.equals("")?0:Integer.parseInt(_SmissingQty);
        int _damageQty = _SdamageQty.equals("")?0:Integer.parseInt(_SdamageQty);

        for (Location_Inventory_Mapping lim:listLocation_Inventory_Mapping) {
            _qtyTot+= Integer.parseInt(lim.getQty());
        }
        if((_qtyTot+_damageQty+_missingQty)>Integer.parseInt(ProjectVariables.bookingIn.getQty())){
            showMessage("Total Quantity(Missing+Damage_Total) is greater than the booking quantity");
            return false;
        }

        return true;
    }

    private void GenerateRVData(View v){
        Location_Inventory_Mapping _location_inventory_mapping = new Location_Inventory_Mapping();
        _location_inventory_mapping.setLocationDetailsId(ProjectVariables.locationDetails.getId());
        _location_inventory_mapping.setLocationDetails(ProjectVariables.locationDetails.getDetails());
        _location_inventory_mapping.setQty(((EditText)v.findViewById(R.id.txtQty_BookingInUpdate)).getText().toString());
        _location_inventory_mapping.setLast_user(ProjectVariables.LoginId);
        listLocation_Inventory_Mapping.add(_location_inventory_mapping);
        rvAdapterLocationInventoryMapping.notifyDataSetChanged();
    }

    private void loadLocationInventoryMappingRV(View v){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Location_Inventory_Mapping>> call = service.doGetLocationInventoryMapping(ProjectVariables.Rec_Id,ProjectVariables.bookingIn.getSKU());
        call.enqueue(new Callback<List<Location_Inventory_Mapping>>() {
            @Override
            public void onResponse(Call<List<Location_Inventory_Mapping>> call, Response<List<Location_Inventory_Mapping>> response) {
                pDialog.dismiss();
                setRvLocationInventoryMapping(response.body());
            }

            @Override
            public void onFailure(Call<List<Location_Inventory_Mapping>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRvLocationInventoryMapping(List<Location_Inventory_Mapping> _listLocationInventoryMapping){
        listLocation_Inventory_Mapping.clear();
        listLocation_Inventory_Mapping.addAll(_listLocationInventoryMapping);
        rvAdapterLocationInventoryMapping.notifyDataSetChanged();
    }

    private void saveData(View v){
        if(!validateBeforeSave(v)){
            return;
        }

        BookingInUpdate _bookingUpdate = prepareSaveData(v);



        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<InsertionMessage>> call = service.doUpdateBookingIn(_bookingUpdate);
        call.enqueue(new Callback<List<InsertionMessage>>() {
            @Override
            public void onResponse(Call<List<InsertionMessage>> call, Response<List<InsertionMessage>> response) {
                pDialog.dismiss();
                showMessage("returned");
                showMessage(response.body().get(0).getStrMessage());
                //setRvLocationInventoryMapping(response.body());
            }

            @Override
            public void onFailure(Call<List<InsertionMessage>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private BookingInUpdate prepareSaveData(View v){
        String _SmissingQty= ((EditText)v.findViewById(R.id.txtMissingQty_BookingInUpdate)).getText().toString();
        String _SdamageQty= ((EditText)v.findViewById(R.id.txtDamageQty_BookingInUpdate)).getText().toString();

        int _missingQty = _SmissingQty.equals("")?0:Integer.parseInt(_SmissingQty);
        int _damageQty = _SdamageQty.equals("")?0:Integer.parseInt(_SdamageQty);

        //Prepare BookingMasterIn
        ProjectVariables.bookingMasterIn.setLast_User(ProjectVariables.LoginId);
        List<BookingMasterIn> _listBookingMasterIn = new ArrayList<>();
        _listBookingMasterIn.add(ProjectVariables.bookingMasterIn);

        //Prepare BookingIn
        List<BookingIn> _listBookingIn = new ArrayList<>();
        BookingIn _bookingIn = new BookingIn();
        _bookingIn=ProjectVariables.bookingIn;
        _bookingIn.setMissingQty(String.valueOf(_missingQty));
        _bookingIn.setDamageQty(String.valueOf(_damageQty));
        _bookingIn.setRecivedQty(String.valueOf(Integer.parseInt(_bookingIn.getQty())-_missingQty-_damageQty));
        _listBookingIn.add(_bookingIn);

        //prepare LocationInventoryMapping
        List<Location_Inventory_Mapping> _listLocationInventoryMapping = new ArrayList<>();
        _listLocationInventoryMapping.addAll(listLocation_Inventory_Mapping);

//        showMessage("Booking in Size: " + _listBookingIn.size());
//        showMessage("Booking Master Size: " + _listBookingMasterIn.size());
//        showMessage("Location Inventory Mapping Size: " + _listLocationInventoryMapping.size());



        //prepare BookingInUpdate
        BookingInUpdate bookingInUpdate = new BookingInUpdate();
        bookingInUpdate.setBi(_listBookingIn);
        bookingInUpdate.setBm(_listBookingMasterIn);
        bookingInUpdate.setLim(_listLocationInventoryMapping);
        //showMessage("Sizes : " + _listBookingIn.size() + " : " + _listBookingMasterIn.size() + " : " + _listLocationInventoryMapping.size() + " : " + _listBookingInUpdate.size());

        return bookingInUpdate;

    }

    private void showMessage(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }



}